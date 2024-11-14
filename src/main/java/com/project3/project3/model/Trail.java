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

    private Double rating;

    // Default constructor
    public Trail() {}

    // Constructor
    public Trail(String name, String location, String difficulty, String description, Double rating) {
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

    public Double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

}
