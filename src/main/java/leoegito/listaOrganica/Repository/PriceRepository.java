package leoegito.listaOrganica.Repository;


import leoegito.listaOrganica.Model.Price;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PriceRepository  extends JpaRepository<Price, Long> {
}
