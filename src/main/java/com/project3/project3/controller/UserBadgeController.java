package com.project3.project3.controller;

import com.project3.project3.model.UserBadge;
import com.project3.project3.service.UserBadgeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/user-badges")
public class UserBadgeController {

    @Autowired
    private UserBadgeService userBadgeService;

    // Get all badges awarded to a specific user
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<UserBadge>> getUserBadges(@PathVariable String userId) {
        return ResponseEntity.ok(userBadgeService.getUserBadgesByUserId(userId));
    }

    // Get specific user badge by userId and badgeId
    @GetMapping("/user/{userId}/badge/{badgeId}")
    public ResponseEntity<UserBadge> getUserBadge(@PathVariable String userId, @PathVariable String badgeId) {
        Optional<UserBadge> userBadge = userBadgeService.getUserBadge(userId, badgeId);
        return userBadge.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Award a new badge to a user
    @PostMapping
    public ResponseEntity<UserBadge> awardBadgeToUser(
            @RequestParam String userId,
            @RequestParam String badgeId) {

        // Create a UserBadge instance and set the award date to the current time
        UserBadge userBadge = new UserBadge(userId, badgeId, LocalDateTime.now());

        UserBadge awardedBadge = userBadgeService.awardBadgeToUser(userBadge);
        return ResponseEntity.ok(awardedBadge);
    }

    // Remove a badge from a user by userId and badgeId
    @DeleteMapping("/user/{userId}/badge/{badgeId}")
    public ResponseEntity<Void> removeUserBadge(@PathVariable String userId, @PathVariable String badgeId) {
        boolean removed = userBadgeService.removeBadge(badgeId);
        return removed ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}

