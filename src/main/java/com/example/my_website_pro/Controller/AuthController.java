package com.example.my_website_pro.Controller;

import com.example.my_website_pro.Config.UserAuthenticationProvider;
import com.example.my_website_pro.Entity.DTO.CredenticalsDTO;
import com.example.my_website_pro.Entity.DTO.UserDTO;
import com.example.my_website_pro.Entity.User;
import com.example.my_website_pro.Repository.UserRepository;
import com.example.my_website_pro.Service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
public class AuthController {

    @Autowired
    AuthenticationService authenticationService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    UserAuthenticationProvider userAuthenticationProvider;

    @PostMapping("/auth/signIn")
    public ResponseEntity<UserDTO> login(@RequestBody @Valid CredenticalsDTO credentialsDto) {
        UserDTO userDto = authenticationService.login(credentialsDto);
        userDto.setToken(userAuthenticationProvider.createToken(userDto.getUsername(), userDto.getRoles()));
        userDto.setRefreshToken(userAuthenticationProvider.createRefreshToken(userDto.getUsername()));
        return ResponseEntity.ok(userDto);
    }

    // TODO: Test only, delete when deploy
    @PostMapping("/api/listUser")
    public ResponseEntity<List<User>> listUser() {
        return ResponseEntity.ok(userRepository.findAll());
    }

    @PostMapping("/auth/register")
    public ResponseEntity<UserDTO> register(@RequestBody @Valid CredenticalsDTO user) {
        UserDTO createdUser = authenticationService.register(user);
        return ResponseEntity.ok(createdUser);
    }

    @PostMapping("/auth/refresh")
    public ResponseEntity<UserDTO> refresh(@RequestBody @Valid UserDTO userDto) {
        userDto.setToken(userAuthenticationProvider.createToken(userDto.getUsername(), userDto.getRoles()));
        return ResponseEntity.ok(userDto);
    }

    @PostMapping("/signOut")
    public ResponseEntity<Void> signOut() {
        SecurityContextHolder.clearContext();
        return ResponseEntity.noContent().build();
    }

}