package leoegito.listaOrganica.Service;

import jakarta.persistence.EntityNotFoundException;
import leoegito.listaOrganica.Model.Price;
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
public class PriceService {

    @Autowired
    private PriceRepository priceRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    public PriceService(PriceRepository priceRepository){
        this.priceRepository = priceRepository;
    }

    public Price save(Price price){
        return this.priceRepository.save(price);
    }

    public Price insert(Long productID, Price price){
        Price temp = this.productRepository.findById(productID).map(
                product -> {
                    product.addPrice(price);
                    product.setUserPrice(price.getPriceValue());
                    return this.save(price);
                }).orElseThrow(
                () -> new ResourceNotFoundException(productID)
        );
        return temp;
    }

    public Price insertAdmin(Long productID, Price price){
        Price temp = this.productRepository.findById(productID).map(
                product -> {
                    product.enforceAddPrice(price.getPriceValue());
                    product.setUserPrice(price.getPriceValue());
                    return this.save(price);
                }).orElseThrow(
                () -> new ResourceNotFoundException(productID)
        );
        return temp;
    }

    public Price update(Long id, Price price){
        try{
            this.priceRepository.getReferenceById(id);
            return this.priceRepository.save(price);
        } catch (EntityNotFoundException e){
            throw new ResourceNotFoundException(id);
        }
    }

    public void delete(Long id){
        try{
            this.priceRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e){
            throw new ResourceNotFoundException(id);
        //TODO - refactor
        } catch (DataIntegrityViolationException e){
            throw new DatabaseException(e.getMessage());
        }

    }

    public List<Price> findAll(){
        return this.priceRepository.findAll();
    }

    public Price findByID(Long id){
        Optional<Price> obj = priceRepository.findById(id);
        return obj.orElseThrow(
                () -> new ResourceNotFoundException(id)
        );
    }


}
