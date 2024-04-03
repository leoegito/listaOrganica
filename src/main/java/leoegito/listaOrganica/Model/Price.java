package leoegito.listaOrganica.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
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
    @Column(name = "price_id", unique = true, insertable = false, updatable = false)
    private Long id;

    @NotNull
    private Double priceValue;

    public Price(Double value){
        this.priceValue = value;
    }

//    @JsonIgnore
//    @ManyToOne(targetEntity = Product.class, fetch = FetchType.LAZY)
//    @JoinColumn(name = "product_id", insertable = false, updatable = false)
//    private Product product;


    //Teste somente com ID
//    @ManyToOne(targetEntity = Product.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//    @JoinColumn(name = "product_id")
//    private Product product;
//
//    @Column(name = "product_id")
//    private Long productId;

//    public Price(Double priceValue, Product product){
//        this.priceValue = priceValue;
//        this.product = product;
//    }

//    public void setProduct(Product product) {
//        this.product = product;
//    }
//
//    public Product getProduct(){
//        return this.product;
//    }

//    public Price(Double priceValue, Long productId){
//        this.priceValue = priceValue;
//        this.productId = productId;
//    }

    @Override
    public int compareTo(Price anotherPrice){
        return Double.compare(this.priceValue, anotherPrice.priceValue);
    }

}
