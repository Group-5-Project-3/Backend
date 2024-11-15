package com.project3.project3.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "milestones")
public class Milestones {

    private String userId;
    private Integer totalHikes;
    private Double totalDistance;
    private Integer uniqueTrails;
    private Double totalElevationGain;
    private Integer nationalParksVisited;

    // Constructors
    public Milestones() {}

    public Milestones(String userId, Integer totalHikes, Double totalDistance, Integer uniqueTrails, Double totalElevationGain, Integer nationalParksVisited) {
        this.userId = userId;
        this.totalHikes = totalHikes;
        this.totalDistance = totalDistance;
        this.uniqueTrails = uniqueTrails;
        this.totalElevationGain = totalElevationGain;
        this.nationalParksVisited = nationalParksVisited;
    }

    // Getters and Setters

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Integer getTotalHikes() {
        return totalHikes;
    }

    public void setTotalHikes(Integer totalHikes) {
        this.totalHikes = totalHikes;
    }

    public Double getTotalDistance() {
        return totalDistance;
    }

    public void setTotalDistance(Double totalDistance) {
        this.totalDistance = totalDistance;
    }

    public Integer getUniqueTrails() {
        return uniqueTrails;
    }

    public void setUniqueTrails(Integer uniqueTrails) {
        this.uniqueTrails = uniqueTrails;
    }

    public Double getTotalElevationGain() {
        return totalElevationGain;
    }

    public void setTotalElevationGain(Double totalElevationGain) {
        this.totalElevationGain = totalElevationGain;
    }

    public Integer getNationalParksVisited() {
        return nationalParksVisited;
    }

    public void setNationalParksVisited(Integer nationalParksVisited) {
        this.nationalParksVisited = nationalParksVisited;
    }
}

