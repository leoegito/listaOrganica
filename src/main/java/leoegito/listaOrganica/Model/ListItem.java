package leoegito.listaOrganica.Model;

import jakarta.persistence.*;
import leoegito.listaOrganica.Model.PK.ListItemPK;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tb_list_item")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ListItem {

//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;

    @EmbeddedId
    private ListItemPK id = new ListItemPK();

    private Integer quantity;
    private Double price;

    public ListItem(ProductList productList, Product product, Integer quantity, Double price){
        super();
        this.id.setProductList(productList);
        this.id.setProduct(product);
        this.quantity = quantity;
        this.price = price;
    }


}
