package com.project3.project3.service;

import com.project3.project3.model.FavoriteTrail;
import com.project3.project3.repository.FavoriteTrailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.time.LocalDateTime;

@Service
public class FavoriteTrailService {

    private final FavoriteTrailRepository favoriteTrailRepository;

    @Autowired
    public FavoriteTrailService(FavoriteTrailRepository favoriteTrailRepository) {
        this.favoriteTrailRepository = favoriteTrailRepository;
    }

    public List<FavoriteTrail> getFavoritesByUserId(String userId) {
        return favoriteTrailRepository.findByUserId(userId);
    }

    public FavoriteTrail addFavoriteTrail(FavoriteTrail favoriteTrail) {
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

