package com.project3.project3.repository;

import com.project3.project3.model.Trail;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface TrailRepository extends MongoRepository<Trail, String> {
    Trail findByPlacesId(String placesId);
    Trail findByTrailId(String trailId);
}


