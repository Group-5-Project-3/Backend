package com.project3.project3.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
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


