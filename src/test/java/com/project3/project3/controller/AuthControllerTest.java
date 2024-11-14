package com.project3.project3.controller;

import com.project3.project3.controller.AuthController.LoginRequest;
import com.project3.project3.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import java.util.Collections;

public class AuthControllerTest {

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private UserService userService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private AuthController authController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAuthenticateUser() {
        // Arrange
        String username = "testuser";
        String password = "password";

        // Mock login request
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername(username);
        loginRequest.setPassword(password);

        // Create an authentication token
        UsernamePasswordAuthenticationToken authToken =
                new UsernamePasswordAuthenticationToken(username, password);

        // Mock successful authentication
        Authentication authentication = mock(Authentication.class);
        when(authenticationManager.authenticate(authToken)).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(new org.springframework.security.core.userdetails.User(username, password, Collections.emptyList()));

        // Act
        ResponseEntity<String> response = authController.authenticateUser(loginRequest);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("User authenticated: testuser", response.getBody());

        // Verify interactions
        verify(authenticationManager, times(1)).authenticate(authToken);
    }
}
