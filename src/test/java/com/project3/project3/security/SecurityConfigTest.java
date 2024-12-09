package com.project3.project3.security;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class SecurityConfigTest {

    private final SecurityConfig securityConfig = new SecurityConfig();

    @Test
    public void shouldReturnCorsConfigurationSource() {
        assertNotNull(securityConfig.corsConfigurationSource());
    }

    @Test
    public void shouldConfigureSecurityFilterChain() throws Exception {
        // Mock HttpSecurity and its chained methods
        HttpSecurity http = mock(HttpSecurity.class, Mockito.RETURNS_DEEP_STUBS);

        // Mock behavior of HttpSecurity chained methods
        when(http.csrf(any())).thenReturn(http);
        when(http.cors(any())).thenReturn(http);
        when(http.authorizeHttpRequests(any())).thenReturn(http);
        when(http.sessionManagement(any())).thenReturn(http);
        when(http.addFilterBefore(any(), any())).thenReturn(http);

        // Call securityFilterChain method
        SecurityFilterChain securityFilterChain = securityConfig.securityFilterChain(http);

        // Validate that the SecurityFilterChain is not null
        assertNotNull(securityFilterChain);
    }

    @Test
    public void shouldReturnPasswordEncoder() {
        PasswordEncoder passwordEncoder = securityConfig.passwordEncoder();
        assertNotNull(passwordEncoder);
    }

    @Test
    public void shouldReturnAuthenticationManager() throws Exception {
        AuthenticationConfiguration authenticationConfiguration = mock(AuthenticationConfiguration.class);
        AuthenticationManager authenticationManager = mock(AuthenticationManager.class);

        // Mock the behavior of authenticationConfiguration.getAuthenticationManager
        when(authenticationConfiguration.getAuthenticationManager()).thenReturn(authenticationManager);

        AuthenticationManager result = securityConfig.authenticationManager(authenticationConfiguration);

        // Validate that the returned AuthenticationManager is not null
        assertNotNull(result);
    }
}
