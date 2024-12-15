package com.project3.project3.service;

import com.project3.project3.model.Trail;
import com.project3.project3.model.TrailImage;
import com.project3.project3.repository.TrailImageRepository;
import com.project3.project3.repository.TrailRepository;
import com.project3.project3.utility.ChatGPTUtil;
import com.project3.project3.utility.FlickrUtil;
import com.project3.project3.utility.S3Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
        // Fetch the trail once and reuse it throughout the method
        Trail trail = trailRepository.findById(trailId)
                .orElseThrow(() -> new IllegalArgumentException("Trail not found for ID: " + trailId));

        List<TrailImage> trailImages = trailImageRepository.findByTrailId(trailId);
        List<TrailImage> updatedImages = new ArrayList<>();
        String bucketName = System.getenv("BUCKET_NAME");

        if (trailImages.isEmpty()) {
            // Validate and parse coordinates
            String coordinates = trail.getCoordinates();
            if (coordinates == null || !coordinates.contains(",")) {
                throw new IllegalArgumentException("Invalid coordinates for trail ID: " + trailId);
            }

            String[] coordinateParts = coordinates.split(",");
            double latitude = Double.parseDouble(coordinateParts[0]);
            double longitude = Double.parseDouble(coordinateParts[1]);

            // Fetch Flickr images and add them to the updated list
            List<String> flickrImages = FlickrUtil.fetchImagesByCoordinates(latitude, longitude);
            for (String imageUrl : flickrImages) {
                TrailImage flickrImage = new TrailImage();
                flickrImage.setTrailId(trailId);
                flickrImage.setImageObjectKey(imageUrl);
                flickrImage.setDescription("Flickr Image");
                updatedImages.add(flickrImage);
            }
        } else {
            // Generate presigned URLs for existing images
            for (TrailImage trailImage : trailImages) {
                String presignedUrl = s3Util.generatePresignedUrl(bucketName, trailImage.getImageObjectKey());
                trailImage.setImageObjectKey(presignedUrl);
                updatedImages.add(trailImage);
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
        trailImage.setImageObjectKey(objectKey);
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
        String objectKey = trailImage.getImageObjectKey();
        String presignedUrl = s3Util.generatePresignedUrl(bucketName, objectKey);
        trailImage.setImageObjectKey(presignedUrl);
    }

    public void deleteByUserId(String userId) {
        List<TrailImage> userImages = trailImageRepository.findByUserId(userId);
        if (!userImages.isEmpty()) {
            trailImageRepository.deleteAll(userImages);
        }
    }
}

