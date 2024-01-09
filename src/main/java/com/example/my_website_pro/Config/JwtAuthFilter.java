package com.example.my_website_pro.Config;

import com.example.my_website_pro.Entity.DTO.ErrorDTO;
import com.example.my_website_pro.Entity.DTO.UserDTO;
import com.example.my_website_pro.Entity.Role;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final UserAuthenticationProvider userAuthenticationProvider;
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @Override
    protected void doFilterInternal(
            HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse,
            FilterChain filterChain) throws ServletException, IOException {

        String header = httpServletRequest.getHeader(HttpHeaders.AUTHORIZATION);

        if (header != null) {
            String[] authElements = header.split(" ");

            if (authElements.length == 2
                    && "Bearer".equals(authElements[0])) {
                try {

                    Authentication authentication = userAuthenticationProvider.validateToken(authElements[1]);

                    UserDTO user = (UserDTO) authentication.getPrincipal();
                    Authentication authenticatedUser = new UsernamePasswordAuthenticationToken(
                            user,
                            null,
                            getAuthorities(user.getRoles())
                    );
                    SecurityContextHolder.getContext().setAuthentication(authenticatedUser);
                } catch (ExpiredJwtException e) {
                    httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    httpServletResponse.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
                    OBJECT_MAPPER.writeValue(httpServletResponse.getOutputStream(), new ErrorDTO("JWT.EXPIRED"));
                } catch (RuntimeException e) {
                    SecurityContextHolder.clearContext();
                    httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    httpServletResponse.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
                    OBJECT_MAPPER.writeValue(httpServletResponse.getOutputStream(), new ErrorDTO("JWT.WRONG"));
                }
            }
        }

        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

    private Collection<? extends GrantedAuthority> getAuthorities(List<Role> roleList) {
        return roleList.stream()
                .map(role -> new SimpleGrantedAuthority(role.getCode()))
                .collect(Collectors.toList());
    }

}