package leoegito.listaOrganica.Controller;

import leoegito.listaOrganica.Model.Product;
import leoegito.listaOrganica.Service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;

//    @Autowired
//    public ProductController(ProductService productService){
//        this.productService = productService;
//    }

    @GetMapping("/list")
    public List<Product> getList(){
        return this.productService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getByID(@PathVariable("id") Long id) throws Exception{
        return ResponseEntity.ok(this.productService.findByID(id).orElseThrow(
                () -> new Exception("Product not found.")
        ));
    }

    @PostMapping()
    public ResponseEntity<Product> insert(@RequestBody Product product){
        Product obj = this.productService.save(product);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(obj.getId()).toUri();
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> update(@PathVariable("id") Long id, @RequestBody Product product){
        Product obj = this.productService.update(id, product);
        return ResponseEntity.ok().body(obj);
    }

}
