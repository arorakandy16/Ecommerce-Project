package com.project.ecommerce.dto;

import javax.persistence.Transient;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class PasswordDto {

    @NotNull
    @NotEmpty(message = "Password is a mandatory field")
    @Pattern(regexp = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{6,20})",message = "Password should contain atleast 8 characters and one Uppercase,lowercase,digit and special character")
    private String password;

    @Transient
    @NotNull
    @NotEmpty(message = "Password is a mandatory field")@Pattern(regexp = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{6,20})",message = "Password should contain atleast 8 characters and one Uppercase,lowercase,digit and special character")
    private String confirmPassword;

    PasswordDto(){
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmpassword) {
        this.confirmPassword = confirmpassword;
    }

    @Override
    public String toString() {
        return "PasswordDto{" +
                "password='" + password + '\'' +
                ", confirmPassword='" + confirmPassword + '\'' +
                '}';
    }
}