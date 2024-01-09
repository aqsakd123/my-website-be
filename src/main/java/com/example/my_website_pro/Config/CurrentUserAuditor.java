package com.example.my_website_pro.Config;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

public class CurrentUserAuditor implements AuditorAware<String> {
    @Override
    public Optional<String> getCurrentAuditor() {
        Authentication authen = SecurityContextHolder.getContext().getAuthentication();
        if (authen != null && authen.isAuthenticated()){
            return Optional.of(authen.getName());
        }
        return Optional.empty();
    }
}