package com.example.my_website_pro.Config;

import com.example.my_website_pro.Entity.DTO.UserDTO;
import com.example.my_website_pro.Entity.Role;
import com.example.my_website_pro.Service.AuthenticationService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.annotation.PostConstruct;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class UserAuthenticationProvider {

    private String jwtSecretKey = "SECRET_JWT_MEOW_MEOW";
    private String refreshSecretKey = "SECRET_REFRESH_MEOW_MEOW";

    private final AuthenticationService authenticationService;

    public UserAuthenticationProvider(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostConstruct
    protected void init() {
        jwtSecretKey = Base64.getEncoder().encodeToString(jwtSecretKey.getBytes());
        refreshSecretKey = Base64.getEncoder().encodeToString(refreshSecretKey.getBytes());
    }

    public String createToken(String login, List<Role> roles) {
        Claims claims = Jwts.claims().setSubject(login);

        List<String> role = new ArrayList<>();
        roles.forEach((item) -> {
            role.add(item.getCode());
        });

        claims.put("role", role);

        Date now = new Date();
        Date expiration = new Date(now.getTime() + 10*1000); // 1 hour

        return Jwts.builder()
                .setClaims(claims)
                .setIssuer(login)
                .setIssuedAt(now)
                .setExpiration(expiration)
                .signWith(SignatureAlgorithm.HS256, jwtSecretKey)
                .compact();
    }

    public String createRefreshToken(String login) {
        Claims claims = Jwts.claims().setSubject(login);

        Date now = new Date();
        Date expiration = new Date(now.getTime() + 30L *24*60*60*1000); // 1 month

        return Jwts.builder()
                .setClaims(claims)
                .setIssuer(login)
                .setIssuedAt(now)
                .setExpiration(expiration)
                .signWith(SignatureAlgorithm.HS256, refreshSecretKey)
                .compact();
    }

    public Authentication validateToken(String token) {
        String login = Jwts.parser()
                .setSigningKey(jwtSecretKey)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();

        UserDTO user = authenticationService.findByLogin(login);
        return new UsernamePasswordAuthenticationToken(user, null, Collections.emptyList());
    }

//    public Authentication validateCredentials(CredentialsDto credentialsDto) {
//        UserDto user = authenticationService.authenticate(credentialsDto);
//        return new UsernamePasswordAuthenticationToken(user, null, Collections.emptyList());
//    }


}