package leoegito.listaOrganica.Model;


import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="tb_product_list")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Getter
@Setter
public class ProductList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToMany(fetch = FetchType.LAZY)
    @Nullable
    private Set<Product> products = new HashSet<>();

    //Maybe its better to just store the two results in two attributes

    public Double[] getTotalValue(){
        Double total = 0.0;
        Double medianTotal = 0.0;
        Double[] results = new Double[2];
        for(Product product : this.products){
            total += product.getMinimumValue();
            medianTotal += product.getMedian();
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
