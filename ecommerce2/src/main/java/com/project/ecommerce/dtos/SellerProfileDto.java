package com.project.ecommerce.dtos;

public class SellerProfileDto {

    private Long userId;
    private String firstname;
    private String middlename;
    private String lastname;
    private String GST;
    private Long companyContact;
    private String companyName;
    private String city;
    private String state;
    private String country;
    private String address;
    private String zipcode;

    public SellerProfileDto(Long userId, String firstname, String middlename, String lastname,
                            String GST, Long companyContact, String companyName,String city,
                            String state, String country, String address, String zipcode) {
        this.userId = userId;
        this.firstname = firstname;
        this.middlename = middlename;
        this.lastname = lastname;
        this.GST = GST;
        this.companyContact = companyContact;
        this.companyName = companyName;
        this.city = city;
        this.state = state;
        this.country = country;
        this.address = address;
        this.zipcode = zipcode;
    }

    public SellerProfileDto() {
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

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }
}
