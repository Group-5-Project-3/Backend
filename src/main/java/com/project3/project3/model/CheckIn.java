package com.project3.project3.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;

@Document(collection = "check_ins")
public class CheckIn {

    @Id
    private String checkInId;

    private String trailId;  // ID of the trail being checked into
    private String userId;   // ID of the user checking in

    private LocalDateTime timestamp;

    // Default constructor
    public CheckIn() {}

    // Constructor
    public CheckIn(String trailId, String userId, LocalDateTime timestamp) {
        this.trailId = trailId;
        this.userId = userId;
        this.timestamp = timestamp;
    }

    // Getters and Setters
    public String getCheckInId() {
        return checkInId;
    }

    public void setCheckInId(String checkInId) {
        this.checkInId = checkInId;
    }

    public String getTrailId() {
        return trailId;
    }

    public void setTrailId(String trailId) {
        this.trailId = trailId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}

