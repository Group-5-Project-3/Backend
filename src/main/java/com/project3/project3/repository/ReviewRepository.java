package com.project3.project3.repository;

import com.project3.project3.model.Review;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface ReviewRepository extends MongoRepository<Review, String> {
    List<Review> findByTrailId(String trailId);
    List<Review> findByUserId(String userId);
}

