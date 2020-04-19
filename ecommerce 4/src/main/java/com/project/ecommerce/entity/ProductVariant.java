package com.project.ecommerce.entity;

import com.project.ecommerce.metadata.HashMapConverter;
import javax.persistence.*;
import java.io.File;
import java.util.Map;

@Entity
public class  ProductVariant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long quantityAvailable;
    private Integer price;

    @Convert(converter = HashMapConverter.class)
    @Column(columnDefinition = "json")
    private Map<String,String> metaData;

    private File productVariationImage= new File("/static/productVariation/id.jpeg");


    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(name = "is_active")
    private boolean isActive=true;

    public File getProductVariationImage() {
        return productVariationImage;
    }

    public void setProductVariationImage(File productVariationImage) {
        this.productVariationImage = productVariationImage;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Long getQuantityAvailable() {
        return quantityAvailable;
    }

    public void setQuantityAvailable(Long quantityAvailable) {
        this.quantityAvailable = quantityAvailable;
    }

    public Map<String, String> getMetaData() {
        return metaData;
    }

    public void setMetaData(Map<String, String> metaData) {
        this.metaData = metaData;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    @Override
    public String toString() {
        return "ProductVariation{" +
                "id=" + id +
                ", quantityAvailable=" + quantityAvailable +
                ", price=" + price +
                ", metaData=" + metaData +
                ", productVariationImage=" + productVariationImage +
                ", product=" + product +
                ", isActive=" + isActive +
                '}';
    }
}