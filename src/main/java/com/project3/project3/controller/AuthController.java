package com.project3.project3.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project3.project3.model.AuthRequest;
import com.project3.project3.model.AuthResponse;
import com.project3.project3.model.User;
import com.project3.project3.service.JwtService;
import com.project3.project3.service.MilestonesService;
import com.project3.project3.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Collections;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserService userService;

    @Autowired
    private MilestonesService milestonesService;

    private final String googleClientId = System.getenv("GOOGLE_CLIENT_ID");
    private final String googleClientSecret = System.getenv("GOOGLE_CLIENT_SECRET");
    private final String googleRedirectUri = System.getenv("GOOGLE_REDIRECT_URI");

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest authRequest) {
        try {
            // Authenticate the user
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
            User savedUser = userService.saveUser(user);
            milestonesService.createMilestones(savedUser.getId());
            return ResponseEntity.ok("User registered successfully. Please log in.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("An error occurred while creating the account.");
        }
    }

    @GetMapping("/google-login")
    public RedirectView googleLogin() {
        String googleAuthUrl = "https://accounts.google.com/o/oauth2/v2/auth" +
                "?client_id=" + googleClientId +
                "&redirect_uri=" + googleRedirectUri +
                "&response_type=code" +
                "&scope=profile email";

        return new RedirectView(googleAuthUrl);
    }

    @GetMapping("/google-callback")
    public ResponseEntity<?> googleCallback(@RequestParam("code") String code) {
        try {
            String googleToken = exchangeCodeForGoogleToken(code);
            if (googleToken == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Google authentication failed.");
            }

            User googleUser = fetchGoogleUserProfile(googleToken);
            if (googleUser == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Failed to retrieve Google user information.");
            }

            User user = userService.findOrCreateGoogleUser(googleUser);
            milestonesService.createMilestones(user.getId());
            String jwtToken = jwtService.generateToken(user.getId(), user.getAuthorities());
            return ResponseEntity.ok(new AuthResponse(jwtToken));

        } catch (Exception e) {
            return ResponseEntity.status(500).body("An error occurred during Google authentication.");
        }
    }

    // Exchange authorization code for Google access token
    private String exchangeCodeForGoogleToken(String code) {
        RestTemplate restTemplate = new RestTemplate();
        String tokenRequestUrl = "https://oauth2.googleapis.com/token";

        // Headers and body for POST request to exchange code for token
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        String body = "code=" + code +
                "&client_id=" + googleClientId +
                "&client_secret=" + googleClientSecret +
                "&redirect_uri=" + googleRedirectUri +
                "&grant_type=authorization_code";

        HttpEntity<String> entity = new HttpEntity<>(body, headers);
        ResponseEntity<String> response = restTemplate.exchange(tokenRequestUrl, HttpMethod.POST, entity, String.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            try {
                ObjectMapper mapper = new ObjectMapper();
                JsonNode jsonNode = mapper.readTree(response.getBody());
                return jsonNode.get("access_token").asText();
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
        return null;
    }

    // Fetch Google user profile from access token
    private User fetchGoogleUserProfile(String accessToken) {
        RestTemplate restTemplate = new RestTemplate();
        String profileRequestUrl = "https://www.googleapis.com/oauth2/v3/userinfo";

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);

        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(profileRequestUrl, HttpMethod.GET, entity, String.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            try {
                ObjectMapper mapper = new ObjectMapper();
                JsonNode profileJson = mapper.readTree(response.getBody());

                String googleUserId = profileJson.get("sub").asText();
                String email = profileJson.get("email").asText();
                String name = profileJson.get("name").asText();

                // Map Google profile to User
                User googleUser = new User();
                googleUser.setUsername(email);
                googleUser.setEmail(email);
                googleUser.setGoogleUserId(googleUserId);
                googleUser.setRoles(Collections.singletonList("ROLE_USER"));
                googleUser.setName(name);

                return googleUser;

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
        return null;
    }

}



