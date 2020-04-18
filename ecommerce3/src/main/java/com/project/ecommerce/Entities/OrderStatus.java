package com.project.ecommerce.Entities;

import javax.persistence.Entity;
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
