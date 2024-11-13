package com.project3.project3.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;

@Document(collection = "favorite_trails")
public class FavoriteTrail {

    @Id
    private String id;

    private String userId;
    private String trailId;
    private LocalDateTime favoritedTimestamp;

    // Default constructor
    public FavoriteTrail() {}

    // Constructor
    public FavoriteTrail(String userId, String trailId, LocalDateTime favoritedTimestamp) {
        this.userId = userId;
        this.trailId = trailId;
        this.favoritedTimestamp = favoritedTimestamp;
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

    public String getTrailId() {
        return trailId;
    }

    public void setTrailId(String trailId) {
        this.trailId = trailId;
    }

    public LocalDateTime getFavoritedTimestamp() {
        return favoritedTimestamp;
    }

    public void setFavoritedTimestamp(LocalDateTime favoritedTimestamp) {
        this.favoritedTimestamp = favoritedTimestamp;
    }
}

