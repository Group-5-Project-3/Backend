package com.project3.project3.security;

import com.project3.project3.service.JwtService;
import com.project3.project3.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class JwtRequestFilterTest {

    @InjectMocks
    private JwtRequestFilter jwtRequestFilter;

    @Mock
    private JwtService jwtService;

    @Mock
    private UserService userService;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private FilterChain chain;

    @BeforeEach
    public void setUp() {
        // Clear the security context before each test
        SecurityContextHolder.clearContext();
    }

    @Test
    public void shouldBypassFilterForAuthEndpoint() throws ServletException, IOException {
        // Mock request to an auth endpoint
        when(request.getRequestURI()).thenReturn("/api/auth/login");

        // Execute the filter
        jwtRequestFilter.doFilterInternal(request, response, chain);

        // Verify that the filter bypassed further processing
        verify(chain, times(1)).doFilter(request, response);
        verify(jwtService, never()).extractUserId(anyString());
    }

    @Test
    public void shouldAuthenticateUserWithValidToken() throws ServletException, IOException {
        // Mock a valid JWT token
        when(request.getHeader("Authorization")).thenReturn("Bearer validToken");
        when(jwtService.extractUserId("validToken")).thenReturn("userId");
        when(jwtService.validateToken("validToken", "userId")).thenReturn(true);

        // Mock user details
        UserDetails userDetails = new User("userId", "password", new ArrayList<>());
        when(userService.loadUserById("userId")).thenReturn(userDetails);

        // Mock request URI
        when(request.getRequestURI()).thenReturn("/some/path");

        // Execute the filter
        jwtRequestFilter.doFilterInternal(request, response, chain);

        // Assert that authentication was set in the SecurityContext
        assertNotNull(SecurityContextHolder.getContext().getAuthentication());
        assertEquals("userId", SecurityContextHolder.getContext().getAuthentication().getName());

        // Verify the chain continued processing
        verify(chain, times(1)).doFilter(request, response);
    }

    @Test
    public void shouldNotAuthenticateUserWithInvalidToken() throws ServletException, IOException {
        // Mock an invalid JWT token
        when(request.getHeader("Authorization")).thenReturn("Bearer invalidToken");
        when(jwtService.extractUserId("invalidToken")).thenReturn("userId");
        when(jwtService.validateToken("invalidToken", "userId")).thenReturn(false);

        // Mock user details
        when(userService.loadUserById("userId")).thenReturn(null);

        // Mock request URI
        when(request.getRequestURI()).thenReturn("/some/path");

        // Execute the filter
        jwtRequestFilter.doFilterInternal(request, response, chain);

        // Assert that no authentication was set in the SecurityContext
        assertNull(SecurityContextHolder.getContext().getAuthentication());

        // Verify the chain continued processing
        verify(chain, times(1)).doFilter(request, response);
    }
}