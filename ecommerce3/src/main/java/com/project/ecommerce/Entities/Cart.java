package com.project.ecommerce.Entities;

import javax.persistence.*;

@Entity
@PrimaryKeyJoinColumn(name = "customer_user_id")
public class Cart extends Customer{

    private Integer quantity;
    private boolean is_wishList_item;
    
////    ***************************************************
////    ---------------Cart and Product Variation----------
//    @OneToMany(mappedBy = "cart",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
//    @JoinColumn(name = "product_variation_id")
//    private List<ProductVariant> productVariants;
//
////    -----------Cart and Customer------------------------------------
//    @OneToOne(cascade = CascadeType.ALL)
//    private Customer customer;
    
}
