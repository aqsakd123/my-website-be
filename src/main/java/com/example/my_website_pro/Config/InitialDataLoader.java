package com.example.my_website_pro.Config;

import com.example.my_website_pro.Entity.Role;
import com.example.my_website_pro.Repository.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class InitialDataLoader {

    private final RoleRepository roleRepository;

    public InitialDataLoader(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Bean
    public CommandLineRunner initializeRoles() {
        return args -> {
            if (roleRepository.count() == 0) {
                Role userRole = new Role(1L, "user", "USER");
                Role adminRole = new Role(2L, "admin", "ADMIN");

                roleRepository.saveAll(List.of(userRole, adminRole));
            }
        };
    }
}
