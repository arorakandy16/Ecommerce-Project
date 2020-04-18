package com.project.ecommerce.Entities;

//import com.project.ecommerce.metadata.HashMapConverter;
//import org.json.JSONObject;

import javax.persistence.*;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.TreeMap;

@Entity
public class  ProductVariant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productVariation_Id;
    private Integer quantityAvailable;
    private double price;
    private String primary_Image_Name;

    //----------------Product and Product Variant--------------------------------
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "product_id")
    private Product product;
//    @Column()
    private String metadata;

//    @Convert(converter = HashMapConverter.class)
//    @Convert(converter = HashMapConverter.class)
//    private JSONObject customerAttributes;

    //    **************************************
//    //--------Product variation and cart-------------------------
//    @ManyToOne
//    private Cart cart;


    public Long getProductVariation_Id() {
        return productVariation_Id;
    }

    public void setProductVariation_Id(Long productVariation_Id) {
        this.productVariation_Id = productVariation_Id;
    }

   

    public Integer getQuantityAvailable() {
        return quantityAvailable;
    }

    public void setQuantityAvailable(Integer quantityAvailable) {
        this.quantityAvailable = quantityAvailable;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getPrimary_Image_Name() {
        return primary_Image_Name;
    }

    public void setPrimary_Image_Name(String primary_Image_Name) {
        this.primary_Image_Name = primary_Image_Name;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

}
