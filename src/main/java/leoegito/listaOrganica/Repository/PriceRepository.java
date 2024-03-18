package leoegito.listaOrganica.Repository;


import leoegito.listaOrganica.Model.Price;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.stereotype.Repository;

//@NoRepositoryBean
@Repository
public interface PriceRepository  extends JpaRepository<Price, Long> {
}
