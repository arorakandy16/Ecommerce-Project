package com.project.ecommerce.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Entity
@PrimaryKeyJoinColumn(name = "user_id")
public class Customer extends User{
    
    private String contact;

    public Customer(){
        this.addRole(new Role(3, "ROLE_CUSTOMER"));
    }

    public Customer(String email, String firstName, String middleName, String lastName, String contact) {
    super(email, firstName, middleName, lastName);
    this.addRole(new Role(3,"ROLE_CUSTOMER"));
    this.contact = contact;
}

    //---------Customer and Orders mapping----------------------------
//    @OneToMany(mappedBy ="customer",cascade = CascadeType.ALL)
////    @JsonIgnore
//    private List<Orders> orders;

    //--------------Customer and Product Review Mapping-------------------------
//    @OneToMany(mappedBy = "customer",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
//    @JsonIgnore
//    private List<ProductReview> product_reviews;


    //-----Customer and Address Mapping-------
//    @OneToMany(mappedBy = "customer",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
//    @JsonIgnore
//    private List<Address> addresses;

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

//    public List<Orders> getOrders() {
//        return orders;
//    }
//
//    public void setOrders(List<Orders> orders) {
//        this.orders = orders;
//    }
//----------Cart and Customer are linked through inheritence--------------------
//
//    public List<ProductReview> getProduct_reviews() {
//        return product_reviews;
//    }
//
//    public void setProduct_reviews(List<ProductReview> product_reviews) {
//        this.product_reviews = product_reviews;
//    }
//
//    public List<Address> getAddress() {
//        return address;
//    }
//
//    public void setAddress(List<Address> address) {
//        this.address = address;
//    }
//
//    public List<Orders> getOrders() {
//        return orders;
//    }
//
//    public void setOrders(List<Orders> orders) {
//        this.orders = orders;
//    }

    @Override
    public String toString() {
        return "Customer{" +
                "contact='" + contact + '\'' +
//                ", orders=" + orders +
//                ", addresses=" + addresses +
                '}';
    }

//    public void addAddress(Address address){
//    if(address!=null){
//        if(addresses == null)
//            addresses = new ArrayList<>();
//
//        System.out.println("address added");
//        address.setCustomer(this);
//        addresses.add(address);
//    }
//}

}
