package com.project3.project3.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;

@Document(collection = "check_ins")
public class CheckIn {

    @Id
    private String checkInId;

    private String trailId;
    private String name;
    private String userId;

    private LocalDateTime timestamp;

    // Default constructor
    public CheckIn() {}

    // Constructor
    public CheckIn(String trailId, String userId, String name, LocalDateTime timestamp) {
        this.trailId = trailId;
        this.userId = userId;
        this.name = name;
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
    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}

