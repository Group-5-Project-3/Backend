package com.project3.project3.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.List;

@Document(collection = "trails")
public class Trail {

    @Id
    private String trailId;

    private String name;
    private String location;
    private String difficulty;
    private String description;

    private double rating;

    // Lists of related document IDs
    private List<String> reviewIds;      // List of review IDs for the trail
    private List<String> checkInIds;     // List of check-in IDs for the trail
    private List<String> imageIds;       // List of image IDs for the trail

    // Default constructor
    public Trail() {}

    // Constructor
    public Trail(String name, String location, String difficulty, String description, double rating) {
        this.name = name;
        this.location = location;
        this.difficulty = difficulty;
        this.description = description;
        this.rating = rating;
    }

    // Getters and Setters
    public String getTrailId() {
        return trailId;
    }

    public void setTrailId(String trailId) {
        this.trailId = trailId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
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

    public List<String> getImageIds() {
        return imageIds;
    }

    public void setImageIds(List<String> imageIds) {
        this.imageIds = imageIds;
    }
}
