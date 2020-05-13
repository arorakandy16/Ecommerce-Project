package com.project.ecommerce.rabbitmq;

import com.project.ecommerce.entity.Product;
import com.project.ecommerce.entity.Seller;
import org.springframework.stereotype.Component;

@Component
public class JMSReceiver {
    public void receiveMessage(Seller seller)
    {
        System.out.println("Received <" + seller + ">");
    }
}
