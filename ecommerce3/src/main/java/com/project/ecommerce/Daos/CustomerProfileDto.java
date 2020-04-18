package com.project.ecommerce.Daos;

public class CustomerProfileDto {

    private Long userId;
    private String firstname;
    private String middlename;
    private String lastname;
    private Long contact;

    public CustomerProfileDto(Long userId, String firstname, String middlename, String lastname, Long contact) {
        this.userId = userId;
        this.firstname = firstname;
        this.middlename = middlename;
        this.lastname = lastname;
        this.contact = contact;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getMiddlename() {
        return middlename;
    }

    public void setMiddlename(String middlename) {
        this.middlename = middlename;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public Long getContact() {
        return contact;
    }

    public void setContact(Long contact) {
        this.contact = contact;
    }
}
