package com.project3.project3.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
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

}

