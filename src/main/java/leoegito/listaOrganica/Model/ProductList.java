package leoegito.listaOrganica.Model;


import com.fasterxml.jackson.annotation.*;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import java.util.*;

@Entity
@Table(name="tb_product_list")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(exclude = "listItems")
@Getter
@Setter
//@NotFound(action = NotFoundAction.IGNORE)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class ProductList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

//    @ManyToMany(fetch = FetchType.LAZY)
//    @Nullable
//    private Set<Product> products = new HashSet<>();
//    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    //TESTE DE SIMPLIFICAÇÃO DE RELACIONAMENTOS 02/06 - mappedBy = id.productList
    @OneToMany(mappedBy = "id.productList", cascade = CascadeType.ALL, orphanRemoval = true)
    @Nullable
//    @JsonBackReference
    @JsonIdentityReference(alwaysAsId = true) // Adicione esta linha
//    @JsonProperty("listItems") // Adicione esta linha
//    @NotFound(action = NotFoundAction.IGNORE)
//    private Set<ListItem> listItems = new LinkedHashSet<>();
    private List<ListItem> listItems = new LinkedList<>();

    //Maybe its better to just store the two results in two attributes

    public Double[] getTotalValue(){
        Double total = 0.0;
        Double medianTotal = 0.0;
        Double[] results = new Double[2];
//        for(Product product : this.products){
//            total += product.getMinimumValue();
//            medianTotal += product.getMedian();
//        }
        for(ListItem listItem : this.listItems){
            total += listItem.getSubTotal();
            medianTotal += (listItem.getProduct().getMedian() * listItem.getQuantity());
        }
        results[0] = total;
        results[1] = medianTotal;
        return results;
    }

//    public Double getMedianTotalValue(){
//        Double total = 0.0;
//        for(Product product : this.products){
//            total += product.getMedian();
//        }
//        return total;
//    }

}
