package com.project3.project3.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "milestones")
public class Milestones {

    @Id
    private String milestonesId;
    private String userId;
    private Integer totalHikes;
    private Double totalDistance;
    private Integer totalCheckIn;
    private Integer uniqueTrails;
    private Double totalElevationGain;
    private Integer nationalParksVisited;

    public Milestones() {
    }

    public Milestones(String milestonesId, String userId, Integer totalHikes, Double totalDistance, Integer totalCheckIn, Integer uniqueTrails, Double totalElevationGain, Integer nationalParksVisited) {
        this.milestonesId = milestonesId;
        this.userId = userId;
        this.totalHikes = totalHikes;
        this.totalDistance = totalDistance;
        this.totalCheckIn = totalCheckIn;
        this.uniqueTrails = uniqueTrails;
        this.totalElevationGain = totalElevationGain;
        this.nationalParksVisited = nationalParksVisited;
    }

    public String getMilestonesId() {
        return milestonesId;
    }

    public void setMilestonesId(String milestonesId) {
        this.milestonesId = milestonesId;
    }

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

    public Integer getTotalCheckIn() {
        return totalCheckIn;
    }

    public void setTotalCheckIn(Integer totalCheckIn) {
        this.totalCheckIn = totalCheckIn;
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

    public static Milestones milestonesFactory(String userId, Integer totalHikes, Double totalDistance, Integer totalCheckIn, Integer uniqueTrails, Double totalElevationGain, Integer nationalParksVisited) {
        Milestones milestones = new Milestones();
        milestones.setUserId(userId);
        milestones.setTotalHikes(totalHikes);
        milestones.setTotalDistance(totalDistance);
        milestones.setTotalCheckIn(totalCheckIn);
        milestones.setUniqueTrails(uniqueTrails);
        milestones.setTotalElevationGain(totalElevationGain);
        milestones.setNationalParksVisited(nationalParksVisited);
        return milestones;
    }
}


