package com.project.ecommerce.entity;

import javax.persistence.*;

@Entity
public class OrderProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private int quantity;
    private int price;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Orders orders;
    @OneToOne
    @JoinColumn(name = "product_variation_id")
    private ProductVariant productVariant;

    private String productVariationMetadata;

    //******************************************************************************************************
    public Long getId() {
        return id;
    }


    public void setId(Long id) {
        this.id = id;
    }


    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Orders getOrders() {
        return orders;
    }

    public void setOrders(Orders orders) {
        this.orders = orders;
    }

    public ProductVariant getProductVariant() {
        return productVariant;
    }

    public void setProductVariant(ProductVariant productVariant) {
        this.productVariant = productVariant;
    }

    public String getProductVariationMetadata() {
        return productVariationMetadata;
    }

    public void setProductVariationMetadata(String productVariationMetadata) {
        this.productVariationMetadata = productVariationMetadata;
    }

    @Override
    public String toString() {
        return "OrderProduct{" +
                "id=" + id +
                ", quantity=" + quantity +
                ", price=" + price +
                ", orders=" + orders +
                ", productVariant=" + productVariant +
                ", productVariationMetadata='" + productVariationMetadata + '\'' +
                '}';
    }
}
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Integer opId;
//    private Integer quantity;
//    private double price;
//
////    private Orders orderId;
////    private ProductVariant pid;
////    private ProductVariant prodMetaData;
