package com.project3.project3.controller;

import com.project3.project3.model.User;
import com.project3.project3.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AdminControllerTest {

    @InjectMocks
    private AdminController adminController;

    @Mock
    private UserService userService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateUser_Success() {
        User user = new User();
        user.setId("1");
        user.setUsername("johndoe");

        when(userService.saveUser(user)).thenReturn(user);

        ResponseEntity<User> response = adminController.createUser(user);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("johndoe", response.getBody().getUsername());
    }

    @Test
    public void testCreateUser_Failure() {
        User user = new User();

        when(userService.saveUser(user)).thenReturn(null);

        ResponseEntity<User> response = adminController.createUser(user);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    public void testGetAllUsers() {
        User user1 = new User();
        user1.setId("1");
        user1.setUsername("johndoe");

        User user2 = new User();
        user2.setId("2");
        user2.setUsername("janedoe");

        when(userService.getAllUsers()).thenReturn(Arrays.asList(user1, user2));

        ResponseEntity<List<User>> response = adminController.getAllUsers();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
    }

    @Test
    public void testAssignRoleToUser_Success() {
        User user = new User();
        user.setId("1");
        user.setRoles(Arrays.asList("ROLE_ADMIN"));

        when(userService.assignRole("1", "ROLE_ADMIN")).thenReturn(Optional.of(user));

        ResponseEntity<User> response = adminController.assignRoleToUser("1", "ROLE_ADMIN");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().getRoles().contains("ROLE_ADMIN"));
    }

    @Test
    public void testAssignRoleToUser_Failure() {
        when(userService.assignRole("1", "ROLE_ADMIN")).thenReturn(Optional.empty());

        ResponseEntity<User> response = adminController.assignRoleToUser("1", "ROLE_ADMIN");

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    public void testGetUserRoles_Success() {
        when(userService.getUserRoles("1")).thenReturn(Optional.of(Arrays.asList("ROLE_ADMIN", "ROLE_USER")));

        ResponseEntity<List<String>> response = adminController.getUserRoles("1");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
        assertTrue(response.getBody().contains("ROLE_ADMIN"));
    }

    @Test
    public void testGetUserRoles_Failure() {
        when(userService.getUserRoles("1")).thenReturn(Optional.empty());

        ResponseEntity<List<String>> response = adminController.getUserRoles("1");

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }
}

