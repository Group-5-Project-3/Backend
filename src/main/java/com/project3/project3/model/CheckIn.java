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

    public CheckIn(){
    }

    public CheckIn(String checkInId, String trailId, String name, String userId, LocalDateTime timestamp) {
        this.checkInId = checkInId;
        this.trailId = trailId;
        this.name = name;
        this.userId = userId;
        this.timestamp = timestamp;
    }

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

