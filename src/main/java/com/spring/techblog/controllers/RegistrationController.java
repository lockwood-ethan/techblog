package com.spring.techblog.controllers;

import com.spring.techblog.dtos.RegistrationRequest;
import com.spring.techblog.jwt.JwtUtils;
import com.spring.techblog.jwt.LoginController;
import com.spring.techblog.jwt.LoginRequest;
import com.spring.techblog.models.Authorities;
import com.spring.techblog.models.Users;
import com.spring.techblog.repositories.AuthoritiesRepository;
import com.spring.techblog.repositories.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/register")
public class RegistrationController {

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

    @PostMapping
    public ResponseEntity<?> register(@RequestBody RegistrationRequest registrationRequest) {
        if (userRepository.existsByUsername(registrationRequest.getUsername())) {
            return ResponseEntity.badRequest().body("Error: Username is already in use.");
        }

        Users user = new Users(registrationRequest.getUsername(), passwordEncoder.encode(registrationRequest.getPassword()), true, "USER");
        Authorities authorities = new Authorities(registrationRequest.getUsername(), "USER");
        try {
            userRepository.save(user);
            authoritiesRepository.save(authorities);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }

        UserDetails userDetails = org.springframework.security.core.userdetails.User.withUsername(user.getUsername())
                .password(user.getPassword())
                .roles("USER")
                .build();

        String jwt = jwtUtils.generateTokenFromUsername(userDetails);
        Map<String, String> response = new HashMap<>();
        response.put("message", "User registered successfully.");
        response.put("token", jwt);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
