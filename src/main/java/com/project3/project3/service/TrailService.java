package com.project3.project3.service;

import com.project3.project3.model.Trail;
import com.project3.project3.model.TrailDTO;
import com.project3.project3.model.TrailImage;
import com.project3.project3.repository.TrailRepository;
import com.project3.project3.utility.ChatGPTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tinylog.Logger;

import java.util.List;

@Service
public class TrailService {

    private final TrailRepository trailRepository;
    private final TrailImageService trailImageService;

    @Autowired
    public TrailService(TrailRepository trailRepository, TrailImageService trailImageService) {
        this.trailRepository = trailRepository;
        this.trailImageService = trailImageService;
    }

    public List<Trail> getAllTrails() {
        return trailRepository.findAll();
    }

    public Trail getTrailById(String id) {
        return trailRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Trail not found for ID: " + id));
    }

    public TrailDTO getTrailByPlacesId(String placesId) {
        Trail trail = trailRepository.findByPlacesId(placesId);
        if (trail == null) {
            throw new IllegalArgumentException("Trail not found for Places ID: " + placesId);
        }
        Logger.info("Trail ID: {}", trail.getTrailId());
        List<TrailImage> images = trailImageService.getImagesByTrailId(trail.getTrailId());
        return TrailDTO.trailDTOFactory(trail, images);
    }

    public TrailDTO createTrail(Trail trail) {
        String placeId = trail.getPlacesId();
        Trail existingTrail = trailRepository.findByPlacesId(placeId);
        if (existingTrail != null) {
            List<TrailImage> existingTrailImages = trailImageService.getImagesByTrailId(existingTrail.getTrailId());
            return TrailDTO.trailDTOFactory(existingTrail, existingTrailImages);
        }
        String prompt = String.format("Provide a detailed and engaging description for a trail or park named '%s'. " + "Ensure the description is accurate to the park or trail while highlighting its features.", trail.getName() != null ? trail.getName() : "Unnamed Trail");
        String generatedDescription = ChatGPTUtil.getChatGPTResponse(prompt);
        trail.setDescription(generatedDescription);
        Trail createdTrail = trailRepository.save(trail);
        List<TrailImage> images = trailImageService.getImagesByTrailId(createdTrail.getTrailId());
        return TrailDTO.trailDTOFactory(createdTrail, images);
    }

    public Trail updateTrailCoordinates(String id, String coordinates) {
        Trail trail = trailRepository.findByPlacesId(id);
        if (trail == null) {
            throw new IllegalArgumentException("Trail not found for Places ID: " + id);
        }
        trail.setCoordinates(coordinates);
        return trailRepository.save(trail);
    }

    public boolean deleteTrail(String id) {
        if (trailRepository.existsById(id)) {
            trailRepository.deleteById(id);
            return true;
        }
        return false;
    }

    private Boolean checkIfTrailExists(String placeId) {
        return trailRepository.findByPlacesId(placeId) != null;
    }
}
