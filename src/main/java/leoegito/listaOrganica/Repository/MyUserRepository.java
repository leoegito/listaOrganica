package leoegito.listaOrganica.Repository;

import leoegito.listaOrganica.Model.MyUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MyUserRepository extends JpaRepository<MyUser, Long> {

//    @Query("SELECT u FROM User u WHERE UPPER(u.username) LIKE UPPER(:userName)")
    MyUser findByUsername(String username);
}
