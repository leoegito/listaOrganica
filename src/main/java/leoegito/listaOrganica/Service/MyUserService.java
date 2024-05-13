package leoegito.listaOrganica.Service;

import jakarta.persistence.EntityNotFoundException;
import leoegito.listaOrganica.Configuration.SecurityConfiguration;
import leoegito.listaOrganica.Controller.Exceptions.InvalidPasswordException;
import leoegito.listaOrganica.Model.MyUser;
import leoegito.listaOrganica.Model.PasswordChangeRequest;
import leoegito.listaOrganica.Model.ProductList;
import leoegito.listaOrganica.Repository.MyUserRepository;
import leoegito.listaOrganica.Service.Exceptions.DatabaseException;
import leoegito.listaOrganica.Service.Exceptions.NotAuthorizedException;
import leoegito.listaOrganica.Service.Exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.Console;
import java.util.List;
import java.util.Optional;

@Service
public class MyUserService implements UserDetailsService {

    @Autowired
    private MyUserRepository myUserRepository;

    @Autowired
    private final PasswordEncoder passwordEncoder = SecurityConfiguration.passwordEncoder();

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
        Optional<MyUser> user = Optional.ofNullable(myUserRepository.findByUsername(username));
        if(user.isEmpty()){
            throw new UsernameNotFoundException(username);
        }
        MyUser loadedMyUser = user.get();
        return org.springframework.security.core.userdetails.User.builder()
                .username(loadedMyUser.getUsername())
                .password(loadedMyUser.getPasswordHash())
                .roles(this.getRoles(loadedMyUser))
                .build();
//        return org.springframework.security.core.userdetails.User.builder()
//                .username(user.get().getUsername())
//                .password(user.get().getPasswordHash())
//                .roles(user.get().getRole())
//                .build();
//    }
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
        try{
            this.myUserRepository.getReferenceById(id);
            return this.myUserRepository.save(user);
        } catch (EntityNotFoundException e){
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

    public MyUser addProductListToMyUser(Long id, ProductList productList) {
        MyUser myUser = myUserRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("MyUser not found"));
        myUser.getProductLists().add(productList);
//        productList.setMyUser(myUser);
        //Better not to return the entire user... Work on it later.
        return myUserRepository.save(myUser);
    }

    //Working on it
//    public MyUser updatePassword(Long id, String hashPassword, String authToken){
//        if(authToken != this.myUserRepository.getReferenceById(id)){
//            throw new NotAuthorizedException();
//        }
//        try{
//            MyUser user = this.myUserRepository.getReferenceById(id);
//            user.setPasswordHash(hashPassword);
//            return this.myUserRepository.save(user);
//        } catch (EntityNotFoundException e){
//            throw new ResourceNotFoundException(id);
//        }
//    }
//    public String changeUserPassword(String passwordChangeRequest) {
//        Authentication currentUser = SecurityContextHolder.getContext().getAuthentication();
//        String username = currentUser.getName();
//
//        UserDetails userDetails = this.loadUserByUsername(username);
//        if (!passwordEncoder.matches(passwordChangeRequest.getOldPassword(), userDetails.getPassword())) {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Senha atual incorreta");
//        }
//        if(oldPasswordValidation(userDetails, userDetails.getPassword()))
//
//        User user = userRepository.findByUsername(username);
//        user.setPassword(passwordEncoder.encode(passwordChangeRequest.getNewPassword()));
//        userRepository.save(user);
//
//        return ResponseEntity.ok("Senha alterada com sucesso");
//    }

    public String changeUserPassword(PasswordChangeRequest passwordChangeRequest) throws InvalidPasswordException {
        Authentication currentUser = SecurityContextHolder.getContext().getAuthentication();
        String username = currentUser.getName();
        //Debug
//        System.out.println("Username:" +username);

        MyUser user = this.myUserRepository.findByUsername(username);
        if (!oldPasswordValidation(user, passwordChangeRequest.getOldPassword())) {
            throw new InvalidPasswordException("Senha antiga incorreta.");
        }

        //Debug
//        System.out.println(passwordChangeRequest.toString());
        user.setPasswordHash(passwordEncoder.encode(passwordChangeRequest.getNewPassword()));

        //Debug
//        System.out.println(user.toString());

        this.myUserRepository.save(user);

        return "Senha alterada com sucesso";
    }

    public boolean oldPasswordValidation(MyUser user, String oldPassword){
        return passwordEncoder.matches(oldPassword, user.getPasswordHash());
    }

}
