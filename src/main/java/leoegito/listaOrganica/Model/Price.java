package leoegito.listaOrganica.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tb_price")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Price implements Comparable<Price>{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "price_id", unique = true, insertable = false, updatable = false)
    private Long id;

    @NotNull
    private Double priceValue;

    public Price(Double value){
        this.priceValue = value;
    }

    @Override
    public int compareTo(Price anotherPrice){
        return Double.compare(this.priceValue, anotherPrice.priceValue);
    }

}
