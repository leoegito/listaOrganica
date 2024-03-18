package leoegito.listaOrganica.Controller;

import leoegito.listaOrganica.Model.Product;
import leoegito.listaOrganica.Service.ProductService;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {
    private ProductService productService;

    @Autowired
    public ProductController(ProductService productService){
        this.productService = productService;
    }

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

}
