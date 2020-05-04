package com.project.ecommerce.auditing;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;


//  Auditing Author Using AuditorAware

public class AuditorAwareImpl implements AuditorAware<String> {

    /*
    @Override
    public Optional<String> getCurrentAuditor() {
        return Optional.of("Ramesh");
         Use below commented code when will use Spring Security.
    }
    */



    //For Spring Security Use this code

    @Override
    public Optional<String> getCurrentAuditor() {
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null ||
                authentication.getPrincipal().equals("anonymousUser") ||
                !authentication.isAuthenticated()) {
            return Optional.of("Kandy");
        }

        return Optional.ofNullable
                (((UserDetails) authentication.getPrincipal()).getUsername());
    }
}