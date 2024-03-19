package leoegito.listaOrganica.Model;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.SortedSet;
import java.util.TreeSet;


@Entity
@Table(name = "tb_product")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id", unique = true)
    private Long id;
    private String name;
    private String description;
    @Nullable
    private Double globalMediumPrice = null;

//    @Nullable
//    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
//    @JoinColumn(name="price_id", referencedColumnName = "price_id")
//    private Price medianPrice;
    @Nullable
    private Double userPrice = null;

//    @JsonIgnore
//    @Getter(onMethod_=@JsonIgnore)
//    @JoinTable(name = "product_prices")
//    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "product")
//    @JoinColumn(name = "price_id")
    @Nullable
    private SortedSet<Price> prices = new TreeSet<>();

    public Product(String name, String description, Double userPrice){
        this.name = name;
        this.description = description;
        this.globalMediumPrice = 0.0;
//        this.medianPrice = null;
        if(userPrice != null){
            this.userPrice = userPrice;
        }
    }

    //Methods
    public void addPrice(Double price) throws IllegalArgumentException{
        if(price > 1.3 * this.getMaximumValue() || price < 0.7 * this.getMinimumValue()){
            throw new IllegalArgumentException("Price is too high or too low.");
        }
        prices.add(new Price(price, this));
//        prices.add(new Price(null, price, this));
    }

    public void enforceAddPrice(Double price){
//        prices.add(new Price(null, price, this));
        prices.add(new Price(price, this));
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


}
