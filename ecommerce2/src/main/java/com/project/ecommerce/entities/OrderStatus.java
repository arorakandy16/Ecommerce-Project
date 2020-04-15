package com.project.ecommerce.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class OrderStatus {

    @Id
    private Long id;
    private String From_Status;
    private String To_Status;
    private String Transmission_Notes_Comments;

//    private Order_Product orderProductId;



}
