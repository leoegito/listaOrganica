package leoegito.listaOrganica.Controller;

import leoegito.listaOrganica.Model.Product;
import leoegito.listaOrganica.Model.ProductList;
import leoegito.listaOrganica.Service.ProductListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RestController
@RequestMapping("/shoppingList")
public class ProductListController {

    @Autowired
    private ProductListService productListService;

    @GetMapping("/list")
    public ResponseEntity<List<ProductList>> findAll(){
        List<ProductList> list = productListService.findAll();
        return ResponseEntity.ok().body(list);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<ProductList> findByID(@PathVariable Long id){
        ProductList obj = productListService.findByID(id);
        return ResponseEntity.ok().body(obj);
    }

    @PostMapping("/create")
    public ResponseEntity<ProductList> insert(@RequestBody ProductList productList){
        ProductList obj = this.productListService.save(productList);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(obj.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

//    @PostMapping("/product/{id}")
//    public ResponseEntity<ProductList> insertProduct(){}

    @PostMapping("/{id}/add-product")
    public ResponseEntity<ProductList> addProductToList(@PathVariable(value = "id") Long id, @RequestBody Product product){
        Optional<ProductList> optionalProductList = productListService.insertProduct(id, product);
        if(!optionalProductList.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(optionalProductList.get());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductList> update(@PathVariable(value = "id") Long id, Product product){
        ProductList list = this.productListService.findByID(id);
        list.getProducts().add(product);
        return ResponseEntity.ok().body(list);
    }


}
