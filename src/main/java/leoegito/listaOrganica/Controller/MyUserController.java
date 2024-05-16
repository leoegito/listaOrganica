package leoegito.listaOrganica.Controller;

import leoegito.listaOrganica.Configuration.SecurityConfiguration;
import leoegito.listaOrganica.Controller.Exceptions.InvalidPasswordException;
import leoegito.listaOrganica.Model.MyUser;
import leoegito.listaOrganica.Model.PasswordChangeRequest;
import leoegito.listaOrganica.Model.Product;
import leoegito.listaOrganica.Model.ProductList;
import leoegito.listaOrganica.Repository.MyUserRepository;
import leoegito.listaOrganica.Service.Exceptions.ResourceNotFoundException;
import leoegito.listaOrganica.Service.MyUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/users")
public class MyUserController {

    @Autowired
    private MyUserRepository myUserRepository;

    @Autowired
    private MyUserService myUserService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public MyUser createUser(@RequestBody MyUser user){
        user.setPasswordHash(this.passwordEncoder.encode(user.getPasswordHash()));
        return myUserRepository.save(user);
    }

    @GetMapping("/list")
    public ResponseEntity<List<MyUser>> getList(){
        List<MyUser> myUsers = this.myUserService.findAll();
        return ResponseEntity.ok().body(myUsers);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MyUser> getByID(@PathVariable("id") Long id) throws ResourceNotFoundException {
        return ResponseEntity.ok(this.myUserService.findByID(id));
    }

    @PutMapping("/{id}/productList")
    public ResponseEntity<MyUser> addProductListToMyUser(@PathVariable Long id, @RequestBody ProductList productList) {
        return ResponseEntity.ok(myUserService.addProductListToMyUser(id, productList));
    }

    @PutMapping("/changePassword")
    public ResponseEntity<?> changePassowrd(@RequestBody PasswordChangeRequest passwordChangeRequest){
        try{
            this.myUserService.changeUserPassword(passwordChangeRequest);
            return ResponseEntity.ok("Senha alterada com sucesso.");
        } catch (InvalidPasswordException e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }
}
