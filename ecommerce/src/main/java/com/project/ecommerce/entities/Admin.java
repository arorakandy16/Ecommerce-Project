package com.project.ecommerce.entities;

import javax.persistence.Entity;

@Entity
public class Admin extends User {

    public Admin() {
    }

    public Admin(String email, String firstname, String middlename, String lastname) {
        super(email, firstname, middlename, lastname);
        this.addRole(new Role(1,"ROLE_ADMIN"));
    }

    @Override
    public String toString() {
        return "Admin{}";
    }

}
