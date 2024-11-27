package com.project3.project3.service;

import com.project3.project3.model.Trail;
import com.project3.project3.repository.TrailRepository;
import com.project3.project3.utility.ChatGPTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TrailService {

    private final TrailRepository trailRepository;

    @Autowired
    public TrailService(TrailRepository trailRepository) {
        this.trailRepository = trailRepository;
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

    public Trail getTrailByPlacesId(String placesId) {
        return trailRepository.findByPlacesId(placesId);
    }

    public Trail createTrail(Trail trail) {
        return trailRepository.save(trail);
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
}
