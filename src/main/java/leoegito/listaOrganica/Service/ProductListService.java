package leoegito.listaOrganica.Service;

import jakarta.persistence.EntityNotFoundException;
import leoegito.listaOrganica.Model.Product;
import leoegito.listaOrganica.Model.ProductList;
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

    public ProductList insertProduct(Long id, Product product) {
        this.productListRepository.getReferenceById(id).getProducts().add(product);
    }


}
