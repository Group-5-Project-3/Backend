package com.project3.project3.service;

import com.project3.project3.model.FavoriteTrail;
import com.project3.project3.model.Trail;
import com.project3.project3.repository.FavoriteTrailRepository;
import com.project3.project3.repository.TrailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;

@Service
public class FavoriteTrailService {

    private final FavoriteTrailRepository favoriteTrailRepository;
    private final TrailRepository trailRepository;

    @Autowired
    public FavoriteTrailService(FavoriteTrailRepository favoriteTrailRepository, TrailRepository trailRepository) {
        this.favoriteTrailRepository = favoriteTrailRepository;
        this.trailRepository = trailRepository;
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
}

