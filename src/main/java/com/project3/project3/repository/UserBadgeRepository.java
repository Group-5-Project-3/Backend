package com.project3.project3.repository;

import com.project3.project3.model.UserBadge;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface UserBadgeRepository extends MongoRepository<UserBadge, String> {
    List<UserBadge> findByUserId(String userId); // Retrieve badges awarded to a user
    List<UserBadge> findByBadgeId(String badgeId); // Retrieve users awarded a specific badge
}

