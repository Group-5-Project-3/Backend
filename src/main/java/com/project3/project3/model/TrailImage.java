package com.project3.project3.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "trail_images")
public class TrailImage {

    @Id
    private String id;
    private String trailId;
    private String imageObjectKey;
    private String userId;
    private String description;
    private Integer likes;

    public TrailImage() {
    }

    public TrailImage(String id, String trailId, String imageObjectKey, String userId, String description) {
        this.id = id;
        this.trailId = trailId;
        this.imageObjectKey = imageObjectKey;
        this.userId = userId;
        this.description = description;
    }

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

    public String getImageObjectKey() {
        return imageObjectKey;
    }

    public void setImageObjectKey(String imageObjectKey) {
        this.imageObjectKey = imageObjectKey;
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

    public Integer getLikes() {
        return likes;
    }

    public void setLikes(Integer likes) {
        this.likes = likes;
    }
}

