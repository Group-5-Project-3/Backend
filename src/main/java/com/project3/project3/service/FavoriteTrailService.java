package com.project3.project3.service;

import com.project3.project3.model.*;
import com.project3.project3.repository.FavoriteTrailRepository;
import com.project3.project3.repository.TrailImageRepository;
import com.project3.project3.repository.TrailRepository;
import com.project3.project3.utility.DefaultImageUtil;
import com.project3.project3.utility.FlickrUtil;
import com.project3.project3.utility.S3Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tinylog.Logger;

import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;

@Service
public class FavoriteTrailService {

    private final FavoriteTrailRepository favoriteTrailRepository;
    private final TrailRepository trailRepository;
    private final TrailImageRepository trailImageRepository;
    private final S3Util s3Util;

    @Autowired
    public FavoriteTrailService(FavoriteTrailRepository favoriteTrailRepository, TrailRepository trailRepository, TrailImageRepository trailImageRepository, S3Util s3Util) {
        this.favoriteTrailRepository = favoriteTrailRepository;
        this.trailRepository = trailRepository;
        this.trailImageRepository = trailImageRepository;
        this.s3Util = s3Util;
    }

    public List<FavoriteTrailWithImagesDTO> getFavoriteTrailsWithImages(String userId) {
        List<FavoriteTrail> favoriteTrails = favoriteTrailRepository.findByUserId(userId);
        List<FavoriteTrailWithImagesDTO> favoriteTrailWithImagesDTOs = new ArrayList<>();

        for (FavoriteTrail favoriteTrail : favoriteTrails) {
            Trail trail = trailRepository.findById(favoriteTrail.getTrailId()).orElse(null);
            if (trail != null) {
                String bucketName = System.getenv("BUCKET_NAME");
                List<TrailImage> trailImages = trailImageRepository.findByTrailId(trail.getTrailId());
                if (trailImages.isEmpty()) {
                    String[] coordinates = trail.getCoordinates().split(",");
                    double latitude = Double.parseDouble(coordinates[0]);
                    double longitude = Double.parseDouble(coordinates[1]);
                    List<String> flickrImages = FlickrUtil.fetchImagesByCoordinates(latitude, longitude);
                    trailImages = new ArrayList<>();
                    for (String imageUrl : flickrImages) {
                        TrailImage flickrImage = new TrailImage();
                        flickrImage.setTrailId(trail.getTrailId());
                        flickrImage.setImageObjectKey(imageUrl);
                        flickrImage.setDescription("Flickr Image");
                        trailImages.add(flickrImage);
                    }
                } else {
                    for (TrailImage image : trailImages) {
                        String presignedUrl = s3Util.generatePresignedUrl(bucketName, image.getImageObjectKey());
                        image.setImageObjectKey(presignedUrl);
                    }
                }
                favoriteTrailWithImagesDTOs.add(new FavoriteTrailWithImagesDTO(trail, trailImages));
            }
        }
        return favoriteTrailWithImagesDTOs;
    }

    public List<Trail> getFavoritesByUserId(String userId) {
        List<FavoriteTrail> favoriteTrails = favoriteTrailRepository.findByUserId(userId);
        List<Trail> trails = new ArrayList<>();
        for (FavoriteTrail favorite : favoriteTrails) {
            trailRepository.findById(favorite.getTrailId()).ifPresent(trails::add);
        }
        return trails;
    }

    public FavoriteTrail addFavoriteTrail(String userId, String trailId) {
        if (favoriteTrailRepository.existsByUserIdAndTrailId(userId, trailId)) {
            throw new IllegalArgumentException("This trail is already in the user's favorites.");
        }
        FavoriteTrail favoriteTrail = FavoriteTrail.favoriteTrailFactory(userId, trailId, LocalDateTime.now());
        return favoriteTrailRepository.save(favoriteTrail);
    }

    public boolean removeFavoriteTrail(String userId, String trailId) {
        if (favoriteTrailRepository.existsByUserIdAndTrailId(userId, trailId)) {
            favoriteTrailRepository.deleteByUserIdAndTrailId(userId, trailId);
            Logger.info("Successfully deleted favorite trail with User ID: {} and Trail ID: {}", userId, trailId);
            return true;
        }
        Logger.warn("No favorite trail found with User ID: {} and Trail ID: {}", userId, trailId);
        return false;
    }



    public void deleteByUserId(String userId) {
        List<FavoriteTrail> userFavoriteTrails = favoriteTrailRepository.findByUserId(userId);
        if (!userFavoriteTrails.isEmpty()) {
            favoriteTrailRepository.deleteAll(userFavoriteTrails);
        }
    }
}

