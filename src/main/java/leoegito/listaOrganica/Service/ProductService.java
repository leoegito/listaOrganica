package leoegito.listaOrganica.Service;

import jakarta.persistence.EntityNotFoundException;
import leoegito.listaOrganica.Model.Price;
import leoegito.listaOrganica.Model.Product;
import leoegito.listaOrganica.Repository.PriceRepository;
import leoegito.listaOrganica.Repository.ProductRepository;
import leoegito.listaOrganica.Service.Exceptions.DatabaseException;
import leoegito.listaOrganica.Service.Exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private PriceRepository priceRepository;

    @Autowired
    public ProductService(ProductRepository productRepository, PriceRepository priceRepository){
        this.productRepository = productRepository;
        this.priceRepository = priceRepository;
    }

//    @Transactional
    public Product save(Product product){
        Price proxyPrice = new Price(product.getUserPrice());
        this.priceRepository.save(proxyPrice);
        product.addPrice(proxyPrice);
        return this.productRepository.save(product);
    }

    public Product update(Long id, Product product){
        //TODO - verify if id exists
        //** Old
        //** Product updatedProduct = this.productRepository.getReferenceById(id);
        //** return this.productRepository.save(product);

        //Instead of using another method to check and save data, here we'll use
        //the natural function of JPA getReferenceById, save and the
        //EntityNotFoundException catch error.
        try{
            this.productRepository.getReferenceById(id);
            return this.productRepository.save(product);
        } catch (EntityNotFoundException e){
            throw new ResourceNotFoundException(id);
        }

    }

    public void delete(Long id){
        try{
            this.productRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e){
            throw new ResourceNotFoundException(id);
        } catch (DataIntegrityViolationException e){
            throw new DatabaseException(e.getMessage());
        }
    }

    public List<Product> findAll(){
        return this.productRepository.findAll();
    }

    public Product findByID(Long id){
        Optional<Product> obj = productRepository.findById(id);
        return obj.orElseThrow(
                () -> new ResourceNotFoundException(id)
        );
    }

}
