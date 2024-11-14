package com.project3.project3.service;

import com.project3.project3.model.TrailImage;
import com.project3.project3.repository.TrailImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TrailImageService {

    private final TrailImageRepository trailImageRepository;

    @Autowired
    public TrailImageService(TrailImageRepository trailImageRepository) {
        this.trailImageRepository = trailImageRepository;
    }

    public List<TrailImage> getImagesByTrailId(String trailId) {
        return trailImageRepository.findByTrailId(trailId);
    }

    public List<TrailImage> getImagesByUserId(String userId) {
        return trailImageRepository.findByUserId(userId);
    }

    public TrailImage saveTrailImage(String imageUrl, String trailId, String userId, String description) {
        TrailImage trailImage = new TrailImage();
        trailImage.setImageUrl(imageUrl);
        trailImage.setTrailId(trailId);
        trailImage.setUserId(userId);
        trailImage.setDescription(description);
        return trailImageRepository.save(trailImage);
    }

    public Optional<TrailImage> getTrailImageById(String id) {
        return trailImageRepository.findById(id);
    }

    public boolean deleteTrailImage(String id) {
        if(trailImageRepository.existsById(id)) {
            trailImageRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }
}


