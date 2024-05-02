package leoegito.listaOrganica.Service;

import leoegito.listaOrganica.Model.User;
import leoegito.listaOrganica.Repository.UserRepository;
import leoegito.listaOrganica.Service.Exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
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

    public User save(User user){
        return this.userRepository.save(user);
    }

    public User update(Long id, User user){

    }

    public User updatePassword(Long id, String hashPassword){

    }

}
