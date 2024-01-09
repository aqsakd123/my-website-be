package com.example.my_website_pro.Service.Impl;

import com.example.my_website_pro.Config.AppException;
import com.example.my_website_pro.Entity.DTO.CredenticalsDTO;
import com.example.my_website_pro.Entity.DTO.UserDTO;
import com.example.my_website_pro.Entity.Role;
import com.example.my_website_pro.Entity.User;
import com.example.my_website_pro.Repository.UserRepository;
import com.example.my_website_pro.Service.AuthenticationService;
import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.nio.CharBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class AuthenticationServiceImpl implements AuthenticationService {

    @Autowired
    private UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public AuthenticationServiceImpl(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDTO login(CredenticalsDTO credentialsDto) {
        User user = userRepository.findByUsername(credentialsDto.getUsername())
                .orElseThrow(() -> new AppException("USER_UNKNOWN", HttpStatus.NOT_FOUND));
        if (passwordEncoder.matches(CharBuffer.wrap(credentialsDto.getPassword()), user.getPassword())) {
            return toUserDTO(user);
        }
        throw new AppException("WRONG_PASSWORD", HttpStatus.UNAUTHORIZED);
    }

    @Override
    public UserDTO register(CredenticalsDTO userDto) {
        Optional<User> optionalUser = userRepository.findByUsername(userDto.getUsername());
        if (optionalUser.isPresent()) {
            throw new AppException("USERNAME_EXISTED", HttpStatus.BAD_REQUEST);
        }
        List<Role> roles = new ArrayList<>();
        roles.add(Role.builder().id(1L).build());
        User user = toUser(userDto);
        user.setRoles(roles);
        user.setPassword(passwordEncoder.encode(CharBuffer.wrap(userDto.getPassword())));
        User savedUser = userRepository.save(user);
        return toUserDTO(savedUser);
    }

    @Override
    public UserDTO findByLogin(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new AppException("JWT.WRONG", HttpStatus.UNAUTHORIZED));
        return toUserDTO(user);
    }

    // Convert logic to Mapper Instance
    private UserDTO toUserDTO(Object user){
        UserDTO result = new UserDTO();
        BeanUtils.copyProperties(user, result);
        return result;
    }

    private User toUser(Object user){
        User result = new User();
        BeanUtils.copyProperties(user, result);
        return result;
    }
}