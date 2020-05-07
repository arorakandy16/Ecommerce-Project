package com.project.ecommerce.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.ecommerce.auditing.Auditable;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.File;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)

public class User extends Auditable<String> {
/*
    User class extends Auditable for Auditing
 */
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long userid;


    @Email
    @NotNull
    @Column(unique = true)
    private String email;

    @NotNull
//    @Pattern(regexp = "^[a-zA-Z]{1,20}$",message =
//            "field first name can only contains alphabets")
    private String firstname;

    private String middlename;

    private String lastname;


    @NotNull
    private String password;

    private Integer failedAttempts = 0;

    private File userImage= new File("/resource/images/users/0.jpeg");


    @Transient
    @Size(min = 8,max = 15)
    @Pattern(regexp = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%])" +
            ".{6,20})",message =
            "Password should contain atleast 8 characters " +
            "and one Uppercase,lowercase,digit and special character")
    private String confirmPassword;


    private boolean is_deleted = false;

    private boolean is_active = false;

    //------------------------------------------For password expiry task------------------------------------------------

    private boolean passwordNotExpired = true;

    public boolean isPasswordNotExpired() {
        return passwordNotExpired;
    }

    public void setPasswordNotExpired(boolean passwordNotExpired) {
        this.passwordNotExpired = passwordNotExpired;
    }

    @CreatedDate
    private LocalDate updatePasswordDate;

    public LocalDate getUpdatePasswordDate() {
        return updatePasswordDate;
    }

    public void setUpdatePasswordDate(LocalDate updatePasswordDate) {
        this.updatePasswordDate = updatePasswordDate;
    }

    //------------------------------------------------------------------------------------------------------------------


    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_role",
            joinColumns = @JoinColumn(name = "user_id",referencedColumnName = "userid"),
            inverseJoinColumns = @JoinColumn(name = "role_id",referencedColumnName = "roleid"))


    @JsonIgnore
    private Set<Role> roles;


    public  User(){ }

    //------------------------------------------------------------------------------------------

    public void addRole(Role role){
        if(roles==null)
            roles = new HashSet<>();
        roles.add(role);
    }

    //------------------------------------------------------------------------------------------

    public File getUserImage() {
        return userImage;
    }

    public void setUserImage(File userImage) {
        this.userImage = userImage;
    }

    public Long getUserid() {
        return userid;
    }

    public User(String email, String firstname, String middlename, String lastname) {
        this.email = email;
        this.firstname = firstname;
        this.middlename = middlename;
        this.lastname = lastname;
    }


    @Override
    public String toString() {
        return "User{" +
                "userid=" + userid +
                ", email='" + email + '\'' +
                ", firstname='" + firstname + '\'' +
                ", middlename='" + middlename + '\'' +
                ", lastname='" + lastname + '\'' +
                ", password='" + password + '\'' +
                ", confirmPassword='" + confirmPassword + '\'' +
                ", is_deleted=" + is_deleted +
                ", is_active=" + is_active +
                ", roles=" + roles +
                '}';
    }

    public void setUserid(Long userid) {
        this.userid = userid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public boolean isIs_deleted() {
        return is_deleted;
    }

    public void setIs_deleted(boolean is_deleted) {
        this.is_deleted = is_deleted;
    }

    public boolean isIs_active() {
        return is_active;
    }

    public void setIs_active(boolean is_active) {
        this.is_active = is_active;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public Integer getFailedAttempts() {
        return failedAttempts;
    }

    public void setFailedAttempts(Integer failedAttempts) {
        this.failedAttempts = failedAttempts;
    }


}
