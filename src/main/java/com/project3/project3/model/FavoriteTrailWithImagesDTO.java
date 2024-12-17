package com.project3.project3.model;

import java.util.List;

public class FavoriteTrailWithImagesDTO {

    private Trail trail;
    private List<TrailImage> trailImages;

    public FavoriteTrailWithImagesDTO() {
    }

    public FavoriteTrailWithImagesDTO(Trail trail, List<TrailImage> trailImages) {
        this.trail = trail;
        this.trailImages = trailImages;
    }

    public Trail getTrail() {
        return trail;
    }

    public void setTrail(Trail trail) {
        this.trail = trail;
    }

    public List<TrailImage> getTrailImages() {
        return trailImages;
    }

    public void setTrailImages(List<TrailImage> trailImages) {
        this.trailImages = trailImages;
    }
}

