package com.project3.project3.controller;

import com.project3.project3.model.AuthRequest;
import com.project3.project3.model.AuthResponse;
import com.project3.project3.model.User;
import com.project3.project3.service.JwtService;
import com.project3.project3.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import java.util.Collections;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest authRequest) {
        try {
            // Authenticate the user
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
            );
            UserDetails userDetails = userService.loadUserByUsername(authRequest.getUsername());
            String userId = userService.findUserByUsername(authRequest.getUsername())
                    .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + authRequest.getUsername()))
                    .getId();
            String token = jwtService.generateToken(userId, userDetails.getAuthorities());
            return ResponseEntity.ok(new AuthResponse(token));
        } catch (AuthenticationException e) {
            return ResponseEntity.status(401).body("Invalid username or password");
        } catch (Exception ex) {
            return ResponseEntity.status(500).body("An internal server error occurred");
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        try {
            // Set the default role
            user.setRoles(Collections.singletonList("ROLE_USER"));

            // Save the user (ensure password hashing with BCrypt)
            userService.saveUser(user);

            return ResponseEntity.ok("User registered successfully. Please log in.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("An error occurred while creating the account.");
        }
    }
}

