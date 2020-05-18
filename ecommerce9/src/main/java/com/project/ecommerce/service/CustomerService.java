package com.project.ecommerce.service;

import com.project.ecommerce.dto.CustomerDto;
import com.project.ecommerce.dto.CustomerProfileDto;
import com.project.ecommerce.dto.PasswordDto;
import com.project.ecommerce.entity.Customer;
import com.project.ecommerce.entity.VerificationToken;
import com.project.ecommerce.exception.ConfirmPasswordException;
import com.project.ecommerce.exception.Message;
import com.project.ecommerce.exception.ServerSideException;
import com.project.ecommerce.exception.UserAlreadyRegisteredException;
import com.project.ecommerce.repository.CustomerRepository;
import com.project.ecommerce.repository.UserRepository;
import com.project.ecommerce.repository.VerificationTokenRepository;
import com.project.ecommerce.security.AppUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

@Service
public class CustomerService {
    
    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    VerificationTokenRepository verificationTokenRepository;

    @Autowired
    EmailService emailService;

    @Autowired
    private MessageSource messageSource;


    //to Login Customer

    public Customer getLoggedInCustomer() {

        //The SecurityContext is used to store the details of the currently authenticated user.

        //The SecurityContextHolder is a helper class, which provide access to the security context.
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        //Object getPrincipal()     The identity of the principal being authenticated.
        // In the case of an authentication request with username and password, this would be the username.
        AppUser userDetail = (AppUser) authentication.getPrincipal();
        String username = userDetail.getUsername();
        Customer customer = customerRepository.findByUsername(username);
        return customer;
    }



    // Get All Customers

    public List<Customer> listAllCustomers(Integer offset,Integer size){

        return customerRepository.findAllCustomer
                (PageRequest.of
                        (offset,size, Sort.Direction.ASC,"user_id"));
    }



    //Add new Customer

    public String addCustomer(Customer user, Locale locale) {
        System.out.println(user.getPassword());
        String password=user.getPassword();
        if (userRepository.findByUsername(user.getEmail())==null){
            String confirmPassword=user.getConfirmPassword();
            if(password.equals(confirmPassword)) {
                user.setPassword(passwordEncoder.encode(password));
                user.setIs_active(false);
                String token = UUID.randomUUID().toString();
                VerificationToken confirmToken = new VerificationToken();
                confirmToken.setToken(token);
                confirmToken.setUserEmail(user.getEmail());
                confirmToken.setGeneratedDate(new Date());
                System.out.println(confirmToken.getToken());
                customerRepository.save(user);
                try {
                    emailService.sendEmail("ACCOUNT ACTIVATE TOKEN", "Hii, \n To confirm your account, please click here : "
                            + "http://localhost:8080/customer/activate-account/" + token, user.getEmail());
                    verificationTokenRepository.save(confirmToken);
                }
                catch (Exception ex) {
                     throw new ServerSideException("if you don't get email click here -> http://localhost:8080/re-sent-link/" + user.getEmail());
                }
            }
            else
                throw  new ConfirmPasswordException("Password & Confirm-Password doesn't match");
        }
        else
            throw new UserAlreadyRegisteredException("Email "+user.getEmail()+" is already registered");

        return messageSource.getMessage("customer.add.message", null, locale);
    }



    //Update Customer

    @Transactional
    public void editCustomer(CustomerDto customer1) {
        Customer customer=getLoggedInCustomer();
            if (customer1.getFirstName() == null)
                customer1.setFirstName(customer.getFirstname());
            if (customer1.getMiddleName() == null)
                customer1.setMiddleName(customer.getMiddlename());
            if (customer1.getLastName() == null)
                customer1.setLastName(customer.getLastname());
            if (customer1.getContact() == null)
                customer1.setContact(customer.getContact());

            customerRepository.updateCustomer(customer.getUserid(), customer1.getFirstName(),
                    customer1.getMiddleName(), customer1.getLastName(), customer1.getContact());
    }



    //Activate a Customer

    @Transactional
    public String activateCustomer(String token,Locale locale){
        VerificationToken token1= verificationTokenRepository.getByToken(token);
        if (token1.getToken()!=null){
            customerRepository.updateIsActive(true,token1.getUserEmail());
            verificationTokenRepository.deleteById(token1.getTokenId());
            return messageSource.getMessage
                    ("customer.activate.message", null, locale);
            }
        else {
        throw new ServerSideException("http://localhost:8080/activate-customer-account/"+token+" has been expired.");
        }
    }



    //Re send Link

    @Transactional
    public String reSentLink(String email,Locale locale) {
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
            return messageSource.getMessage
                    ("customer.resent.link.message", null, locale);
        }
        catch (Exception ex){
            throw new ServerSideException("There is some error while connecting you," +
                    " please click again to re-sent activation link-> http://localhost:8080/re-sent-link/" + email);
        }
    }



    //Show My Profile

    public CustomerProfileDto myProfile() {
        Customer customer=getLoggedInCustomer();
        CustomerProfileDto customerProfileDto = new CustomerProfileDto(customer.getUserid(),
                customer.getFirstname(),customer.getMiddlename(),
                customer.getLastname(),customer.getContact());
        return customerProfileDto;
    }



    //Update Customer Password

    @Transactional
    public void updateCustomerPassword(PasswordDto password, Locale locale) {
        Customer customer = getLoggedInCustomer();
        String password1 = password.getPassword();
        String confirmPassword = password.getConfirmPassword();
        if (password1.equals(confirmPassword)) {
            customer.setPassword(passwordEncoder.encode(password1));
            customerRepository.save(customer);
        }
        else
            throw new ConfirmPasswordException("password didn't matched");

        throw new Message(messageSource.getMessage
                ("customer.update.password.message", null, locale));
    }
}