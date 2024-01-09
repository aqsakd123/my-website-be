package com.example.my_website_pro.Service;

import com.example.my_website_pro.Entity.DTO.CredenticalsDTO;
import com.example.my_website_pro.Entity.DTO.UserDTO;

public interface AuthenticationService {

    UserDTO login(CredenticalsDTO credentialsDto);
    UserDTO register(CredenticalsDTO userDto);
    UserDTO findByLogin(String login);
}
