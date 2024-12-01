package com.project3.project3.service;

import com.project3.project3.model.Trail;
import com.project3.project3.model.TrailDTO;
import com.project3.project3.model.TrailImage;
import com.project3.project3.repository.TrailRepository;
import com.project3.project3.utility.ChatGPTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public Trail getTrailById(String id) {
        Trail trail = trailRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Trail not found for ID: " + id));
        if (trail.getDescription() == "New review") {
            String prompt = String.format("Provide a detailed and engaging description for a trail or park named '%s'. Highlight its beauty, key features, and why people would enjoy visiting.", trail.getName());
            String generatedDescription = ChatGPTUtil.getChatGPTResponse(prompt);
            trail.setDescription(generatedDescription);
            trailRepository.save(trail);
        }
        return trail;
    }

    public TrailDTO getTrailByPlacesId(String placesId) {
        Trail trail = trailRepository.findByPlacesId(placesId);
        if (trail == null) {
            throw new IllegalArgumentException("Trail not found for Places ID: " + placesId);
        }
        List<TrailImage> images = trailImageService.getImagesByTrailId(trail.getTrailId());
        return mapToTrailDTO(trail, images);
    }

    public TrailDTO createTrail(Trail trail) {
        Trail createdTrail = trailRepository.save(trail);
        List<TrailImage> images = trailImageService.getImagesByTrailId(createdTrail.getTrailId());
        return mapToTrailDTO(createdTrail, images);
    }

    public Trail updateTrail(String id, Trail trail) {
        trail.setTrailId(id);
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
        return trailDTO;
    }
}
