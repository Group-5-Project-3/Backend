package com.project3.project3.repository;

import com.project3.project3.model.Badge;
import com.project3.project3.model.BadgeType;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface BadgeRepository extends MongoRepository<Badge, String> {
    Badge findByBadgeId(String badgeId);
    List<Badge> findByType(BadgeType badgeType);
}

