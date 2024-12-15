package com.project3.project3.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
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
    private String coordinates;

}

