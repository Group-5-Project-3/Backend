package com.project3.project3.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.project3.project3.model.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {

    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
    List<User> findByRolesContaining(String role);
    List<User> findByFavoriteTrailIdsContaining(String trailId);
    List<User> findByBadgeIdsContaining(String badgeId);
    List<User> findByTrailImageIdsContaining(String trailImageId);
}

