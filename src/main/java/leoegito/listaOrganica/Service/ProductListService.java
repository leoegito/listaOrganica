package leoegito.listaOrganica.Service;

import jakarta.persistence.EntityNotFoundException;
import leoegito.listaOrganica.Model.ListItem;
import leoegito.listaOrganica.Model.PK.ListItemPK;
import leoegito.listaOrganica.Model.Product;
import leoegito.listaOrganica.Model.ProductList;
import leoegito.listaOrganica.Repository.ListItemRepository;
import leoegito.listaOrganica.Repository.ProductListRepository;
import leoegito.listaOrganica.Service.Exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductListService {

    @Autowired
    private ProductListRepository productListRepository;

    @Autowired
    private ListItemRepository listItemRepository;

    @Autowired
    private ProductService productService;

    public List<ProductList> findAll() {
        return this.productListRepository.findAll();
    }

    public ProductList findByID(Long id) {
        Optional<ProductList> obj = this.productListRepository.findById(id);
        return obj.get();
//        return obj.orElseThrow(
//                () -> new ResourceNotFoundException(id)
//        );
    }

    public ProductList save(ProductList productList) {
        return this.productListRepository.save(productList);
    }

    public ProductList update(Long id, ProductList productList) {
        try {
            this.productListRepository.getReferenceById(id);
            return this.productListRepository.save(productList);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException(id);
        }
    }

    public Optional<ProductList> insertProduct(Long id, Product product) throws ResourceNotFoundException {
        Optional<ProductList> optionalProductList = productListRepository.findById(id);
//        if(optionalProductList.isPresent()){
//
//        }
//        ProductList proxyList = optionalProductList.orElseThrow();

//
//        ProductList productList = this.productListRepository.getReferenceById(id);
//        productList.getProducts().add(product);
        Product realProduct = this.productService.findByID(product.getId());

        if(optionalProductList.isPresent()){
            ProductList productList = optionalProductList.get();
//            ListItemPK listItemPK = new ListItemPK(productList, product);
            ListItem listItem = new ListItem(productList, realProduct, 1, realProduct.getMedian());
            this.listItemRepository.save(listItem);
            productList.getListItems().add(listItem);
            productListRepository.save(productList);
        } else {
            throw new RuntimeException("DEU MERDA AQUI Ó");
        }
//        return productListRepository.save(productList);
//        Optional<ProductList> optionalProductList = productListRepository.findById(id);
        return optionalProductList;
    }


}
