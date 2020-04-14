package com.project.ecommerce.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import javax.persistence.*;

@Entity
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer addressId;
//    @NotEmpty(message = "Please provide a city")
    private String city;
    private String state;
    private String country;
    private String address;
    private String zipcode;
    private String Label;

    //-------------------------------------------------------------
    @OneToOne
    @JoinColumn(name = "seller_user_id")
    private Seller seller;

    
    //---------------------------------------------------------------
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "customer_user_id")
    @JsonIgnoreProperties
    private Customer customer;

    public Address(Integer addressId, String city, String state, String country, String address, String zipcode, String label) {
        this.addressId = addressId;
        this.city = city;
        this.state = state;
        this.country = country;
        this.address = address;
        this.zipcode = zipcode;
        Label = label;
    }

    public Address() {

    }

    public Integer getAddressId() {
        return addressId;
    }

    public void setAddressId(Integer addressId) {
        this.addressId = addressId;
    }
    
    public Seller getSeller() {
        return seller;
    }

    public void setSeller(Seller seller) {
        this.seller = seller;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getLabel() {
        return Label;
    }

    public void setLabel(String label) {
        Label = label;
    }

    @Override
    public String toString() {
        return "Address{" +
                "addressId=" + addressId +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", country='" + country + '\'' +
                ", address='" + address + '\'' +
                ", zipcode='" + zipcode + '\'' +
                ", Label='" + Label + '\'' +
                ", seller=" + seller +
                ", customer=" + customer +
                '}';
    }
}
