package com.project.ecommerce.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@PrimaryKeyJoinColumn(name = "user_id")
public class Customer extends User{


    @NotNull
    private Long contact;


    public Customer(){
        this.addRole(new Role(3l, "ROLE_CUSTOMER"));
    }


    public Customer(String email, String firstName, String middleName,
                    String lastName, Long contact) {

        super(email, firstName, middleName, lastName);

        this.addRole(new Role(3l,"ROLE_CUSTOMER"));

        this.contact = contact;
}


    public Long getContact() {
        return contact;
    }

    public void setContact(Long contact) {
        this.contact = contact;
    }


    @Override
    public String toString() {
        return "Customer{" +
                "contact='" + contact + '\'' +
                '}';
    }
}
