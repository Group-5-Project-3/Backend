package com.project3.project3.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

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

    public TrailDTO() {
    }

    public TrailDTO(String trailId, String placesId, String name, String location, String description, String sentiments, Double avgDifficulty, Double avgRating, String coordinates, List<TrailImage> images) {
        this.trailId = trailId;
        this.placesId = placesId;
        this.name = name;
        this.location = location;
        this.description = description;
        this.sentiments = sentiments;
        this.avgDifficulty = avgDifficulty;
        this.avgRating = avgRating;
        this.coordinates = coordinates;
        this.images = images;
    }

    public String getTrailId() {
        return trailId;
    }

    public void setTrailId(String trailId) {
        this.trailId = trailId;
    }

    public String getPlacesId() {
        return placesId;
    }

    public void setPlacesId(String placesId) {
        this.placesId = placesId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSentiments() {
        return sentiments;
    }

    public void setSentiments(String sentiments) {
        this.sentiments = sentiments;
    }

    public Double getAvgDifficulty() {
        return avgDifficulty;
    }

    public void setAvgDifficulty(Double avgDifficulty) {
        this.avgDifficulty = avgDifficulty;
    }

    public Double getAvgRating() {
        return avgRating;
    }

    public void setAvgRating(Double avgRating) {
        this.avgRating = avgRating;
    }

    public String getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(String coordinates) {
        this.coordinates = coordinates;
    }

    public List<TrailImage> getImages() {
        return images;
    }

    public void setImages(List<TrailImage> images) {
        this.images = images;
    }

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



