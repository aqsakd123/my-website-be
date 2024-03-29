package com.example.my_website_pro.Config;

import com.example.my_website_pro.Entity.DTO.UserDTO;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

public class CurrentUserAuditor implements AuditorAware<String> {
    @Override
    public Optional<String> getCurrentAuditor() {
        Authentication authen = SecurityContextHolder.getContext().getAuthentication();
        if (authen != null && authen.isAuthenticated()){
            UserDTO user = (UserDTO) authen.getPrincipal();
            return Optional.of(user.getUsername());
        }
        return Optional.empty();
    }
}