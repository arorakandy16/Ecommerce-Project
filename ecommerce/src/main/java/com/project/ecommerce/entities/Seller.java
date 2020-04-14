package com.project.ecommerce.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

@Entity
@PrimaryKeyJoinColumn(name = "user_id")
public class Seller extends User {

   
    private String GST;
    private Integer companyContact;
    private String companyName;

    //-------Seller and Product mapping-------
    @OneToMany(mappedBy = "seller",fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Product> products;
    

    //----Seller and Address mapping-------------
    @OneToOne(mappedBy = "seller",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JsonIgnore
    private Address address;

    public Seller() {
        this.addRole(new Role(2, "ROLE_SELLER"));
    }

    public Seller(String email, String firstName, String middleName, String lastName, String GST, String companyName, Integer companyContact) {
        super(email, firstName, middleName, lastName);
        this.GST = GST.toUpperCase();
        this.companyName = companyName;
        this.companyContact = companyContact;
        this.addRole(new Role(2, "ROLE_SELLER"));
    }

    public String getGST() {
        return GST;
    }

    public void setGST(String GST) {
        this.GST = GST;
    }

    public Integer getCompanyContact() {
        return companyContact;
    }

    public void setCompanyContact(Integer companyContact) {
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

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "Seller{" +
                "GST=" + GST +
                ", companyContact=" + companyContact +
                ", companyName='" + companyName + '\'' +
                ", products=" + products +
                ", address=" + address +
                '}';
    }
    public void addProduct(Product product){
        if(product != null){
            if(products == null)
                products = new ArrayList<>();

            products.add(product);

            product.setSeller(this);
        }
    }
}
