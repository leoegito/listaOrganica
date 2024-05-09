package leoegito.listaOrganica.Service;

import jakarta.persistence.EntityNotFoundException;
import leoegito.listaOrganica.Model.MyUser;
import leoegito.listaOrganica.Repository.MyUserRepository;
import leoegito.listaOrganica.Service.Exceptions.DatabaseException;
import leoegito.listaOrganica.Service.Exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MyUserService implements UserDetailsService {

    @Autowired
    private MyUserRepository myUserRepository;

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
                .roles(loadedMyUser.getRole())
                .build();
//        return org.springframework.security.core.userdetails.User.builder()
//                .username(user.get().getUsername())
//                .password(user.get().getPasswordHash())
//                .roles(user.get().getRole())
//                .build();
//    }
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

    //Working on it
//    public User updatePassword(Long id, String hashPassword, String authToken){
//        if(authToken != this.userRepository.getReferenceById(id).getAuthToken()){
//            throw new NotAuthorizedException();
//        }
//        try{
//            User user = this.userRepository.getReferenceById(id);
//            user.setPasswordHash(hashPassword);
//            return this.userRepository.save(user);
//        } catch (EntityNotFoundException e){
//            throw new ResourceNotFoundException(id);
//        }
//    }

}
