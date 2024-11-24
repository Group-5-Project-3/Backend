package com.project3.project3.repository;

import com.project3.project3.model.Badge;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BadgeRepository extends MongoRepository<Badge, String> {
    Badge findByBadgeId(String badgeId);
}

