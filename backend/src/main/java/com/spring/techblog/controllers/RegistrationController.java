package com.spring.techblog.controllers;

import com.spring.techblog.dtos.RegistrationRequest;
import com.spring.techblog.jwt.JwtUtils;
import com.spring.techblog.jwt.LoginResponse;
import com.spring.techblog.models.Authorities;
import com.spring.techblog.models.Users;
import com.spring.techblog.repositories.AuthoritiesRepository;
import com.spring.techblog.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
public class RegistrationController {

    @Autowired
    DataSource dataSource;

    @Autowired
    AuthenticationManager authenticationManager;

    private final AuthoritiesRepository authoritiesRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;

    public RegistrationController(AuthoritiesRepository authoritiesRepository, UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtils jwtUtils) {
        this.authoritiesRepository = authoritiesRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtils = jwtUtils;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegistrationRequest registrationRequest) {
        if (userRepository.existsByUsername(registrationRequest.getUsername())) {
            return ResponseEntity.badRequest().body("Error: Username is already in use.");
        }

        Users user = new Users(registrationRequest.getUsername(), passwordEncoder.encode(registrationRequest.getPassword()), true);
        Authorities authorities = new Authorities(registrationRequest.getUsername(), "USER");

        UserDetails userDetails = User.withUsername(user.getUsername())
                .password(user.getPassword())
                .roles(authorities.getAuthority())
                .build();

        JdbcUserDetailsManager userDetailsManager = new JdbcUserDetailsManager(dataSource);
        userDetailsManager.createUser(userDetails);
        Authentication authentication;
        try {
            authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(registrationRequest.getUsername(), registrationRequest.getPassword()));
        } catch (AuthenticationException exception) {
            Map<String, Object> error = new HashMap<>();
            error.put("message", "Error: " + exception.getMessage());
            error.put("status", false);
            return new ResponseEntity<Object>(error, HttpStatus.NOT_FOUND);
        }

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwtAccessToken= jwtUtils.generateAccessTokenFromUsername(userDetails.getUsername());
        String jwtRefreshToken = jwtUtils.generateRefreshTokenFromUsername(userDetails.getUsername());
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        LoginResponse response = new LoginResponse(userDetails.getUsername(), roles, jwtAccessToken, jwtRefreshToken);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
