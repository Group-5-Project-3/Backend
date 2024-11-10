package com.project3.project3.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;

@Document(collection = "user_badges")
public class UserBadge {

    @Id
    private String id;

    private String userId;    // ID of the user who received the badge
    private String badgeId;   // ID of the badge awarded
    private LocalDateTime awardedTimestamp;

    // Default constructor
    public UserBadge() {}

    // Constructor
    public UserBadge(String userId, String badgeId, LocalDateTime awardedTimestamp) {
        this.userId = userId;
        this.badgeId = badgeId;
        this.awardedTimestamp = awardedTimestamp;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getBadgeId() {
        return badgeId;
    }

    public void setBadgeId(String badgeId) {
        this.badgeId = badgeId;
    }

    public LocalDateTime getAwardedTimestamp() {
        return awardedTimestamp;
    }

    public void setAwardedTimestamp(LocalDateTime awardedTimestamp) {
        this.awardedTimestamp = awardedTimestamp;
    }
}