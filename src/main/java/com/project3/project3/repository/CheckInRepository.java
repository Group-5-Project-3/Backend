package com.project3.project3.repository;

import com.project3.project3.model.CheckIn;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface CheckInRepository extends MongoRepository<CheckIn, String> {
    List<CheckIn> findByTrailId(String trailId);
    List<CheckIn> findByUserId(String userId);
}

