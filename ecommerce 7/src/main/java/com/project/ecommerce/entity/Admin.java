package com.project.ecommerce.entity;

import javax.persistence.Entity;

@Entity
public class Admin extends User {


    public Admin() {
        this.addRole(new Role(1l,"ROLE_ADMIN"));
    }


    public Admin(String email, String firstname, String middlename, String lastname) {

        super(email, firstname, middlename, lastname);

        this.addRole(new Role(1l,"ROLE_ADMIN"));
    }

    @Override
    public String toString() {
        return "Admin{}";
    }

}
