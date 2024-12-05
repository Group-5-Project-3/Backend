package com.project3.project3.controller;

import com.project3.project3.model.AuthRequest;
import com.project3.project3.model.AuthResponse;
import com.project3.project3.model.User;
import com.project3.project3.service.JwtService;
import com.project3.project3.service.MilestonesService;
import com.project3.project3.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthControllerTest {

    @InjectMocks
    private AuthController authController;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtService jwtService;

    @Mock
    private UserService userService;

    @Mock
    private MilestonesService milestonesService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testLogin_Success() {
        // Arrange
        AuthRequest authRequest = new AuthRequest("testuser", "password");
        UserDetails userDetails = mock(UserDetails.class);
        User user = new User();
        user.setId("123");
        user.setUsername("testuser");

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(null);
        when(userService.loadUserByUsername("testuser")).thenReturn(userDetails);
        when(userService.findUserByUsername("testuser")).thenReturn(Optional.of(user));
        when(jwtService.generateToken("123", userDetails.getAuthorities())).thenReturn("mockToken");

        // Act
        ResponseEntity<?> response = authController.login(authRequest);

        // Assert
        assertEquals(200, response.getStatusCode().value());
        assertTrue(response.getBody() instanceof AuthResponse);
        assertEquals("mockToken", ((AuthResponse) response.getBody()).getToken());
    }

    @Test
    void testLogin_UserNotFound() {
        // Arrange
        AuthRequest authRequest = new AuthRequest("nonexistent", "password");

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(null);
        when(userService.findUserByUsername("nonexistent")).thenReturn(Optional.empty());

        // Act
        ResponseEntity<?> response = authController.login(authRequest);

        // Assert
        assertEquals(404, response.getStatusCode().value());
        assertEquals("User not found with username: nonexistent", response.getBody());
    }

    @Test
    void testLogin_InvalidCredentials() {
        // Arrange
        AuthRequest authRequest = new AuthRequest("testuser", "wrongpassword");

        doThrow(BadCredentialsException.class).when(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));

        // Act
        ResponseEntity<?> response = authController.login(authRequest);

        // Assert
        assertEquals(401, response.getStatusCode().value());
        assertEquals("Invalid username or password", response.getBody());
    }

    @Test
    void testRegister_Success() {
        // Arrange
        User user = new User();
        user.setUsername("newuser");
        user.setEmail("newuser@example.com");

        User savedUser = new User();
        savedUser.setId("123");
        savedUser.setUsername("newuser");

        when(userService.saveUser(user)).thenReturn(savedUser);

        // Act
        ResponseEntity<?> response = authController.register(user);

        // Assert
        assertEquals(200, response.getStatusCode().value());
        assertEquals("User registered successfully. Please log in.", response.getBody());
        verify(milestonesService, times(1)).createMilestones("123");
    }

    @Test
    void testRegister_Error() {
        // Arrange
        User user = new User();
        user.setUsername("erroruser");

        when(userService.saveUser(user)).thenThrow(RuntimeException.class);

        // Act
        ResponseEntity<?> response = authController.register(user);

        // Assert
        assertEquals(500, response.getStatusCode().value());
        assertEquals("An error occurred while creating the account.", response.getBody());
    }
}
