package com.project.ecommerce.security;

import com.project.ecommerce.entity.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;

public class AppUser implements UserDetails {

    private String username;

    private String password;

    private boolean is_active;

    private boolean passwordNotExpired;

    Set<Role> roles;

    public AppUser(String username, String password, Set<Role> grantAuthorities,boolean is_active, boolean passwordNotExpired) {
        this.username = username;
        this.password = password;
        this.roles = grantAuthorities;
        this.is_active=is_active;
        this.passwordNotExpired=passwordNotExpired;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return passwordNotExpired;
    }

    @Override
    public boolean isEnabled() {
        return is_active;
    }
}
