package com.project3.project3.repository;

import com.project3.project3.model.TrailImage;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface TrailImageRepository extends MongoRepository<TrailImage, String> {
    List<TrailImage> findByTrailId(String trailId);
    List<TrailImage> findByUserId(String userId);
}

