package leoegito.listaOrganica.Controller;

import leoegito.listaOrganica.Model.ListItem;
import leoegito.listaOrganica.Model.Product;
import leoegito.listaOrganica.Model.ProductList;
import leoegito.listaOrganica.Repository.ListItemRepository;
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

    @Autowired
    private ListItemRepository listItemRepository;

    @GetMapping("/list")
    public ResponseEntity<List<ProductList>> findAll(){
        List<ProductList> list = productListService.findAll();
        return ResponseEntity.ok().body(list);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<ProductList> findByID(@PathVariable Long id){
        ProductList obj = productListService.findByID(id);
        for(ListItem listItem : obj.getListItems()){
            System.out.println("PRODUCTLIST - ListItem Price ANTES: " + listItem.getPrice());
            System.out.println("PRODUCTLIST - ListItem Product MEDIAN ANTES: " + listItem.getId().getProduct().getMedian());
            listItem.setPrice(listItem.getId().getProduct().getMedian());
            System.out.println("PRODUCTLIST - ListItem Price DEPOIS: " + listItem.getPrice());
        }
        return ResponseEntity.ok().body(obj);
    }

    @PostMapping("/create")
    public ResponseEntity<ProductList> insert(@RequestBody ProductList productList){
        ProductList obj = this.productListService.save(productList);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(obj.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @PostMapping("/{id}/add-product")
    public ResponseEntity<ProductList> addProductToList(@PathVariable(value = "id") Long id, @RequestBody Product product){
        Optional<ProductList> optionalProductList = productListService.insertProduct(id, product);
        if(!optionalProductList.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(optionalProductList.get());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductList> update(@PathVariable(value = "id") Long id, ListItem listItem){
        ProductList list = this.productListService.findByID(id);
        list.getListItems().add(listItem);
        return ResponseEntity.ok().body(list);
    }

    @PutMapping("/{id}/addQuantity")
    public ResponseEntity<ProductList> changeQuantity(@PathVariable Long id, @RequestBody Product product){
        ProductList productList = this.productListService.findByID(id);
        List<ListItem> items = productList.getListItems();

        for(ListItem item : items){
            if(item.getId().getProduct().getId().equals(product.getId())){
                item.setQuantity((item.getQuantity()+1));
            }
        }
        this.productListService.save(productList);
        return ResponseEntity.ok().body(productList);
    }

    @PutMapping("/{listId}/subtractQuantity")
    public ResponseEntity<ProductList> subtractQuantity(@PathVariable Long listId, @RequestBody Product product) {
        ProductList productList = productListService.findByID(listId);
        List<ListItem> items = productList.getListItems();
        ListItem itemToRemove = null;

        for (ListItem item : items) {
            if (item.getId().getProduct().getId().equals(product.getId())) {
                item.setQuantity(item.getQuantity() - 1);
                if (item.getQuantity() == 0) {
                    itemToRemove = item;
                }
                break; // Já encontramos o item, então podemos sair do loop
            }
        }

        if (itemToRemove != null) {
            items.remove(itemToRemove);
        }

        productListService.save(productList);
        return ResponseEntity.ok().body(productList);
    }


    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        this.productListService.delete(id);
    }


}
