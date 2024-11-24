package com.project3.project3.controller;

import com.project3.project3.model.FavoriteTrail;
import com.project3.project3.model.Trail;
import com.project3.project3.service.FavoriteTrailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/favorites")
public class FavoriteTrailController {

    @Autowired
    private FavoriteTrailService favoriteTrailService;

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Trail>> getUserFavoriteTrails(@PathVariable String userId) {
        List<Trail> trails = favoriteTrailService.getFavoritesByUserId(userId);
        return ResponseEntity.ok(trails);
    }

    @PostMapping
    public ResponseEntity<?> addFavoriteTrail(
            @RequestParam String userId,
            @RequestParam String trailId) {
        try {
            FavoriteTrail favoriteTrail = new FavoriteTrail(userId, trailId);
            FavoriteTrail addedTrail = favoriteTrailService.addFavoriteTrail(favoriteTrail);
            return ResponseEntity.ok(addedTrail);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeFavoriteTrail(@PathVariable String id) {
        boolean deleted = favoriteTrailService.removeFavoriteTrail(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}



