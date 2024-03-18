package leoegito.listaOrganica.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.ArrayList;
import java.util.SortedSet;
import java.util.TreeSet;

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

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "product_id", nullable = false)
//    @JsonIgnore
//    private Product product;
    private SortedSet<Price> prices = new TreeSet<>();

    public void addPrice(Double price) throws IllegalArgumentException{
        if(price > 1.3 * this.getMaximumValue() || price < 0.7 * this.getMinimumValue()){
            throw new IllegalArgumentException("Price is too high or too low.");
        }
        prices.add(new Price(null, price));
//        prices.add(new Price(null, price, this));
    }

    public void enforceAddPrice(Double price){
//        prices.add(new Price(null, price, this));
        prices.add(new Price(null, price));
    }

    //get minimum value by getting the first value of the sorted set
    public Double getMinimumValue(){
        return this.prices.first().getPriceValue();
    }

    //get maximum value by getting the last value of the sorted set
    public Double getMaximumValue(){
        return this.prices.last().getPriceValue();
    }

    public double getMedian(){
        ArrayList<Double> priceList = new ArrayList<>();
        //Add only the double values from Price objects
        for(Price allPrices : this.prices){
            priceList.add(allPrices.getPriceValue());
        }

        //Size is important for the calculus
        int size = priceList.size();

        //Error check
        if(size == 0){
            throw new IllegalStateException("There is no price registered for this item.");
        }
        //If is odd
        if(size % 2 == 1){
            // Odd size, mid value is the Median
            return priceList.get(size/2);
        } else {
            // Even size, two center points/mid values considered to calculate the Median
            int mid1 = size / 2 - 1;
            int mid2 = size / 2;
            return (priceList.get(mid1) + priceList.get(mid2)) / 2.0;
        }
    }

    @Override
    public int compareTo(Price anotherPrice){
        return Double.compare(this.priceValue, anotherPrice.priceValue);
    }

}
