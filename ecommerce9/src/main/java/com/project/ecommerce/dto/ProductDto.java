package com.project.ecommerce.dto;

public class ProductDto {

    private String productName;
    private String description;
    private String brand;
    private boolean is_cancellable;
    private boolean is_returnable;

    public ProductDto(String productName, String description, String brand,
                      boolean is_cancellable, boolean is_returnable) {

        this.productName = productName;
        this.description = description;
        this.brand = brand;
        this.is_cancellable = is_cancellable;
        this.is_returnable = is_returnable;
    }

    public ProductDto() { }

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
}
