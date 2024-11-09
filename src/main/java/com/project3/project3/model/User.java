package com.project3.project3.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;
import java.util.stream.Collectors;

@Document(collection = "users")
public class User implements UserDetails {

    @Id
    private String id;

    @Indexed(unique = true, sparse = true)
    private String username;

    private String password;

    @Indexed(unique = true, sparse = true)
    private String email;

    private String firstName;
    private String lastName;

    private String profilePictureUrl; // URL of the user's profile picture

    private List<String> roles = new ArrayList<>();

    // Lists to store IDs for related data
    private List<String> reviewIds = new ArrayList<>();       // List of review IDs
    private List<String> checkInIds = new ArrayList<>();      // List of check-in IDs
    private List<String> favoriteTrailIds = new ArrayList<>();// List of favorite trail IDs
    private List<String> badgeIds = new ArrayList<>();        // List of awarded badge IDs

    // Default constructor
    public User() {}

    // Constructor with role strings
    public User(String username, String password, String email, String firstName, String lastName, String profilePictureUrl, List<String> roles) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.profilePictureUrl = profilePictureUrl;
        this.roles = roles;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getProfilePictureUrl() {
        return profilePictureUrl;
    }

    public void setProfilePictureUrl(String profilePictureUrl) {
        this.profilePictureUrl = profilePictureUrl;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public List<String> getReviewIds() {
        return reviewIds;
    }

    public void setReviewIds(List<String> reviewIds) {
        this.reviewIds = reviewIds;
    }

    public List<String> getCheckInIds() {
        return checkInIds;
    }

    public void setCheckInIds(List<String> checkInIds) {
        this.checkInIds = checkInIds;
    }

    public List<String> getFavoriteTrailIds() {
        return favoriteTrailIds;
    }

    public void setFavoriteTrailIds(List<String> favoriteTrailIds) {
        this.favoriteTrailIds = favoriteTrailIds;
    }

    public List<String> getBadgeIds() {
        return badgeIds;
    }

    public void setBadgeIds(List<String> badgeIds) {
        this.badgeIds = badgeIds;
    }

    // Convert roles to GrantedAuthority objects for Spring Security
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}

