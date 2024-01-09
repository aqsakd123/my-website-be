package com.example.my_website_pro.Entity.DTO;

import com.example.my_website_pro.Entity.Role;
import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class UserDTO {

    private Long id;
    private String firstName;
    private String lastName;
    private String username;
    private String token;
    private String refreshToken;
    private List<Role> roles;
}