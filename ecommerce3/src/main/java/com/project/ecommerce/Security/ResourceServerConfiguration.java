package com.project.ecommerce.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

@Configuration
@EnableResourceServer
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true,prePostEnabled = true)
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

    @Autowired
    AppUserDetailsService userDetailsService;

    public ResourceServerConfiguration() {
        super();
    }

    @Bean
    public static BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        final DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(bCryptPasswordEncoder());
        return authenticationProvider;
    }

    @Autowired
    public void configureGlobal(final AuthenticationManagerBuilder authenticationManagerBuilder) {
        authenticationManagerBuilder.authenticationProvider(authenticationProvider());

    }

    @Override
    public void configure(final HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/getUsers").hasAnyRole("ADMIN")
                .antMatchers("/Admin/Register").hasAnyRole("ADMIN")
                .antMatchers("/doLogout").hasAnyRole("ADMIN","CUSTOMER","SELLER")

                .antMatchers("/customer/registration").anonymous()
                .antMatchers("/getCustomers").hasAnyRole("ADMIN")
                .antMatchers("customer/update/password").hasAnyRole("CUSTOMER")
                .antMatchers("/customer/update/Profile").hasAnyRole("CUSTOMER")
                .antMatchers("/customer/myProfile").hasAnyRole("CUSTOMER")
                .antMatchers("/activate-customer-account/{token}").anonymous()
                .antMatchers("/forgot-password").hasAnyRole("CUSTOMER","SELLER","ADMIN")

                .antMatchers("/seller/registration").anonymous()
                .antMatchers("/getSellers").hasAnyRole("ADMIN")
                .antMatchers("/seller/update/password").hasAnyRole("SELLER")
                .antMatchers("/seller/update/profile").hasAnyRole("SELLER")
                .antMatchers("/customer/getAddress").hasAnyRole("CUSTOMER")
                .antMatchers("/customer/addAddress").hasAnyRole("CUSTOMER")
                .antMatchers("/customer/updateAddress/{AddressId}").hasAnyRole("CUSTOMER")
                .antMatchers("/customer/deleteAddress/{AddressId}").hasAnyRole("CUSTOMER")
                .antMatchers("/seller/updateAddress/{addId}").hasAnyRole("SELLER")
                .antMatchers("/seller/myProfile").hasAnyRole("SELLER")
                .antMatchers("reset-password/{token}").anonymous()
                .antMatchers("/*").permitAll()
                .anyRequest().permitAll()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .csrf().disable();
    }
}