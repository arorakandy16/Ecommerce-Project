package com.project.ecommerce.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@PrimaryKeyJoinColumn(name = "user_id")
public class Seller extends User{
//public class Seller extends User implements Serializable{

   @NotNull
    private String GST;

   @NotNull
    private Long companyContact;

   @NotNull
    private String companyName;


    //-------Seller and Product mapping-------
    @OneToMany(mappedBy = "seller",fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Product> products;


    public Seller() {
        this.addRole(new Role(2l, "ROLE_SELLER"));
    }


    public Seller(String email, String firstName,
                  String middleName, String lastName,
                  String GST, String companyName,
                  Long companyContact) {

        super(email, firstName, middleName, lastName);

        this.GST = GST.toUpperCase();
        this.companyName = companyName;
        this.companyContact = companyContact;
        this.addRole(new Role(2l, "ROLE_SELLER"));
    }


    //-------------------------------------------------------------------------------------------

    public void addProduct(Product product){
        if(product != null){
            if(products == null)
                products = new ArrayList<>();
            products.add(product);

            product.setSeller(this);
        }
    }

    //-------------------------------------------------------------------------------------------


    public String getGST() {
        return GST;
    }

    public void setGST(String GST) {
        this.GST = GST;
    }

    public Long getCompanyContact() {
        return companyContact;
    }

    public void setCompanyContact(Long companyContact) {
        this.companyContact = companyContact;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
    
    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    @Override
    public String toString() {
        return "Seller{" +
                "GST=" + GST +
                ", companyContact=" + companyContact +
                ", companyName='" + companyName + '\'' +
                ", products=" + products +
                '}';
    }
}
