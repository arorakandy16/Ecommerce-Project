package com.project.ecommerce.dto;

public class SellerDto {

    private String firstName;
    private String middleName;
    private String lastName;
    private String GST;
    private Long companyContact;
    private String companyName;

    public SellerDto(String firstName, String middleName, String lastName, String GST, Long companyContact, String companyName) {
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.GST = GST;
        this.companyContact = companyContact;
        this.companyName = companyName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstname) {
        this.firstName = firstname;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middlename) {
        this.middleName = middlename;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getGST() {
        return GST;
    }

    public void setGST(String GST) {
        this.GST = GST;
    }

    public Long getCompanyContact() {
        return companyContact;
    }

    public void setCompanyContact(Long companyContact) {
        this.companyContact = companyContact;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}
