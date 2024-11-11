package com.project3.project3.controller;

import com.project3.project3.model.User;
import com.project3.project3.service.ImageService;
import com.project3.project3.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private ImageService imageService;

    // Find user ID by username
    @GetMapping("/username/{username}")
    public ResponseEntity<String> findUserIdByUsername(@PathVariable String username) {
        Optional<User> user = userService.findUserByUsername(username);
        return user.map(u -> ResponseEntity.ok(u.getId())).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Find user by ID
    @GetMapping("/{id}")
    public ResponseEntity<User> findUserById(@PathVariable String id) {
        Optional<User> user = userService.getUserById(id);
        return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Find user by email
    @GetMapping("/email/{email}")
    public ResponseEntity<User> findUserByEmail(@PathVariable String email) {
        Optional<User> user = userService.findUserByEmail(email);
        return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Get all users
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    // Create a new user
    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User createdUser = userService.saveUser(user);
        return ResponseEntity.ok(createdUser);
    }

    // Update a user by ID
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable String id, @RequestBody User updatedUser) {
        Optional<User> user = userService.updateUser(id, updatedUser);
        return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Delete a user by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserById(@PathVariable String id) {
        boolean deleted = userService.deleteUserById(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    // Upload a profile picture
    @PostMapping("/{id}/profile-picture")
    public ResponseEntity<User> uploadProfilePicture(
            @PathVariable String id,
            @RequestParam("file") MultipartFile file) {
        try {
            String profileImageUrl = imageService.uploadImage(file, System.getenv("BUCKET_NAME"), System.getenv("PROFILE_PIC_FOLDER"));
            Optional<User> updatedUser = userService.updateProfilePicture(id, profileImageUrl);
            return updatedUser.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }
}
