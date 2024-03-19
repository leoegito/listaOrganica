package leoegito.listaOrganica.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tb_price")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Price implements Comparable<Price>{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "price_id", unique = true)
    private Long id;

    @NonNull
    private Double priceValue;

    @JoinColumn(name = "product_id", nullable = false)
    @JsonIgnore
    @ManyToOne
    private Product product;

    public Price(Double priceValue, Product product){
        this.priceValue = priceValue;
        this.product = product;
    }

    @Override
    public int compareTo(Price anotherPrice){
        return Double.compare(this.priceValue, anotherPrice.priceValue);
    }

}
