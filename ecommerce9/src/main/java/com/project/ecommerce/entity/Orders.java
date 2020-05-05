package com.project.ecommerce.entity;

import com.project.ecommerce.auditing.Auditable;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Orders extends Auditable<String> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;

    private Long AmountPaid;

    private Date dateCreated;

    private String paymentMethod;
    
    //-----------Orders and customer mapping------------------------
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "customer_user_id")
    private Customer customer;



    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getAmountPaid() {
        return AmountPaid;
    }

    public void setAmountPaid(Long amountPaid) {
        AmountPaid = amountPaid;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}
