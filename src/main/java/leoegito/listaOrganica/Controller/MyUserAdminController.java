package leoegito.listaOrganica.Controller;

import leoegito.listaOrganica.Model.MyUser;
import leoegito.listaOrganica.Service.MyUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RestController
@RequestMapping("/console")
public class MyUserAdminController {

    @Autowired
    private MyUserService myUserService;

    @GetMapping("/users")
    public ResponseEntity<List<MyUser>> getAllUsers() {
        List<MyUser> users = myUserService.findAll();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<MyUser> getUserById(@PathVariable Long id) {
        MyUser user = myUserService.findByID(id);
        return ResponseEntity.ok(user);
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<MyUser> updateUser(@PathVariable Long id, @RequestBody MyUser user) {
        MyUser updatedUser = myUserService.update(id, user);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        myUserService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
