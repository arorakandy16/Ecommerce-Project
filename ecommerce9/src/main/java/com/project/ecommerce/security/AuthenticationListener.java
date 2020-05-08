package com.project.ecommerce.security;

import com.project.ecommerce.entity.User;
import com.project.ecommerce.repository.UserRepository;
import com.project.ecommerce.service.EmailService;
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
    @Autowired
    EmailService emailService;

    @Override
    public void onApplicationEvent(AbstractAuthenticationEvent appEvent)
    {

        if (appEvent instanceof AuthenticationSuccessEvent) {

//            AuthenticationSuccessEvent event = (AuthenticationSuccessEvent) appEvent;

            try {
                AppUser username = (AppUser) appEvent.getAuthentication().getPrincipal();

                User user = userRepository.findByUsername(username.getUsername());

                user.setFailedAttempts(0);

                userRepository.save(user);
            }

            catch (Exception ignored){
            }

        }

        if (appEvent instanceof AuthenticationFailureBadCredentialsEvent) {

            AuthenticationFailureBadCredentialsEvent event =
                    (AuthenticationFailureBadCredentialsEvent) appEvent;

            // to handle unsuccessful login event

            String username = (String) event.getAuthentication().getPrincipal();

            User user=userRepository.findByUsername(username);

            if(user.getFailedAttempts()==2)
            {
                user.setIs_active(false);

                emailService.sendEmail
                        ("ACCOUNT SECURITY ISSUE",
                        "Hii, \n As we analyse, " +
                                "There are a lot of Invalid Login Requests " +
                                "send through your account. " +
                                "As a result, your account has been temporarily Locked." +
                        "You can access it again within 24 Hours  ",
                                username);
            }
            else {

                user.setFailedAttempts(user.getFailedAttempts()+1);

            }

            userRepository.save(user);

        }
    }
}