package com.project3.project3.repository;

import com.project3.project3.model.UserBadge;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;
import java.util.Optional;

public interface UserBadgeRepository extends MongoRepository<UserBadge, String> {
    List<UserBadge> findByUserId(String userId);
    List<UserBadge> findByBadgeId(String badgeId);
    Optional<UserBadge> findByUserIdAndBadgeId(String userId, String badgeId);
}

