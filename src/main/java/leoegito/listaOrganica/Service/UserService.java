package leoegito.listaOrganica.Service;

import jakarta.persistence.EntityNotFoundException;
import leoegito.listaOrganica.Model.User;
import leoegito.listaOrganica.Repository.UserRepository;
import leoegito.listaOrganica.Service.Exceptions.DatabaseException;
import leoegito.listaOrganica.Service.Exceptions.NotAuthorizedException;
import leoegito.listaOrganica.Service.Exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> findAll(){
        return this.userRepository.findAll();
    }

    public User findByID(Long id){
        Optional<User> obj = this.userRepository.findById(id);
        return obj.orElseThrow(
                () -> new ResourceNotFoundException(id)
        );
    }
    public User searchByUsername(String username){
        return userRepository.findByUsername(username);
    }
    public User save(User user){
        return this.userRepository.save(user);
    }

    public User update(Long id, User user){
        try{
            this.userRepository.getReferenceById(id);
            return this.userRepository.save(user);
        } catch (EntityNotFoundException e){
            throw new ResourceNotFoundException(id);
        }
    }

    public void delete(Long id){
        try{
            this.userRepository.deleteById(id);
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
