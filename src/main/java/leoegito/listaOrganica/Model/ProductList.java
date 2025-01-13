package leoegito.listaOrganica.Model;


import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.*;

import java.util.LinkedList;
import java.util.List;

@Entity
@Table(name="tb_product_list")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(exclude = "listItems")
@Getter
@Setter
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class ProductList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @OneToMany(mappedBy = "id.productList", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @Nullable
    @JsonIdentityReference(alwaysAsId = true) // Adicione esta linha
    private List<ListItem> listItems = new LinkedList<>();

    public Double[] getTotalValue(){
        Double total = 0.0;
        Double medianTotal = 0.0;
        Double[] results = new Double[2];

        for(ListItem listItem : this.listItems){
            total += listItem.getSubTotal();
            medianTotal += (listItem.getProduct().getMedian() * listItem.getQuantity());
        }
        results[0] = total;
        results[1] = medianTotal;
        return results;
    }

}
