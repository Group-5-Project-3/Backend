package com.project3.project3.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.List;

@Document(collection = "trails")
public class Trail {

    @Id
    private String trailId;
    private String placesId;
    private String name;
    private String location;
    private String description;
    private String sentiments;
    private Double avgRating;
    private Double avgDifficulty;

    // Default constructor
    public Trail() {}

    // Constructor
    public Trail(String placesId, String name, String location, String description) {
        this.placesId = placesId;
        this.name = name;
        this.location = location;
        this.description = description;
    }

    // Getters and Setters
    public String getTrailId() {
        return trailId;
    }

    public void setTrailId(String trailId) {
        this.trailId = trailId;
    }

    public String getPlacesId() {
        return placesId;
    }

    public void setPlacesId(String placesId) {
        this.placesId = placesId;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSentiments() {
        return sentiments;
    }

    public void setSentiments(String sentiments) {
        this.sentiments = sentiments;
    }

    public Double getAvgRating() {
        return avgRating;
    }

    public void setAvgRating(Double avgRating) {
        this.avgRating = avgRating;
    }

    public Double getAvgDifficulty() {
        return avgDifficulty;
    }

    public void setAvgDifficulty(Double avgDifficulty) {
        this.avgDifficulty = avgDifficulty;
    }
}
