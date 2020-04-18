package com.project.ecommerce.Services;

import com.project.ecommerce.Daos.CustomerDto;
import com.project.ecommerce.Daos.CustomerProfileDto;
import com.project.ecommerce.Daos.PasswordDto;
import com.project.ecommerce.Entities.Customer;
import com.project.ecommerce.Entities.VerificationToken;
import com.project.ecommerce.Exceptions.ConfirmPasswordException;
import com.project.ecommerce.Exceptions.ServerSideException;
import com.project.ecommerce.Exceptions.UserAlreadyRegisteredException;
import com.project.ecommerce.Repositries.CustomerRepository;
import com.project.ecommerce.Repositries.UserRepository;
import com.project.ecommerce.Repositries.VerificationTokenRepository;
import com.project.ecommerce.Security.AppUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Component
public class CustomerService {
    
    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    UserRepository userRepository;

    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    VerificationTokenRepository verificationTokenRepository;

    @Autowired
    EmailService emailService;

    //to Get the Current Logged-In Username in Spring Security
    public Customer getLoggedInCustomer() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        AppUser userDetail = (AppUser) authentication.getPrincipal();
        String username = userDetail.getUsername();
        Customer customer = customerRepository.findByUsername(username);
        return customer;
    }

    public String addCustomer(Customer user) {
        System.out.println(user.getPassword());
        String password=user.getPassword();
        if (userRepository.findByUsername(user.getEmail())==null){
            String confirmPassword=user.getConfirmPassword();
            if(password.equals(confirmPassword)) {
                user.setPassword(passwordEncoder.encode(password));
                System.out.println(user.getPassword());

                String token = UUID.randomUUID().toString();
                VerificationToken confirmToken = new VerificationToken();
                confirmToken.setToken(token);
                confirmToken.setUserEmail(user.getEmail());
                confirmToken.setGeneratedDate(new Date());
                System.out.println(confirmToken.getToken());
                customerRepository.save(user);
                try {
                    emailService.sendEmail("ACCOUNT ACTIVATE TOKEN", "Hii, \n To confirm your account, please click here : "
                            + "http://localhost:8080/activate-customer-account/" + token, user.getEmail());
                    verificationTokenRepository.save(confirmToken);
                }
                catch (Exception ex) {
                     return "if you don't get email click here -> http://localhost:8080/re-sent-link/" + user.getEmail();
                }
            }
            else
                throw  new ConfirmPasswordException("Password & Confirm-Password doesn't match");
        }
        else
            throw new UserAlreadyRegisteredException("Email "+user.getEmail()+" is already registered");

        return "Check your email for further registration process";
    }



    @Transactional
    public List<Customer> listAllCustomers(){
        return customerRepository.findAllCustomer(PageRequest.of(0,10, Sort.Direction.ASC,"user_id"));
    }

    @Transactional
    public void editCustomer(CustomerDto customer1) {
        Customer customer=getLoggedInCustomer();
            if (customer1.getFirstname() == null)
                customer1.setFirstname(customer.getFirstname());
            if (customer1.getMiddlename() == null)
                customer1.setMiddlename(customer.getMiddlename());
            if (customer1.getLastname() == null)
                customer1.setLastname(customer.getLastname());
            if (customer1.getContact() == null)
                customer1.setContact(customer.getContact());

            customerRepository.updateCustomer(customer.getUserid(), customer1.getFirstname(),
                    customer1.getMiddlename(), customer1.getLastname(), customer1.getContact());
    }

    @Transactional
    public String activateCustomer(String token){
        VerificationToken token1= verificationTokenRepository.getByToken(token);
        if (token1.getToken()!=null){
            customerRepository.updateIsActive(true,token1.getUserEmail());
            verificationTokenRepository.deleteById(token1.getTokenId());
            return "Your account has been activated";
            }
        else {
        return  "http://localhost:8080/activate-customer-account/"+token+" has been expired.";
        }
    }

    @Transactional
    public String reSentLink(String email) {
        String token = UUID.randomUUID().toString();
        VerificationToken confirmToken = new VerificationToken();
        confirmToken.setToken(token);
        confirmToken.setUserEmail(email);
        confirmToken.setGeneratedDate(new Date());
        System.out.println(confirmToken.getToken());
        try {
            emailService.sendEmail("ACCOUNT ACTIVATE TOKEN", "Hii, \n To confirm your account, please click here : "
                    + "http://localhost:8080/activate-customer-account/" + token, email);
            verificationTokenRepository.save(confirmToken);
            return "Check your email for further registration process";
        }
        catch (Exception ex){
            throw new ServerSideException("There is some error while connecting you," +
                    " please click again to re-sent activation link-> http://localhost:8080/re-sent-link/" + email);
        }
    }

    public CustomerProfileDto myProfile() {
        Customer customer=getLoggedInCustomer();
        CustomerProfileDto customerProfileDto = new CustomerProfileDto(customer.getUserid(),
                customer.getFirstname(),customer.getMiddlename(),customer.getLastname(),customer.getContact());
        return customerProfileDto;
    }

    @Transactional
    public void updateCustomerPassword(PasswordDto password) {
        Customer customer = getLoggedInCustomer();
        String password1 = password.getPassword();
        String confirmPassword = password.getConfirmpassword();
        if (password1.equals(confirmPassword)) {
            customer.setPassword(passwordEncoder.encode(password1));
            customerRepository.save(customer);
        }
        else
            throw new ConfirmPasswordException("password didn't matched");
    }
}
