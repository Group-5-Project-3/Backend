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

    public TrailImage saveTrailImage(TrailImage trailImage) {
        return trailImageRepository.save(trailImage);
    }

    public Optional<TrailImage> getTrailImageById(String id) {
        return trailImageRepository.findById(id);
    }

    public Optional<TrailImage> updateTrailImage(String id, TrailImage updatedTrailImage) {
        return trailImageRepository.findById(id).map(existingImage -> {
            if (updatedTrailImage.getImageUrl() != null) {
                existingImage.setImageUrl(updatedTrailImage.getImageUrl());
            }
            if (updatedTrailImage.getTrailId() != null) {
                existingImage.setTrailId(updatedTrailImage.getTrailId());
            }
            if (updatedTrailImage.getUserId() != null) {
                existingImage.setUserId(updatedTrailImage.getUserId());
            }
            return trailImageRepository.save(existingImage);
        });
    }

    public void deleteTrailImage(String id) {
        trailImageRepository.deleteById(id);
    }
}


