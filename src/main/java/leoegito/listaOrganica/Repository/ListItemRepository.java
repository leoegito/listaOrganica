package leoegito.listaOrganica.Repository;

import leoegito.listaOrganica.Model.ListItem;
//import leoegito.listaOrganica.Model.PK.ListItemPK;
import leoegito.listaOrganica.Model.PK.ListItemPK;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ListItemRepository extends JpaRepository<ListItem, ListItemPK> {
}
