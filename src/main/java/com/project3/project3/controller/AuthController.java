package com.project3.project3.controller;

import com.project3.project3.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;


import java.util.Collections;
import java.util.Optional;


@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<String> authenticateUser(@RequestBody LoginRequest loginRequest) {
        // Create authentication token
        UsernamePasswordAuthenticationToken authRequest =
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword());

        try {
            // Authenticate the user

            Authentication authentication = authenticationManager.authenticate(authRequest);
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            return ResponseEntity.ok("User authenticated: " + userDetails.getUsername());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
    }

    // Define LoginRequest as an inner class or a separate class as needed
    public static class LoginRequest {
        private String username;
        private String password;

        // Getters and setters
        public String getUsername() { return username; }
        public void setUsername(String username) { this.username = username; }
        public String getPassword() { return password; }
        public void setPassword(String password) { this.password = password; }
    }
}

            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
            );

            UserDetails userDetails = userService.loadUserByUsername(authRequest.getUsername());
            Optional<User> userOptional = userService.findUserByUsername(authRequest.getUsername());

            if (userOptional.isPresent()) {
                String userId = userOptional.get().getId();
                String token = jwtService.generateToken(userId, userDetails.getAuthorities());
                return ResponseEntity.ok(new AuthResponse(token));
            } else {
                return ResponseEntity.status(404).body("User not found with username: " + authRequest.getUsername());
            }

        } catch (AuthenticationException e) {
            return ResponseEntity.status(401).body("Invalid username or password");
        } catch (Exception ex) {
            return ResponseEntity.status(500).body("An internal server error occurred");
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        try {
            user.setRoles(Collections.singletonList("ROLE_USER"));
            userService.saveUser(user);
            return ResponseEntity.ok("User registered successfully. Please log in.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("An error occurred while creating the account.");
        }
    }
}



