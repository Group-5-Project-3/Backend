package com.project3.project3.controller;

import com.project3.project3.model.FavoriteTrail;
import com.project3.project3.model.FavoriteTrailWithImagesDTO;
import com.project3.project3.model.Trail;
import com.project3.project3.service.FavoriteTrailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.tinylog.Logger;

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
    public ResponseEntity<?> addFavoriteTrail(@RequestParam String userId, @RequestParam String trailId) {
        try {
            FavoriteTrail addedTrail = favoriteTrailService.addFavoriteTrail(userId, trailId);
            return ResponseEntity.ok(addedTrail);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    @DeleteMapping
    public ResponseEntity<Void> removeFavoriteTrail(
            @RequestParam String userId,
            @RequestParam String trailId) {
        Logger.info("User ID: {}, Trail ID: {}", userId, trailId);
        boolean deleted = favoriteTrailService.removeFavoriteTrail(userId, trailId);
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{userId}/with-images")
    public ResponseEntity<List<FavoriteTrailWithImagesDTO>> getFavoriteTrailsWithImages(@PathVariable String userId) {
        try {
            List<FavoriteTrailWithImagesDTO> favoriteTrailsWithImages = favoriteTrailService.getFavoriteTrailsWithImages(userId);
            if (!favoriteTrailsWithImages.isEmpty()) {
                return ResponseEntity.ok(favoriteTrailsWithImages);
            } else {
                return ResponseEntity.noContent().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }
}



