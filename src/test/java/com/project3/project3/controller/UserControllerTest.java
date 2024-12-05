package com.project3.project3.controller;

import com.project3.project3.model.User;
import com.project3.project3.service.ImageService;
import com.project3.project3.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class UserControllerTest {

    @InjectMocks
    private UserController userController;

    @Mock
    private UserService userService;

    @Mock
    private ImageService imageService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindUserIdByUsername_Success() {
        // Arrange
        String username = "testuser";
        User user = new User();
        user.setId("123");
        user.setUsername(username);
        when(userService.findUserByUsername(username)).thenReturn(Optional.of(user));

        // Act
        ResponseEntity<String> response = userController.findUserIdByUsername(username);

        // Assert
        assertEquals(200, response.getStatusCode().value());
        assertEquals("123", response.getBody());
        verify(userService, times(1)).findUserByUsername(username);
    }

    @Test
    void testFindUserIdByUsername_NotFound() {
        // Arrange
        String username = "nonexistent";
        when(userService.findUserByUsername(username)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<String> response = userController.findUserIdByUsername(username);

        // Assert
        assertEquals(404, response.getStatusCode().value());
        verify(userService, times(1)).findUserByUsername(username);
    }

    @Test
    void testFindUserById_Success() {
        // Arrange
        String userId = "123";
        User user = new User();
        user.setId(userId);
        when(userService.getUserById(userId)).thenReturn(Optional.of(user));

        // Act
        ResponseEntity<User> response = userController.findUserById(userId);

        // Assert
        assertEquals(200, response.getStatusCode().value());
        assertEquals(userId, response.getBody().getId());
        verify(userService, times(1)).getUserById(userId);
    }

    @Test
    void testFindUserById_NotFound() {
        // Arrange
        String userId = "nonexistent";
        when(userService.getUserById(userId)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<User> response = userController.findUserById(userId);

        // Assert
        assertEquals(404, response.getStatusCode().value());
        verify(userService, times(1)).getUserById(userId);
    }

    @Test
    void testGetAllUsers() {
        // Arrange
        User user1 = new User();
        user1.setId("1");
        User user2 = new User();
        user2.setId("2");
        when(userService.getAllUsers()).thenReturn(Arrays.asList(user1, user2));

        // Act
        ResponseEntity<List<User>> response = userController.getAllUsers();

        // Assert
        assertEquals(200, response.getStatusCode().value());
        assertEquals(2, response.getBody().size());
        verify(userService, times(1)).getAllUsers();
    }

    @Test
    void testCreateUser() {
        // Arrange
        User user = new User();
        user.setUsername("newuser");
        when(userService.saveUser(user)).thenReturn(user);

        // Act
        ResponseEntity<User> response = userController.createUser(user);

        // Assert
        assertEquals(200, response.getStatusCode().value());
        assertEquals("newuser", response.getBody().getUsername());
        verify(userService, times(1)).saveUser(user);
    }

    @Test
    void testUpdateUser_Success() {
        // Arrange
        String userId = "123";
        User updatedUser = new User();
        updatedUser.setUsername("updateduser");
        when(userService.updateUser(userId, updatedUser)).thenReturn(Optional.of(updatedUser));

        // Act
        ResponseEntity<User> response = userController.updateUser(userId, updatedUser);

        // Assert
        assertEquals(200, response.getStatusCode().value());
        assertEquals("updateduser", response.getBody().getUsername());
        verify(userService, times(1)).updateUser(userId, updatedUser);
    }

    @Test
    void testUpdateUser_NotFound() {
        // Arrange
        String userId = "nonexistent";
        User updatedUser = new User();
        when(userService.updateUser(userId, updatedUser)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<User> response = userController.updateUser(userId, updatedUser);

        // Assert
        assertEquals(404, response.getStatusCode().value());
        verify(userService, times(1)).updateUser(userId, updatedUser);
    }

    @Test
    void testDeleteUserById_Success() {
        // Arrange
        String userId = "123";
        when(userService.deleteUserById(userId)).thenReturn(true);

        // Act
        ResponseEntity<Void> response = userController.deleteUserById(userId);

        // Assert
        assertEquals(204, response.getStatusCode().value());
        verify(userService, times(1)).deleteUserById(userId);
    }

    @Test
    void testDeleteUserById_NotFound() {
        // Arrange
        String userId = "nonexistent";
        when(userService.deleteUserById(userId)).thenReturn(false);

        // Act
        ResponseEntity<Void> response = userController.deleteUserById(userId);

        // Assert
        assertEquals(404, response.getStatusCode().value());
        verify(userService, times(1)).deleteUserById(userId);
    }

    @Test
    void testUploadProfilePicture_Success() throws Exception {
        // Arrange
        String userId = "123";
        MultipartFile file = mock(MultipartFile.class);
        String imageUrl = "http://example.com/image.jpg";
        User user = new User();
        user.setProfilePictureUrl(imageUrl);
        when(imageService.uploadImage(file, System.getenv("BUCKET_NAME"), System.getenv("PROFILE_PIC_FOLDER")))
                .thenReturn(imageUrl);
        when(userService.updateProfilePicture(userId, imageUrl)).thenReturn(Optional.of(user));

        // Act
        ResponseEntity<User> response = userController.uploadProfilePicture(userId, file);

        // Assert
        assertEquals(200, response.getStatusCode().value());
        assertEquals(imageUrl, response.getBody().getProfilePictureUrl());
        verify(imageService, times(1)).uploadImage(file, System.getenv("BUCKET_NAME"), System.getenv("PROFILE_PIC_FOLDER"));
        verify(userService, times(1)).updateProfilePicture(userId, imageUrl);
    }

    @Test
    void testUploadProfilePicture_Failure() throws Exception {
        // Arrange
        String userId = "123";
        MultipartFile file = mock(MultipartFile.class);
        when(imageService.uploadImage(file, System.getenv("BUCKET_NAME"), System.getenv("PROFILE_PIC_FOLDER")))
                .thenThrow(new RuntimeException("Upload failed"));

        // Act
        ResponseEntity<User> response = userController.uploadProfilePicture(userId, file);

        // Assert
        assertEquals(500, response.getStatusCode().value());
        verify(imageService, times(1)).uploadImage(file, System.getenv("BUCKET_NAME"), System.getenv("PROFILE_PIC_FOLDER"));
        verify(userService, times(0)).updateProfilePicture(anyString(), anyString());
    }
}
