package com.project.ecommerce.service;

import com.project.ecommerce.dto.PasswordDto;
import com.project.ecommerce.dto.SellerDto;
import com.project.ecommerce.dto.SellerProfileDto;
import com.project.ecommerce.entity.Address;
import com.project.ecommerce.entity.Seller;
import com.project.ecommerce.exception.ConfirmPasswordException;
import com.project.ecommerce.exception.Message;
import com.project.ecommerce.exception.UserAlreadyRegisteredException;
import com.project.ecommerce.rabbitmq.RabbitMQConfiguration;
import com.project.ecommerce.repository.AddressRepository;
import com.project.ecommerce.repository.SellerRepository;
import com.project.ecommerce.repository.UserRepository;
import com.project.ecommerce.security.AppUser;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Locale;

@Service
public class SellerService {

    @Autowired
    SellerRepository sellerRepository;
    
    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    AddressRepository addressRepository;

    @Autowired
    EmailService emailService;

    @Autowired
    private MessageSource messageSource;

    @Autowired
    RabbitTemplate rabbitTemplate;


    //to Login Seller

    public Seller getLoggedInSeller() {
        //The SecurityContext is used to store the details of the currently authenticated user.

        //The SecurityContextHolder is a helper class, which provide access to the security context.

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        //Object getPrincipal()     The identity of the principal being authenticated.

        // In the case of an authentication request with username and password, this would be the username.

        AppUser userDetail = (AppUser) authentication.getPrincipal();
        String username = userDetail.getUsername();
        Seller seller = sellerRepository.findByUsername(username);
        return seller;
    }




    //Add Seller

    public String addSeller(Seller user, Locale locale) {

        String password=user.getPassword();

        if (userRepository.findByUsername(user.getEmail())==null){
            String confirmPassword=user.getConfirmPassword();

            if(password.equals(confirmPassword)) {
                user.setPassword(passwordEncoder.encode(password));
                user.setIs_active(false);

                emailService.sendEmail("ACCOUNT ACTIVATION APPROVAL","Hii, \n Your email "+user.getEmail()
                    +" have been successfully registered, but you have to wait for a successful approval by our Executive-Officer. ",
                    user.getEmail());

                emailService.sendEmail("ACCOUNT ACTIVATION APPROVAL", "Hii Admin,\n You have a pending task, user " + user.getEmail()
                            + " have been successfully registered, but it needs your approval. To activate " + user.getEmail()
                            + " click here -> http://localhost:8080/user/activate-account/{id}" + user.getEmail(),
                    "kandyarora4047@gmail.com");

                sellerRepository.save(user);

                throw new Message(messageSource.getMessage("seller.add.message", null, locale));
            }

            else

                throw  new ConfirmPasswordException("Password & Confirm-Password doesn't match");
        }

        else
            throw new UserAlreadyRegisteredException("Email "+user.getEmail()+" is already registered");
    }




    //List All Sellers

    public List<Seller> listAllSeller(Integer offset, Integer size){

        if (offset==null)
            offset=0;

        if (size==null)
            size=10;

        return sellerRepository.findAllSeller
                (PageRequest.of
                        (offset, size, Sort.Direction.ASC,"user_id"));
    }




    //Update Seller

    @Transactional
    public void editSeller(SellerDto seller1,Locale locale){
        Seller seller=getLoggedInSeller();

            if(seller1.getFirstName()==null)
                seller1.setFirstName(seller.getFirstname());

            if(seller1.getMiddleName()==null)
                seller1.setMiddleName(seller.getMiddlename());

            if(seller1.getLastName()==null)
                seller1.setLastName(seller.getLastname());

            if(seller1.getGST()==null)
                seller1.setGST(seller.getGST());

            if(seller1.getCompanyName()==null)
                seller1.setCompanyName(seller.getCompanyName());

            if(seller1.getCompanyContact()==null)
                seller1.setCompanyContact(seller.getCompanyContact());

            sellerRepository.updateSeller(seller.getUserid(),seller1.getFirstName(),
                    seller1.getMiddleName(), seller1.getLastName(),seller1.getGST(),
                    seller1.getCompanyName(),seller1.getCompanyContact());

            throw new Message(messageSource.getMessage
                ("seller.update.message", null, locale));

    }



    // Seller Profile

    public SellerProfileDto myProfile() {
        Seller seller=getLoggedInSeller();

        Address address = addressRepository.findAddressBySeller(seller.getUserid());

        SellerProfileDto sellerProfileDto=new SellerProfileDto
                (seller.getUserid(),seller.getFirstname(),seller.getMiddlename(),
                        seller.getLastname(),seller.getGST(),seller.getCompanyContact(),
                        seller.getCompanyName(), address.getCity(),address.getState(),
                        address.getCountry(),address.getAddress(),address.getZipcode());

        System.out.println("Sending message...");
        rabbitTemplate.convertAndSend(RabbitMQConfiguration.topicExchangeName,
                "message_routing_key", "---My Profile---");
        System.out.println("Message sent successfully...");

        return sellerProfileDto;
    }


    //Update Password

    @Transactional
    public void updatePassword(PasswordDto password,Locale locale) {
        Seller seller = getLoggedInSeller();

        String password1 = password.getPassword();
        String confirmPassword = password.getConfirmPassword();

        if (password1.equals(confirmPassword)) {
            seller.setPassword(passwordEncoder.encode(password1));
            sellerRepository.save(seller);
            throw new Message(messageSource.getMessage
                    ("seller.update.password.message", null, locale));
        }
        else
            throw new ConfirmPasswordException("Password & Confirm-Password doesn't match");
    }
}
