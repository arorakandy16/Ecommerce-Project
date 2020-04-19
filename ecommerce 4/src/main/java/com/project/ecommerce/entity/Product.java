package com.project.ecommerce.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;
    @NotNull
    @Column(unique = true)
    private String productName;
    private String description;
    @NotNull
    private String brand;
    private boolean is_cancellable=false;
    private boolean is_returnable=false;
    private boolean is_active=false;
    private boolean is_deleted=false;

    //------------Product and Seller Mapping -------------------------------------
    @ManyToOne
    @JoinColumn(name = "seller_user_id")
    private Seller seller;

    //-------------------Product and ProductCategory Mapping--------------------
    @ManyToOne
    @NotNull
//    @JsonIgnore
    @JoinColumn(name = "category_id")
    private ProductCategory productcategory;

    public Product(String productName, String description, String brand, boolean is_cancellable, boolean is_returnable, boolean is_active) {
        this.productName = productName;
        this.description = description;
        this.brand = brand;
        this.is_cancellable = is_cancellable;
        this.is_returnable = is_returnable;
        this.is_active = is_active;
    }

    public Product() {
    }

//    ------------------Product and Productvariation Mapping--------------
    @OneToMany(mappedBy = "product")
    @JsonIgnore
    List<ProductVariant> productVariants;

//    --------------Product and Product Review Mapping----------------------
    @OneToMany(mappedBy = "product")
    private List<ProductReview> product_reviews;


    
    public List<ProductVariant> getProductVariants() {
        return productVariants;
    }

    public void setProductVariants(List<ProductVariant> productVariants) {
        this.productVariants = productVariants;
    }

    

    public ProductCategory getProductcategory() {
        return productcategory;
    }

    public void setProductcategory(ProductCategory productcategory) {
        this.productcategory = productcategory;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public boolean isIs_cancellable() {
        return is_cancellable;
    }

    public void setIs_cancellable(boolean is_cancellable) {
        this.is_cancellable = is_cancellable;
    }

    public boolean isIs_returnable() {
        return is_returnable;
    }

    public void setIs_returnable(boolean is_returnable) {
        this.is_returnable = is_returnable;
    }

    public boolean isIs_active() {
        return is_active;
    }

    public void setIs_active(boolean is_active) {
        this.is_active = is_active;
    }

    public boolean isIs_deleted() {
        return is_deleted;
    }

    public void setIs_deleted(boolean is_deleted) {
        this.is_deleted = is_deleted;
    }

    public Seller getSeller() {
        return seller;
    }

    public void setSeller(Seller seller) {
        this.seller = seller;
    }

}