package com.project.ecommerce.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Entity
@PrimaryKeyJoinColumn(name = "user_id")
public class Customer extends User{

    private Long contact;

    public Customer(){
        this.addRole(new Role(3l, "ROLE_CUSTOMER"));
    }

    public Customer(String email, String firstName, String middleName, String lastName, Long contact) {
    super(email, firstName, middleName, lastName);
    this.addRole(new Role(3l,"ROLE_CUSTOMER"));
    this.contact = contact;
}

//    ---------Customer and Orders mapping----------------------------
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
//    private List<Address> address;

    public Long getContact() {
        return contact;
    }

    public void setContact(Long contact) {
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
//    public List<Orders> getOrders() {
//        return orders;
//    }
//
//    public void setOrders(List<Orders> orders) {
//        this.orders = orders;
//    }


//    public List<Address> getAddresses() {
//        return addresses;
//    }
//
//    public void setAddresses(List<Address> addresses) {
//        this.addresses = addresses;
//    }
//
//    public List<Address> getAddress() {
//        return address;
//    }
//
//    public void setAddress(List<Address> address) {
//        this.address = address;
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
//        if(address!=null){
//            if(address == null)
//                addresses = new ArrayList<>();
//
//            System.out.println("address added");
//            address.setCustomer(this);
//            address.add(address);
//        }
//    }
}
//__________________________________________________________________