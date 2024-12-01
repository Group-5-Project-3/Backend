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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private S3Util s3Util;

    @Autowired
    private MilestonesService milestonesService;

    @Autowired
    private FavoriteTrailService favoriteTrailService;

    @Autowired
    private UserBadgeService userBadgeService;

    @Autowired
    private CheckInService checkInService;

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private HikeService hikeService;

    @Autowired
    private TrailImageService trailImageService;




    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username).orElse(null);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        for (String role : user.getRoles()) {
            authorities.add(new SimpleGrantedAuthority(role));
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities
        );
    }

    public UserDetails loadUserById(String userId) throws UsernameNotFoundException {
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with ID: " + userId);
        }
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        for (String role : user.getRoles()) {
            authorities.add(new SimpleGrantedAuthority(role));
        }
        return new org.springframework.security.core.userdetails.User(user.getId(), user.getPassword(), authorities
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

    public User getUserById(String id) {
        User user = userRepository.findById(id).orElse(null);
        if (user != null) {
            String bucketName = System.getenv("BUCKET_NAME");
            String presignedUrl = s3Util.generatePresignedUrl(bucketName, user.getProfilePictureUrl());
            user.setProfilePictureUrl(presignedUrl);
        }
        return user;
    }

    public User saveUser(User user) {
        String hashedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(hashedPassword);

        if (user.getRoles() == null || user.getRoles().isEmpty()) {
            user.setRoles(List.of("ROLE_USER"));
        }
        return userRepository.save(user);
    }

    public User updateUser(String id, User updatedUser) {
        User user = userRepository.findById(id).orElse(null);
        if (user == null) {
            return null;
        }

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
    }

    public User updateProfilePicture(String id, String profileImageUrl) {
        User user = userRepository.findById(id).orElse(null);
        if (user != null) {
            user.setProfilePictureUrl(profileImageUrl);
            return userRepository.save(user);
        }
        return null;
    }

    public boolean deleteUserById(String id) {
        if (!userRepository.existsById(id)) {
            return false;
        }
        hikeService.deleteByUserId(id);
        userBadgeService.deleteByUserId(id);
        reviewService.deleteByUserId(id);
        milestonesService.deleteByUserId(id);
        trailImageService.deleteByUserId(id);
        checkInService.deleteByUserId(id);
        favoriteTrailService.deleteByUserId(id);
        userRepository.deleteById(id);
        return true;
    }

    public User removeRole(String userId, String role) {
        User user = userRepository.findById(userId).orElse(null);
        if (user != null) {
            user.getRoles().remove(role);
            return userRepository.save(user);
        }
        return null;
    }

    public List<String> getUserRoles(String userId) {
        User user = userRepository.findById(userId).orElse(null);
        return user != null ? user.getRoles() : new ArrayList<>();
    }

    public User assignRole(String userId, String role) {
        User user = userRepository.findById(userId).orElse(null);
        if (user != null && !user.getRoles().contains(role)) {
            user.getRoles().add(role);
            return userRepository.save(user);
        }
        return null;
    }

    public User findOrCreateGoogleUser(User googleUser) {
        User existingUser = userRepository.findByEmail(googleUser.getEmail()).orElse(null);
        if (existingUser != null) {
            return existingUser;
        }
        googleUser.setRoles(List.of("ROLE_USER"));
        googleUser.setPassword("");
        return userRepository.save(googleUser);
    }

    public String getProfilePicture(String userId) {
        User user = userRepository.findById(userId).orElse(null);
        if (user != null && user.getProfilePictureUrl() != null) {
            String bucketName = System.getenv("BUCKET_NAME");
            return s3Util.generatePresignedUrl(bucketName, user.getProfilePictureUrl());
        }
        return null;
    }

    public boolean verifyPassword(String userId, String rawPassword) {
        User user = userRepository.findById(userId).orElse(null);
        if (user != null && user.getPassword() != null) {
            return passwordEncoder.matches(rawPassword, user.getPassword());
        }
        return false;
    }
}
