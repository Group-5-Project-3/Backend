package com.project3.project3.repository;

import com.project3.project3.model.Milestones;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.Optional;

public interface MilestonesRepository extends MongoRepository<Milestones, String> {
    Milestones findByUserId(String userId);
}
