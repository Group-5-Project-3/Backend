package com.project3.project3.service;

import com.project3.project3.model.Trail;
import com.project3.project3.model.TrailImage;
import com.project3.project3.model.UserBadge;
import com.project3.project3.repository.TrailImageRepository;
import com.project3.project3.repository.TrailRepository;
import com.project3.project3.utility.ChatGPTUtil;
import com.project3.project3.utility.DefaultImageUtil;
import com.project3.project3.utility.S3Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tinylog.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TrailImageService {

    private final TrailImageRepository trailImageRepository;
    private final TrailRepository trailRepository;
    private final S3Util s3Util;

    @Autowired
    public TrailImageService(TrailImageRepository trailImageRepository, TrailRepository trailRepository,S3Util s3Util) {
        this.trailImageRepository = trailImageRepository;
        this.s3Util = s3Util;
        this.trailRepository = trailRepository;
    }

    public List<TrailImage> getImagesByTrailId(String trailId) {
        List<TrailImage> trailImages = trailImageRepository.findByTrailId(trailId);
        List<TrailImage> updatedImages = new ArrayList<>();
        String bucketName = System.getenv("BUCKET_NAME");
        if(trailImages.isEmpty()) {
            String randomDefaultImageKey = DefaultImageUtil.getRandomDefaultImage();
            String presignedUrl = s3Util.generatePresignedUrl(bucketName, randomDefaultImageKey);
            TrailImage defaultImage = new TrailImage();
            defaultImage.setTrailId(trailId);
            defaultImage.setImageUrl(presignedUrl);
            defaultImage.setDescription("Default Image");
            updatedImages.add(defaultImage);
        } else {
            for (TrailImage trailImage : trailImages) {
                String presignedUrl = s3Util.generatePresignedUrl(bucketName, trailImage.getImageUrl());
                trailImage.setImageUrl(presignedUrl);
                updatedImages.add(trailImage);
            }
        }

        Trail trail = trailRepository.findById(trailId).orElseThrow(() -> new IllegalArgumentException("Trail not found for ID: " + trailId));
        if (trail.getDescription().equals("New review")) {
            try {
                String prompt = String.format("Provide a detailed and engaging description for a trail or park named '%s'. Highlight its beauty, key features, and why people would enjoy visiting.", trail.getName());
                String generatedDescription = ChatGPTUtil.getChatGPTResponse(prompt);
                trail.setDescription(generatedDescription);
                trailRepository.save(trail);
            } catch (Exception e) {
                throw new RuntimeException("Failed to generate description using ChatGPT: " + e.getMessage(), e);
            }
        }
        return updatedImages;
    }

    public List<TrailImage> getImagesByUserId(String userId) {
        List<TrailImage> trailImages = trailImageRepository.findByUserId(userId);
        List<TrailImage> updatedImages = new ArrayList<>();

        for (TrailImage trailImage : trailImages) {
            setPresignedUrl(trailImage);
            updatedImages.add(trailImage);
        }

        return updatedImages;
    }

    public TrailImage saveTrailImage(String objectKey, String trailId, String userId, String description) {
        TrailImage trailImage = new TrailImage();
        trailImage.setImageUrl(objectKey);
        trailImage.setTrailId(trailId);
        trailImage.setUserId(userId);
        trailImage.setDescription(description);
        return trailImageRepository.save(trailImage);
    }

    public boolean deleteTrailImage(String id) {
        if (trailImageRepository.existsById(id)) {
            trailImageRepository.deleteById(id);
            return true;
        }
        return false;
    }

    private void setPresignedUrl(TrailImage trailImage) {
        String bucketName = System.getenv("BUCKET_NAME");
        String objectKey = trailImage.getImageUrl();
        String presignedUrl = s3Util.generatePresignedUrl(bucketName, objectKey);
        trailImage.setImageUrl(presignedUrl);
    }

    public void deleteByUserId(String userId) {
        List<TrailImage> userImages = trailImageRepository.findByUserId(userId);
        if (!userImages.isEmpty()) {
            trailImageRepository.deleteAll(userImages);
        }
    }
}

