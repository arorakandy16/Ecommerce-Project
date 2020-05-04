package com.project.ecommerce.entity;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Role  implements GrantedAuthority {

    @Id
    private Long roleid;
    private String role_authority;


    //-------------User and Role Mapping-----------------
    @ManyToMany(mappedBy = "roles",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private Set<User> user;



    public Role(Long roleid, String role_authority) {
        this.roleid = roleid;
        this.role_authority = role_authority;
    }



    public Role() { }



    public Long getRoleid() {
        return roleid;
    }

    public void setRoleid(Long roleid) {
        this.roleid = roleid;
    }

    public Set<User> getUser() {
        return user;
    }

    public void setUser(Set<User> user) {
        this.user = user;
    }

    public String getAuthority() {
        return role_authority;
    }

    public void setAuthority(String authority) {
        this.role_authority = authority;
    }



    @Override
    public String toString() {
        return "Role{" +
                "roleid=" + roleid +
                ", role_authority='" + role_authority + '\'' +
                ", user=" + user +
                '}';
    }
}
