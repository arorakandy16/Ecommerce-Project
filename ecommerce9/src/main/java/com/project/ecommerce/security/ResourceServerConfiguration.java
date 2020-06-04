package com.project.ecommerce.security;

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

                //-----------------------Registration APIs--------------------------------------------------

                .antMatchers("/admin/register").hasAnyRole("ADMIN")
                .antMatchers("/customer/registration").anonymous()
                .antMatchers("/customer/activate-account/{token}").anonymous()
                .antMatchers("/re-sent-link/{email}").anonymous()
                .antMatchers("/seller/registration").anonymous()

                //-----------------------Login & Logout APIs------------------------------------------------

                //.antMatchers("/oauth/token").anonymous()
                .antMatchers("/doLogout").hasAnyRole("ADMIN","CUSTOMER","SELLER")

                //-----------------------Admin APIs---------------------------------------------------------

                .antMatchers("/user/get/all").hasAnyRole("ADMIN")
                .antMatchers("/customer/get/all").hasAnyRole("ADMIN")
                .antMatchers("/seller/get/all").hasAnyRole("ADMIN")
//                .antMatchers("/user/activate-account/{id}").hasAnyRole("ADMIN")
//                .antMatchers("/user/de-activate-account/{id}").hasAnyRole("ADMIN")


                //-----------------------Forgot Password APIs-----------------------------------------------

                .antMatchers("/password/forgot").anonymous()
                //.antMatchers("/password/reset/{token}").anonymous()

                //-----------------------Customer APIs------------------------------------------------------

                .antMatchers("/customer/myProfile").hasAnyRole("CUSTOMER")
                .antMatchers("/customer/address/get/all").hasAnyRole("CUSTOMER")
                .antMatchers("/customer/update/profile").hasAnyRole("CUSTOMER")
                .antMatchers("/customer/update/password").hasAnyRole("CUSTOMER")
                .antMatchers("/customer/address/add").hasAnyRole("CUSTOMER")
                .antMatchers("/customer/address/delete/{AddressId}").hasAnyRole("CUSTOMER")
                .antMatchers("/customer/address/update/{AddressId}").hasAnyRole("CUSTOMER")

                //-----------------------Seller APIs--------------------------------------------------------

                .antMatchers("/seller/myProfile").hasAnyRole("SELLER")
                .antMatchers("/seller/update/profile").hasAnyRole("SELLER")
                .antMatchers("/seller/update/password").hasAnyRole("SELLER")
                .antMatchers("/seller/address/update/{addId}").hasAnyRole("SELLER")

                //-----------------------Category APIs------------------------------------------------------

                .antMatchers("/admin/add/metadata/field").hasAnyRole("ADMIN")
                .antMatchers("/admin/field/view/all").hasAnyRole("ADMIN")
                .antMatchers("/admin/category/add").hasAnyRole("ADMIN")
                .antMatchers("/admin/category/view/{id}").hasAnyRole("ADMIN")
                .antMatchers("/category/view/all").hasAnyRole("ADMIN","SELLER")
                .antMatchers("/admin/category/update/{id}").hasAnyRole("ADMIN")
                .antMatchers("/admin/add/metadata/values/{categoryId}/{fieldId}").hasAnyRole("ADMIN")
                .antMatchers("/admin/update/metadata/values/{categoryId}/{fieldId}").hasAnyRole("ADMIN")
                //
                .antMatchers("/customer/category/view/all").hasAnyRole("CUSTOMER")
                .antMatchers("/customer/category/view/{id}").hasAnyRole("CUSTOMER")

                //-----------------------Product APIs-------------------------------------------------------

                .antMatchers("/seller/product/add").hasRole("SELLER")
                .antMatchers("/seller/product/variation/add").hasRole("SELLER")
                .antMatchers("/seller/product/variation/views/{productId}").hasRole("SELLER")
                .antMatchers("/seller/product/variation/view/{variationId}").hasRole("SELLER")
                .antMatchers("/seller/product/view/all").hasRole("SELLER")
                .antMatchers("/seller/product/variation/views/{productId}").hasRole("SELLER")
                .antMatchers("/seller/product/delete/{productId}").hasRole("SELLER")
                .antMatchers("/seller/product/variation/update/{variationId}").hasRole("SELLER")
                .antMatchers("/seller/product/update/{productId}").hasRole("SELLER")
                .antMatchers("/customer/product/view/{productId}").hasRole("CUSTOMER")
                .antMatchers("/customer/product/view/all/{categoryId}").hasRole("CUSTOMER")
                .antMatchers("/customer/product/view/similar/{productId}").hasRole("CUSTOMER")
                .antMatchers("/customer/product/view/{productId}").hasRole("CUSTOMER")
                .antMatchers("/admin/product/view/{productId}").hasRole("ADMIN")
                .antMatchers("/admin/product/view/all/{categoryId}").hasRole("ADMIN")
                .antMatchers("/admin/product/de-activate/{productId}").hasRole("ADMIN")
                .antMatchers("/admin/product/activate/{productId}").hasRole("ADMIN")

                //-----------------------Thymeleaf APIs-------------------------------------------------------

//                .antMatchers("/listCustomers").hasRole("ADMIN")
//                .antMatchers("/listSellers").hasRole("ADMIN")

                //-------------------------------------------------------------------------------------------

                .antMatchers("/*").permitAll()
                .anyRequest().permitAll()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .csrf().disable();
    }
}