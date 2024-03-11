package leoegito.listaOrganica.Model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tb_price")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Price {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "price_id", unique = true)
    private Long id;

    @NonNull
    private Double priceValue;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @Override
    public int compareTo(Price anotherPrice){
        return Double.compare(this.priceValue, anotherPrice.priceValue);
    }

}
