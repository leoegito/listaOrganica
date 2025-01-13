package leoegito.listaOrganica.Service;

import jakarta.persistence.EntityNotFoundException;
import leoegito.listaOrganica.Configuration.SecurityConfiguration;
import leoegito.listaOrganica.Controller.Exceptions.InvalidPasswordException;
import leoegito.listaOrganica.DTO.CredentialsDto;
import leoegito.listaOrganica.DTO.UserDto;
import leoegito.listaOrganica.Mappers.UserMapper;
import leoegito.listaOrganica.Model.MyUser;
import leoegito.listaOrganica.Model.PasswordChangeRequest;
import leoegito.listaOrganica.Model.ProductList;
import leoegito.listaOrganica.Repository.MyUserRepository;
import leoegito.listaOrganica.Service.Exceptions.DatabaseException;
import leoegito.listaOrganica.Service.Exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.nio.CharBuffer;
import java.util.List;
import java.util.Optional;

@Service
public class MyUserService implements UserDetailsService {

    @Autowired
    private MyUserRepository myUserRepository;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private final PasswordEncoder passwordEncoder = SecurityConfiguration.passwordEncoder();


    public MyUser login(String username, String rawPassword) throws ResourceNotFoundException {
        MyUser myUser = myUserRepository.findByUsername(username).orElseThrow(
                () -> new UsernameNotFoundException(username)
        );

        if (passwordEncoder.matches(rawPassword, myUser.getPasswordHash())) {
            return myUser;
        } else {
            throw new RuntimeException("Invalid password");
        }
    }

    public UserDto login(CredentialsDto credentialsDto) throws ResourceNotFoundException{

        MyUser myUser = myUserRepository.findByUsername(credentialsDto.username()).orElseThrow(
                () -> new UsernameNotFoundException(credentialsDto.username())
        );

        if(this.passwordEncoder.matches(CharBuffer.wrap(credentialsDto.password()),
                myUser.getPasswordHash())){
            return this.userMapper.toUserDto(myUser);
        } else {
            throw new InvalidPasswordException("Senha inválida", HttpStatus.BAD_REQUEST);
        }

    }

    public List<MyUser> findAll(){
        return this.myUserRepository.findAll();
    }

    public MyUser findByID(Long id){
        Optional<MyUser> obj = this.myUserRepository.findById(id);
        return obj.orElseThrow(
                () -> new ResourceNotFoundException(id)
        );
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
        MyUser user = myUserRepository.findByUsername(username).orElseThrow(
                () -> new UsernameNotFoundException(username)
        );

        MyUser loadedMyUser = user;
        return org.springframework.security.core.userdetails.User.builder()
                .username(loadedMyUser.getUsername())
                .password(loadedMyUser.getPasswordHash())
                .roles(this.getRoles(loadedMyUser))
                .build();

    }

    public String[] getRoles(MyUser myUser){
        if(myUser.getRole() == null){
            return new String[]{"USER"};
        }
        return myUser.getRole().split(",");
    }

    public MyUser save(MyUser user){
        return this.myUserRepository.save(user);
    }

    public MyUser update(Long id, MyUser user){

        try {
            MyUser existingUser = myUserRepository.getReferenceById(id);
            existingUser.setFullname(user.getFullname());
            existingUser.setEmail(user.getEmail());
            existingUser.setPhone(user.getPhone());
            existingUser.setZipCode(user.getZipCode());
            existingUser.setCity(user.getCity());
            existingUser.setState(user.getState());
            existingUser.setDistrict(user.getDistrict());
            existingUser.setStreet(user.getStreet());
            // Preservar a role original do usuário
            return this.myUserRepository.save(existingUser);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException(id);
        }
    }

    public void delete(Long id){
        try{
            this.myUserRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e){
            throw new ResourceNotFoundException(id);
        } catch (DataIntegrityViolationException e){
            throw new DatabaseException(e.getMessage());
        }
    }

    public List<ProductList> getAllProductLists(Long id){
        MyUser myUser = myUserRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Usuário inválido."));
        return myUser.getProductLists();
    }

    public ProductList getProductList(Long userID, Long listID){
        MyUser myUser = myUserRepository.findById(userID).orElseThrow(() -> new ResourceNotFoundException("Usuário inválido."));
        for(ProductList shoppingList :  myUser.getProductLists()){
            if(shoppingList.getId() == listID){
                return shoppingList;
            }
        }
        return null;
    }

    public MyUser addProductListToMyUser(Long id, ProductList productList) {
        MyUser myUser = myUserRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("MyUser not found"));
        myUser.getProductLists().add(productList);
        //Better not to return the entire user... Work on it later.
        return myUserRepository.save(myUser);
    }


    public String changeUserPassword(PasswordChangeRequest passwordChangeRequest) throws InvalidPasswordException {
        Authentication currentUser = SecurityContextHolder.getContext().getAuthentication();
        String username = currentUser.getName();

        MyUser user = this.myUserRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username));
        if (!oldPasswordValidation(user, passwordChangeRequest.getOldPassword())) {
            throw new InvalidPasswordException("Senha antiga incorreta.", HttpStatus.BAD_REQUEST);
        }

        user.setPasswordHash(passwordEncoder.encode(passwordChangeRequest.getNewPassword()));

        this.myUserRepository.save(user);

        return "Senha alterada com sucesso";
    }

    public boolean oldPasswordValidation(MyUser user, String oldPassword){
        return passwordEncoder.matches(oldPassword, user.getPasswordHash());
    }

}
