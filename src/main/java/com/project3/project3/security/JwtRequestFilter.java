package com.project3.project3.security;

import com.project3.project3.service.JwtService;
import com.project3.project3.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    @Autowired
    private JwtService jwtService;

    @Autowired
    @Lazy
    private UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        // Get the Authorization header
        final String authorizationHeader = request.getHeader("Authorization");

        String userId = null;
        String jwt = null;

        // Check if the Authorization header contains a Bearer token
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            jwt = authorizationHeader.substring(7);  // Extract the JWT token
            userId = jwtService.extractUserId(jwt);  // Extract userId from token
        }

        // If we have a userId and no authentication is set in the security context, proceed
        if (userId != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            // Fetch the user details from the database using UserService
            UserDetails userDetails = userService.loadUserById(userId);  // Load user by userId

            // Validate the token against the user's ID
            if (jwtService.validateToken(jwt, userId)) {

                // Create the UsernamePasswordAuthenticationToken object
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());

                // Set the authentication details
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // Set the authentication in the SecurityContext
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }
        chain.doFilter(request, response);
    }
}

