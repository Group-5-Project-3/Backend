package com.project3.project3.repository;

import com.project3.project3.model.FavoriteTrail;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface FavoriteTrailRepository extends MongoRepository<FavoriteTrail, String> {
    List<FavoriteTrail> findByUserId(String userId);
    List<FavoriteTrail> findByTrailId(String trailId);
}

