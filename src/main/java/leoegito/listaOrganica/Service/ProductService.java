package leoegito.listaOrganica.Service;

import leoegito.listaOrganica.Model.Product;
import leoegito.listaOrganica.Repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    private ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository){
        this.productRepository = productRepository;
    }



    public Product save(Product product){
        return this.productRepository.save(product);
    }

    public Product update(Long id, Product product){
        //TODO - verify if id exists
        //Product updatedProduct = this.productRepository.save(product);
        return this.productRepository.save(product);
    }

}
