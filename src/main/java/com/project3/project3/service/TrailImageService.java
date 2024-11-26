package com.project3.project3.service;

import com.project3.project3.model.TrailImage;
import com.project3.project3.repository.TrailImageRepository;
import com.project3.project3.utility.S3Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TrailImageService {

    private final TrailImageRepository trailImageRepository;
    private final S3Util s3Util;

    @Autowired
    public TrailImageService(TrailImageRepository trailImageRepository, S3Util s3Util) {
        this.trailImageRepository = trailImageRepository;
        this.s3Util = s3Util;
    }

    public List<TrailImage> getImagesByTrailId(String trailId) {
        List<TrailImage> trailImages = trailImageRepository.findByTrailId(trailId);
        List<TrailImage> updatedImages = new ArrayList<>();
        for (TrailImage trailImage : trailImages) {
            setPresignedUrl(trailImage);
            updatedImages.add(trailImage);
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
}

