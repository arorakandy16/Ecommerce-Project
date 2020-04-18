package com.project.ecommerce.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Entity
public class ProductCategory {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pc_id;

    @NotNull
    @Column(unique = true)
    private String name;

    @OneToMany(mappedBy = "productcategory",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<Product> products;


    @JsonIgnore
    @OneToMany(mappedBy = "parentCategory",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private Set<ProductCategory> leafCategory;   //->childCategory

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private ProductCategory parentCategory;


    public ProductCategory(String name) {
        this.name=name;
    }

    //    @JsonIgnore
    @OneToMany(mappedBy = "productCategory", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<CategoryMetadataFieldValues> fieldValues;


//------------------------------------------------------------------------

    public ProductCategory(){

    }

//    public Set<Product> getProducts() {
//        return products;
//    }
//
//    public void setProducts(Set<Product> products) {
//        this.products = products;
//    }

    public Set<ProductCategory> getLeafCategory() {
        return leafCategory;
    }

    public void setLeafCategory(Set<ProductCategory> leafCategory) {
        this.leafCategory = leafCategory;
    }

    public ProductCategory getParentCategory() {
        return parentCategory;
    }

    public void setParentCategory(ProductCategory parentCategory) {
        this.parentCategory = parentCategory;
    }

    public Long getPc_id() {
        return pc_id;
    }

    public void setPc_id(Long pc_id) {
        this.pc_id = pc_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Product> getProducts() {
        return products;
    }

    public void setProducts(Set<Product> products) {
        this.products = products;
    }

    public Set<CategoryMetadataFieldValues> getFieldValues() {
        return fieldValues;
    }

    public void setFieldValues(Set<CategoryMetadataFieldValues> fieldValues) {
        this.fieldValues = fieldValues;
    }

    public void addLeafCategory(ProductCategory category){
        if(category != null){
            if(leafCategory == null)
                leafCategory = new HashSet<>();

            leafCategory.add(category);
            category.setParentCategory(this);
        }
    }


    public void addProduct(Product product){
        if(product != null){
            if(products == null)
                products = new HashSet<Product>();

            products.add(product);

            product.setProductcategory(this);
        }
    }
    public void addFieldValues(CategoryMetadataFieldValues fieldValue){
        if(fieldValue != null){
            if(fieldValues==null)
                fieldValues = new HashSet<>();

            fieldValues.add(fieldValue);
            fieldValue.setProductCategory(this);

        }
    }
    @Override
    public String toString() {
        return "ProductCategory{" +
                "pc_id=" + pc_id +
                ", name='" + name + '\'' +
//                ", products=" + products +
                '}';
    }
}
