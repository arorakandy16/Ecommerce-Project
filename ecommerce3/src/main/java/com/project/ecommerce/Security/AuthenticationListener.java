package com.project.ecommerce.Security;


import com.project.ecommerce.Entities.User;
import com.project.ecommerce.Repositries.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AbstractAuthenticationEvent;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationListener implements ApplicationListener<AbstractAuthenticationEvent> {

    @Autowired
    UserRepository userRepository;

//    public User getLoggedInUser() {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        AppUser userDetail = (AppUser) authentication.getPrincipal();
//        String username = userDetail.getUsername();
//        User user = userRepository.findByUsername(username);
//        return user;
//    }

    @Override
    public void onApplicationEvent(AbstractAuthenticationEvent appEvent)
    {

        if (appEvent instanceof AuthenticationSuccessEvent)
        {
            AuthenticationSuccessEvent event = (AuthenticationSuccessEvent) appEvent;
            // add code here to handle successful login event
//            userRepository.changeFailedAttempts(getLoggedInUser().getUserid());
            System.out.println("Login successful");
        }

        if (appEvent instanceof AuthenticationFailureBadCredentialsEvent)
        {
            AuthenticationFailureBadCredentialsEvent event = (AuthenticationFailureBadCredentialsEvent) appEvent;

            // add code here to handle unsuccessful login event
            // for example, counting the number of login failure attempts and storing it in db
            // this count can be used to lock or disable any user account as per business requirements
            String username = (String) event.getAuthentication().getPrincipal();
            User user=userRepository.findByUsername(username);
            if(user.getFailedAttempts()>=3)
            {
                user.setIs_active(false);
            }
            else {
                user.setFailedAttempts(user.getFailedAttempts()+1);
            }
            userRepository.save(user);
            System.out.println("Login failed Attempts remaining "+(3-user.getFailedAttempts()));
        }
    }
}