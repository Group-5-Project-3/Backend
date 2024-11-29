package com.project3.project3.service;

import com.project3.project3.model.*;
import com.project3.project3.repository.FavoriteTrailRepository;
import com.project3.project3.repository.TrailImageRepository;
import com.project3.project3.repository.TrailRepository;
import com.project3.project3.utility.DefaultImageUtil;
import com.project3.project3.utility.S3Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
                if(trailImages.isEmpty()) {
                    String randomDefaultImageKey = DefaultImageUtil.getRandomDefaultImage();
                    String presignedUrl = s3Util.generatePresignedUrl(bucketName, randomDefaultImageKey);
                    TrailImage defaultImage = new TrailImage();
                    defaultImage.setImageUrl(presignedUrl);
                    trailImages.add(defaultImage);
                } else {
                    for (TrailImage image : trailImages) {
                        String presignedUrl = s3Util.generatePresignedUrl(bucketName, image.getImageUrl());
                        image.setImageUrl(presignedUrl);
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

    public FavoriteTrail addFavoriteTrail(FavoriteTrail favoriteTrail) {
        if (favoriteTrailRepository.existsByUserIdAndTrailId(favoriteTrail.getUserId(), favoriteTrail.getTrailId())) {
            throw new IllegalArgumentException("This trail is already in the user's favorites.");
        }
        favoriteTrail.setFavoritedTimestamp(LocalDateTime.now());
        return favoriteTrailRepository.save(favoriteTrail);
    }

    public boolean removeFavoriteTrail(String id) {
        if (favoriteTrailRepository.existsById(id)) {
            favoriteTrailRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public void deleteByUserId(String userId) {
        List<FavoriteTrail> userFavoriteTrails = favoriteTrailRepository.findByUserId(userId);
        if (!userFavoriteTrails.isEmpty()) {
            favoriteTrailRepository.deleteAll(userFavoriteTrails);
        }
    }
}

