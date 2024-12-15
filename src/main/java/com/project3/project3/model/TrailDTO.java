package com.project3.project3.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TrailDTO {

    private String trailId;
    private String placesId;
    private String name;
    private String location;
    private String description;
    private String sentiments;
    private Double avgDifficulty;
    private Double avgRating;
    private String coordinates;
    private List<TrailImage> images;

    public static TrailDTO trailDTOFactory(Trail trail, List<TrailImage> images) {
        TrailDTO trailDTO = new TrailDTO();
        trailDTO.setTrailId(trail.getTrailId());
        trailDTO.setPlacesId(trail.getPlacesId());
        trailDTO.setName(trail.getName());
        trailDTO.setLocation(trail.getLocation());
        trailDTO.setDescription(trail.getDescription());
        trailDTO.setSentiments(trail.getSentiments());
        trailDTO.setAvgDifficulty(trail.getAvgDifficulty());
        trailDTO.setAvgRating(trail.getAvgRating());
        trailDTO.setCoordinates(trail.getCoordinates());
        trailDTO.setImages(images);
        return trailDTO;
    }
}



