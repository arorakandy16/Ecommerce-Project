//package com.project.ecommerce.security;
//
//import com.project.ecommerce.entities.User;
//import com.project.ecommerce.repositries.UserRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.ApplicationListener;
//import org.springframework.security.authentication.event.AbstractAuthenticationEvent;
//import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
//import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
//import org.springframework.stereotype.Component;
//
//@Component
//public class AuthenticationListener implements ApplicationListener<AbstractAuthenticationEvent>
//{
//
//    @Autowired
//    UserRepository userRepository;
//    @Override
//    public void onApplicationEvent(AbstractAuthenticationEvent appEvent)
//    {
//        if (appEvent instanceof AuthenticationSuccessEvent)
//        {
//            AuthenticationSuccessEvent event = (AuthenticationSuccessEvent) appEvent;
//            String username = (String) event.getAuthentication().getPrincipal();
//            User user=userRepository.findByUsername(username);
//            user.setFailedAttempts(0);
//            userRepository.save(user);
//            System.out.println("Login successful ");
//        }
//
//        if (appEvent instanceof AuthenticationFailureBadCredentialsEvent)
//        {
//            AuthenticationFailureBadCredentialsEvent event = (AuthenticationFailureBadCredentialsEvent) appEvent;
//
//            String username = (String) event.getAuthentication().getPrincipal();
//            User user=userRepository.findByUsername(username);
//            if(user.getFailedAttempts()>=3)
//            {
//                user.setIs_active(false);
//            }
//            else {
//                user.setFailedAttempts(user.getFailedAttempts()+1);
//            }
//            userRepository.save(user);
//            System.out.println("Login failed Attempts remaining "+(3-user.getFailedAttempts()));
//        }
//    }
//}