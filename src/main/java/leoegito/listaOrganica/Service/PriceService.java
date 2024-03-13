package leoegito.listaOrganica.Service;

import leoegito.listaOrganica.Model.Price;
import leoegito.listaOrganica.Repository.PriceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PriceService {

    private PriceRepository priceRepository;

    @Autowired
    public PriceService(PriceRepository priceRepository){
        this.priceRepository = priceRepository;
    }

    public Price save(Price price){
        return this.priceRepository.save(price);
    }

    public Price update(Long id, Price price){
        //TODO - verify if id exists
        return this.priceRepository.save(price);
    }

    public void delete(Long id){
        //TODO - verify if id exists
        this.priceRepository.deleteById(id);
    }

    public List<Price> findAll(){
        return this.priceRepository.findAll();
    }

    public Optional<Price> findByID(Long id){
        return this.priceRepository.findById(id);
    }


}
