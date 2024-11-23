package com.project3.project3.model;

import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "milestones")
public class Milestones {

    private String userId;
    private LocalDateTime createdDate;
    private Integer totalHikes;
    private Integer totalReviews;
    private Integer totalCheckIns;
    private Double totalDistance;
    private Integer uniqueTrails;
    private Double totalElevationGain;
    private Integer nationalParksVisited;

    // Constructors
    public Milestones() {}

    public Milestones(String userId, LocalDateTime createdDate, Integer totalHikes, Integer totalReviews, Integer totalCheckIns, Double totalDistance, Integer uniqueTrails, Double totalElevationGain, Integer nationalParksVisited) {
        this.userId = userId;
        this.createdDate = createdDate;
        this.totalHikes = totalHikes;
        this.totalReviews = totalReviews;
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

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public Integer getTotalHikes() {
        return totalHikes;
    }

    public void setTotalHikes(Integer totalHikes) {
        this.totalHikes = totalHikes;
    }

    public Integer getTotalReviews() {
        return totalReviews;
    }

    public void setTotalReviews(Integer totalReviews) {
        this.totalReviews = totalReviews;
    }
    public Integer getTotalCheckIns() {
        return totalCheckIns;
    }

    public void setTotalCheckIns(Integer totalCheckIns) {
        this.totalCheckIns = totalCheckIns;
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

