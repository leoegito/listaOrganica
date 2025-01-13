package leoegito.listaOrganica.Model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.*;


@Entity
@Table(name = "tb_product")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = "listItems")
@JsonIdentityReference(alwaysAsId = false)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id", unique = true)
    private Long id;
    @NotNull
    private String name;

    @NotNull
    private String description;
    @Nullable
    private Double globalMediumPrice = 0.0;

    @Nullable
    private Double userPrice = 0.0;

    @OneToMany(mappedBy = "id.product")
//    @JsonIdentityReference(alwaysAsId = true) //
    @JsonIgnore
    private Set<ListItem> listItems = new LinkedHashSet<>();

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    @JoinColumn(name = "product_id")
    private SortedSet<Price> prices = new TreeSet<>();

    public Product(String name, String description, Double userPrice){
        this.name = name;
        this.description = description;
        if(userPrice != null){
            this.userPrice = userPrice;
            this.addPrice(new Price(this.userPrice));
        } else {
            this.userPrice = 0.0;
            this.enforceAddPrice(this.userPrice);
        }
    }

    //Methods
    public void addPrice(Price price) throws IllegalArgumentException{
        if(price.getPriceValue() < 0){
            throw new IllegalArgumentException("Preço não pode ser negativo");
        }
        for(Price checkPrice : this.getPrices()){
            if(checkPrice.getPriceValue() <= 0.0){
                this.getPrices().remove(checkPrice);
            }
        }
        if(this.getMinimumValue() > 0.0 || this.getMinimumValue() > 0.0){
            if(price.getPriceValue() > 2.0 * this.getMaximumValue() || price.getPriceValue() < 0.5 * this.getMinimumValue()){
                throw new IllegalArgumentException("Price is too high or too low.");
            }
        }

        prices.add(price);

    }

    public void enforceAddPrice(Double price){

        Price proxyPrice = new Price(price);

        prices.add(proxyPrice);
    }

    //get minimum value by getting the first value of the sorted set
    public Double getMinimumValue(){
        if(this.prices.size() != 0){
            return this.prices.first().getPriceValue();
        }
        return 0.0D;
    }

    //get maximum value by getting the last value of the sorted set
    public Double getMaximumValue(){
        if(this.prices.size() != 0){
            return this.prices.last().getPriceValue();
        }
        return 0.0D;
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

    @JsonIgnore
    public Set<ProductList> getProductLists(){
        Set<ProductList> set = new HashSet<>();
        for(ListItem x : listItems){
            set.add(x.getProductList());
        }
        return set;
    }


}
