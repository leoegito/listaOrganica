package leoegito.listaOrganica.Controller;

import leoegito.listaOrganica.Model.ProductList;
import leoegito.listaOrganica.Service.ProductListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/shoppingList")
public class ProductListController {

    @Autowired
    private ProductListService productListService;

    @GetMapping
    public ResponseEntity<List<ProductList>> findAll(){
        List<ProductList> list = productListService.findAll();
        return ResponseEntity.ok().body(list);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<ProductList> findByID(@PathVariable Long id){
        ProductList obj = productListService.findByID(id);
        return ResponseEntity.ok().body(obj);
    }

    @PostMapping()
    public ResponseEntity<ProductList> insert(@RequestBody ProductList productList){
        ProductList obj = this.productListService.save(productList);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(obj.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @PostMapping("/product/{id}")
    public ResponseEntity<ProductList> insertProduct(){}

    @PutMapping("/{id}")
    public ResponseEntity<ProductList> update(Long id, Prod){}


}
