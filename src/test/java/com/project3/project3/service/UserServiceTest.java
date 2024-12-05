package com.project3.project3.service;

import com.project3.project3.model.User;
import com.project3.project3.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testLoadUserByUsername_Found() {
        // Arrange
        String username = "testuser";
        User user = new User();
        user.setUsername(username);
        user.setPassword("hashedpassword");
        user.setRoles(List.of("ROLE_USER"));
        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));

        // Act
        UserDetails result = userService.loadUserByUsername(username);

        // Assert
        assertEquals(username, result.getUsername());
        assertEquals("hashedpassword", result.getPassword());
        assertTrue(result.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("ROLE_USER")));
        verify(userRepository, times(1)).findByUsername(username);
    }

    @Test
    void testLoadUserByUsername_NotFound() {
        // Arrange
        String username = "nonexistent";
        when(userRepository.findByUsername(username)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(UsernameNotFoundException.class, () -> userService.loadUserByUsername(username));
        verify(userRepository, times(1)).findByUsername(username);
    }

    @Test
    void testFindUserByUsername_Found() {
        // Arrange
        String username = "testuser";
        User user = new User();
        user.setUsername(username);
        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));

        // Act
        Optional<User> result = userService.findUserByUsername(username);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(username, result.get().getUsername());
        verify(userRepository, times(1)).findByUsername(username);
    }

    @Test
    void testFindUserByUsername_NotFound() {
        // Arrange
        String username = "nonexistent";
        when(userRepository.findByUsername(username)).thenReturn(Optional.empty());

        // Act
        Optional<User> result = userService.findUserByUsername(username);

        // Assert
        assertFalse(result.isPresent());
        verify(userRepository, times(1)).findByUsername(username);
    }

    @Test
    void testSaveUser() {
        // Arrange
        User user = new User();
        user.setPassword("plaintextpassword");
        when(passwordEncoder.encode("plaintextpassword")).thenReturn("hashedpassword");
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        User result = userService.saveUser(user);

        // Assert
        assertEquals("hashedpassword", result.getPassword());
        assertEquals(List.of("ROLE_USER"), result.getRoles());
        verify(passwordEncoder, times(1)).encode("plaintextpassword");
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void testDeleteUserById_Success() {
        // Arrange
        String userId = "user123";
        when(userRepository.existsById(userId)).thenReturn(true);

        // Act
        boolean result = userService.deleteUserById(userId);

        // Assert
        assertTrue(result);
        verify(userRepository, times(1)).existsById(userId);
        verify(userRepository, times(1)).deleteById(userId);
    }

    @Test
    void testDeleteUserById_NotFound() {
        // Arrange
        String userId = "nonexistent";
        when(userRepository.existsById(userId)).thenReturn(false);

        // Act
        boolean result = userService.deleteUserById(userId);

        // Assert
        assertFalse(result);
        verify(userRepository, times(1)).existsById(userId);
        verify(userRepository, never()).deleteById(userId);
    }

    @Test
    void testAssignRole() {
        // Arrange
        String userId = "user123";
        String role = "ROLE_ADMIN";
        User user = new User();
        user.setRoles(new ArrayList<>());
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        Optional<User> result = userService.assignRole(userId, role);

        // Assert
        assertTrue(result.isPresent());
        assertTrue(result.get().getRoles().contains(role));
        verify(userRepository, times(1)).findById(userId);
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void testRemoveRole() {
        // Arrange
        String userId = "user123";
        String role = "ROLE_USER";
        User user = new User();
        user.setRoles(new ArrayList<>(List.of(role)));
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        Optional<User> result = userService.removeRole(userId, role);

        // Assert
        assertTrue(result.isPresent());
        assertFalse(result.get().getRoles().contains(role));
        verify(userRepository, times(1)).findById(userId);
        verify(userRepository, times(1)).save(user);
    }
}
