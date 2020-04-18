package com.project.ecommerce.Services;

import com.project.ecommerce.Daos.PasswordDto;
import com.project.ecommerce.Daos.SellerDto;
import com.project.ecommerce.Daos.SellerProfileDto;
import com.project.ecommerce.Entities.Address;
import com.project.ecommerce.Entities.Seller;
import com.project.ecommerce.Exceptions.ConfirmPasswordException;
import com.project.ecommerce.Exceptions.UserAlreadyRegisteredException;
import com.project.ecommerce.Repositries.AddressRepository;
import com.project.ecommerce.Repositries.SellerRepository;
import com.project.ecommerce.Repositries.UserRepository;
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
import java.util.List;

@Component
public class SellerService {

    @Autowired
    SellerRepository sellerRepository;
    
    @Autowired
    UserRepository userRepository;

    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    AddressRepository addressRepository;

    @Autowired
    EmailService emailService;

    //to Get the Current Logged-In Username in Spring Security
    public Seller getLoggedInSeller() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        AppUser userDetail = (AppUser) authentication.getPrincipal();
        String username = userDetail.getUsername();
        Seller seller = sellerRepository.findByUsername(username);
        return seller;
    }
    
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

    public List<Seller> listAllSeller(){
        return sellerRepository.findAllSeller(PageRequest.of(0,10, Sort.Direction.ASC,"user_id"));
    }

    @Transactional
    public void editSeller(SellerDto seller1){
        Seller seller=getLoggedInSeller();
            if(seller1.getFirstname()==null)
                seller1.setFirstname(seller.getFirstname());
            if(seller1.getMiddlename()==null)
                seller1.setMiddlename(seller.getMiddlename());
            if(seller1.getLastname()==null)
                seller1.setLastname(seller.getLastname());
            if(seller1.getGST()==null)
                seller1.setGST(seller.getGST());
            if(seller1.getCompanyName()==null)
                seller1.setCompanyName(seller.getCompanyName());
            if(seller1.getCompanyContact()==null)
                seller1.setCompanyContact(seller.getCompanyContact());

            sellerRepository.updateSeller(seller.getUserid(),seller1.getFirstname(),
                    seller1.getMiddlename(), seller1.getLastname(),seller1.getGST(),
                    seller1.getCompanyName(),seller1.getCompanyContact());
    }

    public SellerProfileDto myProfile() {
        Seller seller=getLoggedInSeller();
        Address address = addressRepository.findAddressBySeller(seller.getUserid());
        SellerProfileDto sellerProfileDto=new SellerProfileDto(seller.getUserid(),seller.getFirstname(),seller.getMiddlename(),
                seller.getLastname(),seller.getGST(),seller.getCompanyContact(),seller.getCompanyName(),
                address.getCity(),address.getState(),address.getCountry(),address.getAddress(),address.getZipcode());
        return sellerProfileDto;
    }

    @Transactional
    public void updatePassword(PasswordDto password) {
        Seller seller = getLoggedInSeller();
        String password1 = password.getPassword();
        String confirmPassword = password.getConfirmpassword();
        if (password1.equals(confirmPassword)) {
            seller.setPassword(passwordEncoder.encode(password1));
            sellerRepository.save(seller);
        }
        else
            throw new ConfirmPasswordException("password didn't matched");
    }
}
