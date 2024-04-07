package leoegito.listaOrganica.Repository;

import leoegito.listaOrganica.Model.ProductList;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductListRepository extends JpaRepository<ProductList, Long> {
}
