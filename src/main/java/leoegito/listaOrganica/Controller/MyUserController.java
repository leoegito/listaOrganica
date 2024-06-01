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
import leoegito.listaOrganica.Service.ProductListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RestController
@RequestMapping("/users")
public class MyUserController {

    @Autowired
    private MyUserRepository myUserRepository;

    @Autowired
    private MyUserService myUserService;

    @Autowired
    private ProductListService productListService;

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

    @PutMapping("/{id}/productList/{productListID}")
    public ResponseEntity<MyUser> addProductListToMyUser(@PathVariable Long id, @PathVariable Long productListID) {
        ProductList productList = this.productListService.findByID(productListID);
        return ResponseEntity.ok(myUserService.addProductListToMyUser(id, productList));
    }

    //TODO - Deletar lista inteira
//    @DeleteMapping("/{id}/productList/{productListID}")
//    public void deleteProductList(@PathVariable Long id, @PathVariable Long productListID) {
//        ProductList productList = this.productListService.findByID(productListID);
//        return ResponseEntity.ok(myUserService.addProductListToMyUser(id, productList));
//    }

    //TODO - Deletar apenas um listItem

    @GetMapping("{id}/productList/list")
    public ResponseEntity<List<ProductList>> getProductLists(@PathVariable Long id){
        return ResponseEntity.ok(myUserService.getAllProductLists(id));
    }

    @GetMapping("{userID}/productList/{listID}")
    public ResponseEntity<ProductList> getProductList(@PathVariable Long userID, @PathVariable Long listID){
        return ResponseEntity.ok(myUserService.getProductList(userID,listID));
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
