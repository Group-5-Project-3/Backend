package com.project3.project3.controller;

import com.project3.project3.model.User;
import com.project3.project3.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/admin")
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class AdminController {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User createdUser = userService.saveUser(user);
        if (createdUser != null) {
            return ResponseEntity.ok(createdUser);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable String id, @RequestBody User updatedUser) {
        Optional<User> user = userService.updateUser(id, updatedUser);
        if (user.isPresent()) {
            return ResponseEntity.ok(user.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}/{role}")
    public ResponseEntity<User> assignRoleToUser(@PathVariable String id, @PathVariable String role) {
        Optional<User> user = userService.assignRole(id, role);
        if (user.isPresent()) {
            return ResponseEntity.ok(user.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}/{role}")
    public ResponseEntity<User> removeRoleFromUser(@PathVariable String id, @PathVariable String role) {
        Optional<User> user = userService.removeRole(id, role);
        if (user.isPresent()) {
            return ResponseEntity.ok(user.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}/roles")
    public ResponseEntity<List<String>> getUserRoles(@PathVariable String id) {
        Optional<List<String>> roles = userService.getUserRoles(id).map(List::copyOf);
        if (roles.isPresent()) {
            return ResponseEntity.ok(roles.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
