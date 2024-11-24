package com.project3.project3.repository;

import com.project3.project3.model.CheckIn;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;
import java.util.Optional;

public interface CheckInRepository extends MongoRepository<CheckIn, String> {
    List<CheckIn> findByTrailId(String trailId);
    List<CheckIn> findByUserId(String userId);
    List<CheckIn> findByUserIdAndTrailId(String userId, String trailId);
}

