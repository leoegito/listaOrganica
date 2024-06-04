package leoegito.listaOrganica.Controller;

import leoegito.listaOrganica.Model.ListItem;
import leoegito.listaOrganica.Model.Price;
import leoegito.listaOrganica.Model.Product;
import leoegito.listaOrganica.Model.ProductList;
import leoegito.listaOrganica.Repository.ProductRepository;
import leoegito.listaOrganica.Service.Exceptions.ResourceNotFoundException;
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
@RequestMapping("/price")
public class PriceController {

    @Autowired
    private PriceService priceService;

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductRepository productRepository;

    @GetMapping("/list")
    public ResponseEntity<List<Price>> getList(){
        List<Price> priceList = this.priceService.findAll();
        return ResponseEntity.ok().body(priceList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Price> getByID(@PathVariable("id") Long id) throws ResourceNotFoundException {
        return ResponseEntity.ok(this.priceService.findByID(id));
    }

    @PostMapping("/product/{product_id}")
    public ResponseEntity<Price> insertPriceToProduct(@PathVariable(value = "product_id") Long productID, @RequestBody Price price){

        Price tempPrice = this.priceService.insert(productID, price);

        Product proxyProduct = this.productService.findByID(productID);

//        List<ProductList> proxyProductList = ;

        for(ListItem listItem : proxyProduct.getListItems()){
            if(listItem.getId().getProduct().getId() == productID){
                System.out.println("LISTITEM ID: " + listItem.getId().getProduct().getId());
                System.out.println("ProductID: " + productID);
                listItem.setPrice(proxyProduct.getMedian());
                System.out.println("proxyProduct MEDIAN: " + proxyProduct.getMedian());
                System.out.println("listItem PRICE: " + listItem.getPrice());
                proxyProduct.setUserPrice(price.getPriceValue());
            }
        }

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(tempPrice.getId()).toUri();
        return ResponseEntity.created(uri).build();

    }

    @PutMapping("/{id}")
    public ResponseEntity<Price> update(@PathVariable("id") Long id, @RequestBody Price price){
        Price obj = this.priceService.update(id, price);
        return ResponseEntity.ok().body(obj);
    }


}
