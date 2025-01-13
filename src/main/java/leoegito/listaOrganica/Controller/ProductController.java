package leoegito.listaOrganica.Controller;

import leoegito.listaOrganica.Model.Product;
import leoegito.listaOrganica.Service.Exceptions.ResourceNotFoundException;
import leoegito.listaOrganica.Service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.TreeSet;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/list")
    public ResponseEntity<List<Product>> getList(){
        List<Product> productList = this.productService.findAll();
        return ResponseEntity.ok().body(productList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getByID(@PathVariable("id") Long id) throws ResourceNotFoundException{
        return ResponseEntity.ok(this.productService.findByID(id));
    }

    @PostMapping()
    public ResponseEntity<Product> insert(@RequestBody Product product){
        Product obj = this.productService.save(product);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(obj.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }


    @GetMapping("/search")
    public ResponseEntity<List<Product>> searchProducts(@RequestParam String searchName){
        List<Product> foundProducts = productService.searchProducts(searchName);
        if(foundProducts.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(foundProducts);
    }


    // Endpoint para receber o parâmetro de pesquisa como JSON
    @PostMapping("/json/search")
    public List<Product> searchProducts(@RequestBody SearchRequest searchRequest) {
        String query = searchRequest.getQuery();
        // Mock response for testing
        List<Product> products = new ArrayList<>();
        if (query.toLowerCase().contains("maçã")) {
            products.add(new Product(1L, "Maçã", "Maçã Orgânica", 0.0, 0.0, new HashSet<>(), new TreeSet<>()));
        }
        if (query.toLowerCase().contains("banana")) {
            products.add(new Product(2L, "Banana", "Banana Orgânica", 0.0, 0.0, new HashSet<>(), new TreeSet<>()));
        }
        return products;
    }


}

class SearchRequest {
    private String query;

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }
}