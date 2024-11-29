package com.project3.project3.controller;

import com.project3.project3.model.Badge;
import com.project3.project3.model.UserBadge;
import com.project3.project3.service.UserBadgeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/user-badges")
public class UserBadgeController {

    @Autowired
    private UserBadgeService userBadgeService;

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<UserBadge>> getBadgesByUserId(@PathVariable String userId) {
        List<UserBadge> badges = userBadgeService.getBadgesByUserId(userId);
        return ResponseEntity.ok(badges);
    }

    @GetMapping("/user/{userId}/badge/{badgeId}")
    public ResponseEntity<UserBadge> getUserBadge(@PathVariable String userId, @PathVariable String badgeId) {
        Optional<UserBadge> userBadge = userBadgeService.getUserBadge(userId, badgeId);
        if (userBadge.isPresent()) {
            return ResponseEntity.ok(userBadge.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<UserBadge> awardBadgeToUser(
            @RequestParam String userId,
            @RequestParam String badgeId) {
        UserBadge awardedBadge = userBadgeService.awardBadgeToUser(userId, badgeId);
        return ResponseEntity.ok(awardedBadge);
    }

    @DeleteMapping("/user/{userId}/badge/{badgeId}")
    public ResponseEntity<Void> removeUserBadge(@PathVariable String userId, @PathVariable String badgeId) {
        boolean removed = userBadgeService.removeBadge(badgeId);
        if (removed) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}


