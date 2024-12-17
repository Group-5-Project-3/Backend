package com.project3.project3.controller;

import com.project3.project3.model.Badge;
import com.project3.project3.model.BadgeType;
import com.project3.project3.service.BadgeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.tinylog.Logger;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/badges")
public class BadgeController {

    @Autowired
    private BadgeService badgeService;

    @GetMapping
    public ResponseEntity<List<Badge>> getAllBadges() {
        List<Badge> badges = badgeService.getAllBadges();
        for(int i = 0; i < badges.size(); i++) {
           String url = badges.get(i).getBadgeObjectKey();
           Logger.info("URL: {}", url);
        }
        return ResponseEntity.ok(badges);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Badge> getBadgeById(@PathVariable String id) {
        Optional<Badge> badge = badgeService.getBadgeById(id);
        if (badge.isPresent()) {
            return ResponseEntity.ok(badge.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/type/{badgeType}")
    public ResponseEntity<List<Badge>> getBadgesByType(@PathVariable BadgeType badgeType) {
        List<Badge> badges = badgeService.getBadgesByType(badgeType);
        return ResponseEntity.ok(badges);
    }

    @PostMapping
    public ResponseEntity<Badge> createBadge(@RequestBody Badge badge) {
        Badge createdBadge = badgeService.createBadge(badge);
        return ResponseEntity.ok(createdBadge);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBadge(@PathVariable String id) {
        boolean deleted = badgeService.deleteBadge(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}


