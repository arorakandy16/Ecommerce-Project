package com.project.ecommerce.service;

import com.project.ecommerce.entities.Seller;
import com.project.ecommerce.exception.ConfirmPasswordException;
import com.project.ecommerce.exception.UserAlreadyRegisteredException;
import com.project.ecommerce.exception.UserNotFoundException;
import com.project.ecommerce.repositries.SellerRepository;
import com.project.ecommerce.repositries.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Component
public class SellerService {

    @Autowired
    SellerRepository sellerRepository;
    
    @Autowired
    UserRepository userRepository;

    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    EmailService emailService;
    
    public String addseller(Seller user) {
        System.out.println(user.getPassword());
        String password=user.getPassword();
        if (userRepository.findByUsername(user.getEmail())==null){
        String confirmPassword=user.getConfirmPassword();
        if(password.equals(confirmPassword)) {
            user.setPassword(passwordEncoder.encode(password));
            emailService.sendEmail("ACCOUNT ACTIVATION APPROVAL", "Hii, \n Your email "+user.getEmail()
                    +" have been successfully registered, but you have to wait for a successful approval by our Executive-Officer. ",
                    user.getEmail());

            emailService.sendEmail("ACCOUNT ACTIVATION APPROVAL", "Hii Admin,\n You have a pending task, user " + user.getEmail()
                            + " have been successfully registered, but it needs your approval. To activate " + user.getEmail()
                            + " click here -> http://localhost:8080/activate-user/" + user.getEmail(),
                    "kandyarora4047@gmail.com");
            sellerRepository.save(user);
            return "Check your email for further registration process.";
        }
        else
            throw  new ConfirmPasswordException("Password & Confirm-Password doesn't match");
        }
        else
            throw new UserAlreadyRegisteredException("Email "+user.getEmail()+" is already registered");
    }

    
    @Transactional
    public List<Seller> listAllSeller(){
        return sellerRepository.findAllSeller(PageRequest.of(0,10, Sort.Direction.ASC,"user_id"));
    }

    @Transactional
    public void editSeller(Integer id,Seller seller1){
        Optional<Seller> seller=sellerRepository.findById(id);
        if(sellerRepository.sellerUserId(id)==null)
            throw new UserNotFoundException("User Not Exist");
        else{
            if(seller1.getEmail()==null)
                seller1.setEmail(seller.get().getEmail());
            if(seller1.getFirstname()==null)
                seller1.setFirstname(seller.get().getFirstname());
            if(seller1.getMiddlename()==null)
                seller1.setMiddlename(seller.get().getMiddlename());
            if(seller1.getLastname()==null)
                seller1.setLastname(seller.get().getLastname());
            if(seller1.getGST()==null)
                seller1.setGST(seller.get().getGST());
            if(seller1.getCompanyName()==null)
                seller1.setCompanyName(seller.get().getCompanyName());
            if(seller1.getCompanyContact()==null)
                seller1.setCompanyContact(seller.get().getCompanyContact());

            sellerRepository.updateSeller(id,seller1.getEmail(),
                    seller1.getFirstname(),
                    seller1.getMiddlename(), seller1.getLastname(),seller1.getGST(),
                    seller1.getCompanyName(),seller1.getCompanyContact());
        }
    }

    
    public Seller findSellerbyId(Integer id) {
        Optional<Seller> seller=sellerRepository.findById(id);
        return seller.get();
    }
}
