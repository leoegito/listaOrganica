package leoegito.listaOrganica.Repository;

import leoegito.listaOrganica.Model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.stereotype.Repository;

//@NoRepositoryBean
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
}
