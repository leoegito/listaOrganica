package leoegito.listaOrganica.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import leoegito.listaOrganica.Model.PK.ListItemPK;
import lombok.*;

import java.util.Objects;

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

//    Antes de 02/06
    @EmbeddedId
    private ListItemPK id = new ListItemPK();

    //TESTES DE SIMPLIFICAÇÃO DE RELACIONAMENTO - 02/06
//    @EmbeddedId
//    private Long id;

//    @ManyToOne
////    @MapsId("productListId")
//    @JoinColumn(name = "product_list_id")
//    private ProductList productList;
//
//    @ManyToOne
////    @MapsId("productId")
//    @JoinColumn(name = "product_id")
//    private Product product;

    //FIM DOS TESTES 02/06
    private Integer quantity;
    private Double price;

    public ListItem(ProductList productList, Product product, Integer quantity, Double price){
        super();
        this.id.setProductList(productList);
        this.id.setProduct(product);
        this.quantity = quantity;
        this.price = price;
    }

    public ProductList getProductList(){
        return this.id.getProductList();
    }

    public void setProductList(ProductList productList){
        this.id.setProductList(productList);
    }

    public Product getProduct(){
        return this.id.getProduct();
    }

    public void setProduct(Product product){
        this.id.setProduct(product);
    }

    public Double getSubTotal(){
        return this.price * this.quantity;
    }

    @Override
    public int hashCode(){
        return Objects.hash(this.id);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ListItem listItem = (ListItem) o;
        return Objects.equals(id, listItem.id);
    }

}
