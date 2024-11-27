package com.project3.project3.repository;

import com.project3.project3.model.Hike;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HikeRepository extends MongoRepository<Hike, String> {
    List<Hike> findByUserId(String userId);
    List<Hike> findByTrailId(String trailId);
}

