package com.project3.project3.service;

import com.project3.project3.model.User;
import com.project3.project3.repository.UserRepository;
import com.project3.project3.utility.S3Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private S3Util s3Util;

    // Required method for authentication with Spring Security
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));

        Collection<GrantedAuthority> authorities = user.getRoles().stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                authorities
        );
    }

    // Method for loading user details by userId
    public UserDetails loadUserById(String userId) throws UsernameNotFoundException {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with ID: " + userId));

        Collection<GrantedAuthority> authorities = user.getRoles().stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        return new org.springframework.security.core.userdetails.User(
                user.getId(),
                user.getPassword(),
                authorities
        );
    }

    public Optional<User> findUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public Optional<User> findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(String id) {
        Optional<User> optionalUser = userRepository.findById(id);

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            String bucketName = System.getenv("BUCKET_NAME");
            String presignedUrl = s3Util.generatePresignedUrl(bucketName, user.getProfilePictureUrl());
            user.setProfilePictureUrl(presignedUrl);
        }
        return optionalUser;
    }



    public User saveUser(User user) {
        String hashedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(hashedPassword);

        if (user.getRoles() == null || user.getRoles().isEmpty()) {
            user.setRoles(List.of("ROLE_USER"));
        }
        return userRepository.save(user);
    }

    public Optional<User> updateUser(String id, User updatedUser) {
        return userRepository.findById(id).map(user -> {
            if (updatedUser.getPassword() != null) {
                String hashedPassword = passwordEncoder.encode(updatedUser.getPassword());
                user.setPassword(hashedPassword);
            }
            if (updatedUser.getUsername() != null) {
                user.setUsername(updatedUser.getUsername());
            }
            if (updatedUser.getEmail() != null) {
                user.setEmail(updatedUser.getEmail());
            }
            if (updatedUser.getFirstName() != null) {
                user.setFirstName(updatedUser.getFirstName());
            }
            if (updatedUser.getLastName() != null) {
                user.setLastName(updatedUser.getLastName());
            }
            if (updatedUser.getProfilePictureUrl() != null) {
                user.setProfilePictureUrl(updatedUser.getProfilePictureUrl());
            }
            if (updatedUser.getRoles() != null) {
                user.setRoles(updatedUser.getRoles());
            }
            return userRepository.save(user);
        });
    }

    public Optional<User> updateProfilePicture(String id, String profileImageUrl) {
        return userRepository.findById(id).map(user -> {
            user.setProfilePictureUrl(profileImageUrl);
            return userRepository.save(user);
        });
    }

    public boolean deleteUserById(String id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public Optional<User> removeRole(String userId, String role) {
        return userRepository.findById(userId).map(user -> {
            user.getRoles().remove(role);
            return userRepository.save(user);
        });
    }

    public Optional<List<String>> getUserRoles(String userId) {
        return userRepository.findById(userId).map(User::getRoles);
    }

    public Optional<User> assignRole(String userId, String role) {
        return userRepository.findById(userId).map(user -> {
            if (!user.getRoles().contains(role)) {
                user.getRoles().add(role);
            }
            return userRepository.save(user);
        });
    }
    public User findOrCreateGoogleUser(User googleUser) {
        Optional<User> existingUser = userRepository.findByEmail(googleUser.getEmail());
        if (existingUser.isPresent()) {
            return existingUser.get();
        }
        googleUser.setRoles(Collections.singletonList("ROLE_USER"));
        googleUser.setPassword("");
        return userRepository.save(googleUser);
    }

}
