package com.project3.project3.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "hikes")
public class Hike {

    @Id
    private String hikeId;
    private String userId;
    private String trailId;
    private String polyline;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Double distance;
    private Double elevationGain;

    public Hike() {}

    // Constructor with fields
    public Hike(String userId, String trailId, String polyline, LocalDateTime startTime, LocalDateTime endTime, Double distance, Double elevationGain) {
        this.userId = userId;
        this.trailId = trailId;
        this.polyline = polyline;
        this.startTime = startTime;
        this.endTime = endTime;
        this.distance = distance;
        this.elevationGain = elevationGain;
    }

    public String getHikeId() {
        return hikeId;
    }

    public void setHikeId(String hikeId) {
        this.hikeId = hikeId;
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

    public String getPolyline() {
        return polyline;
    }

    public void setPolyline(String polyline) {
        this.polyline = polyline;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

    public Double getElevationGain() {
        return elevationGain;
    }

    public void setElevationGain(Double elevationGain) {
        this.elevationGain = elevationGain;
    }
}

