package com.project.ecommerce.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.ecommerce.auditing.Auditable;
import com.sun.istack.NotNull;

import javax.persistence.*;
import javax.validation.constraints.Pattern;

@Entity
public class Address extends Auditable<String> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long addressId;

    @NotNull
    @Pattern(regexp = "^[a-zA-Z]{1,20}$",message = "field city can only contains alphabets")
    private String city;

    @NotNull
    @Pattern(regexp = "^[a-zA-Z]{1,20}$",message = "field state can only contains alphabets")
    private String state;

    @NotNull
    @Pattern(regexp = "^[a-zA-Z]{1,20}$",message = "field country can only contains alphabets")
    private String country;

    @NotNull
    private String address;

    @NotNull
    @Pattern(regexp = "^[0-9]{1,6}$",message = "field zipcode can only contains numeric")
    private String zipcode;

    private String Label;

    @OneToOne
    @JoinColumn(name = "seller_user_id")
    private Seller seller;


    @ManyToOne
    @JoinColumn(name = "customer_user_id")
    @JsonIgnore
    private Customer customer;


    public Address( String city, String state, String country,
                    String address, String zipcode, String label) {

        this.city = city;
        this.state = state;
        this.country = country;
        this.address = address;
        this.zipcode = zipcode;
        Label = label;
    }


    public Address() { }


    public Long getAddressId() {
        return addressId;
    }

    public void setAddressId(Long addressId) {
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
