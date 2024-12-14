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
import java.util.Optional;

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

    // update this to take out the chat gpt stuff here....its not being used here but in the trail images
    public Trail getTrailById(String id) {
        Trail trail = trailRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Trail not found for ID: " + id));
        return trail;
    }

    public TrailDTO getTrailByPlacesId(String placesId) {
        Trail trail = trailRepository.findByPlacesId(placesId);
        if (trail == null) {
            throw new IllegalArgumentException("Trail not found for Places ID: " + placesId);
        }
        Logger.info("Trail ID: {}", trail.getTrailId());
        List<TrailImage> images = trailImageService.getImagesByTrailId(trail.getTrailId());
        return mapToTrailDTO(trail, images);
    }

    public TrailDTO createTrail(Trail trail) {
        String placeId = trail.getPlacesId();
        if(!checkIfTrailExists(placeId)) {
            throw new IllegalArgumentException("Trail already exist.");
        }
        String prompt = String.format("Provide a detailed and engaging description for a trail or park named '%s'. Ensure the description is accurate to park of trail while highlight the park or trails features.", trail.getName());
        String generatedDescription = ChatGPTUtil.getChatGPTResponse(prompt);
        trail.setDescription(generatedDescription);
        Trail createdTrail = trailRepository.save(trail);
        List<TrailImage> images = trailImageService.getImagesByTrailId(createdTrail.getTrailId());
        return mapToTrailDTO(createdTrail, images);
    }

    public Trail updateTrailCoordinates(String id, String coordinates) {
        Trail trail = trailRepository.findByPlacesId(id);
        trail.setCoordinates(coordinates);
        return trailRepository.save(trail);
    }

    public boolean deleteTrail(String id) {
        if(trailRepository.existsById(id)) {
            trailRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    private TrailDTO mapToTrailDTO(Trail trail, List<TrailImage> images) {
        TrailDTO trailDTO = new TrailDTO();
        trailDTO.setTrailId(trail.getTrailId());
        trailDTO.setPlacesId(trail.getPlacesId());
        trailDTO.setName(trail.getName());
        trailDTO.setLocation(trail.getLocation());
        trailDTO.setDescription(trail.getDescription());
        trailDTO.setSentiments(trail.getSentiments());
        trailDTO.setImages(images);
        trailDTO.setAvgRating(trail.getAvgRating());
        trailDTO.setAvgDifficulty(trail.getAvgDifficulty());
        trailDTO.setCoordinates(trail.getCoordinates());
        return trailDTO;
    }

    private Boolean checkIfTrailExists (String placeId) {
        return trailRepository.findByPlacesId(placeId) != null;
    }
}
