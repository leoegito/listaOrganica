package leoegito.listaOrganica.Controller;

import leoegito.listaOrganica.Model.ListItem;
import leoegito.listaOrganica.Model.MyUser;
import leoegito.listaOrganica.Model.Price;
import leoegito.listaOrganica.Model.Product;
import leoegito.listaOrganica.Service.MyUserService;
import leoegito.listaOrganica.Service.PriceService;
import leoegito.listaOrganica.Service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RestController
@RequestMapping("/console")
public class MyUserAdminController {

    @Autowired
    private MyUserService myUserService;

    @Autowired
    private PriceService priceService;

    @Autowired
    private ProductService productService;

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

    @PostMapping("/price/product/{product_id}")
    public ResponseEntity<Price> insertPriceToProduct(@PathVariable(value = "product_id") Long productID, @RequestBody Price price){

        Price tempPrice = this.priceService.insertAdmin(productID, price);

        Product proxyProduct = this.productService.findByID(productID);

        for(ListItem listItem : proxyProduct.getListItems()){
            if(listItem.getId().getProduct().getId() == productID){

                listItem.setPrice(proxyProduct.getMedian());

                proxyProduct.enforceAddPrice(price.getPriceValue());
            }
        }

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(tempPrice.getId()).toUri();
        return ResponseEntity.created(uri).build();

    }

    @PutMapping("/product/{id}")
    public ResponseEntity<Product> update(@PathVariable("id") Long id, @RequestBody Product product){
//        Product obj = this.productService.update(id, product);
        Product obj = this.productService.findByID(id);
        String newName = product.getName();
        String newDescription = product.getDescription();
        if(newName != null && newName.length() > 3){
            obj.setName(newName);
        }
        if(newDescription != null && newDescription.length() > 3){
            obj.setDescription(newDescription);
        }
        this.productService.update(id, obj);
        return ResponseEntity.ok().body(obj);
    }

    @DeleteMapping("/product/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        this.productService.delete(id);
        return ResponseEntity.noContent().build();
    }


}
