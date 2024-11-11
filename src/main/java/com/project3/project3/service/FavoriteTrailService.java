package com.project3.project3.service;

import com.project3.project3.model.FavoriteTrail;
import com.project3.project3.repository.FavoriteTrailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
        return favoriteTrailRepository.save(favoriteTrail);
    }

    public void removeFavoriteTrail(String id) {
        favoriteTrailRepository.deleteById(id);
    }
}

