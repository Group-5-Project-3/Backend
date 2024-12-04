package com.project3.project3.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "trail_images")
public class TrailImage {

    @Id
    private String id;

    private String trailId;
    private String imageUrl;
    private String userId;
    private String description;
    private Integer likes;

    // Default constructor
    public TrailImage() {}

    // Constructor
    public TrailImage(String trailId, String imageUrl, String userId, String description) {
        this.trailId = trailId;
        this.imageUrl = imageUrl;
        this.userId = userId;
        this.description = description;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTrailId() {
        return trailId;
    }

    public void setTrailId(String trailId) {
        this.trailId = trailId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

