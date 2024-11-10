package com.project3.project3.repository;

import com.project3.project3.model.Trail;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TrailRepository extends MongoRepository<Trail, String> {
    // Define additional query methods if needed
}


