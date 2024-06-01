package leoegito.listaOrganica.Model.PK;


import jakarta.persistence.*;
import leoegito.listaOrganica.Model.Product;
import leoegito.listaOrganica.Model.ProductList;
import lombok.*;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class ListItemPK implements Serializable {

//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
    private static final long serialVersionUID = 1L;

    @ManyToOne
    @JoinColumn(name = "product_list_id")
    private ProductList productList;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        ListItemPK that = (ListItemPK) o;
//        return Objects.equals(productList, that.productList) && Objects.equals(product, that.product);
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(productList, product);
//    }

}
