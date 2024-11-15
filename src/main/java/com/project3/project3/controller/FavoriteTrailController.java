package com.project3.project3.controller;

import com.project3.project3.model.FavoriteTrail;
import com.project3.project3.service.FavoriteTrailService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public ResponseEntity<List<FavoriteTrail>> getUserFavorites(@PathVariable String userId) {
        return ResponseEntity.ok(favoriteTrailService.getFavoritesByUserId(userId));
    }

    @PostMapping
    public ResponseEntity<FavoriteTrail> addFavoriteTrail(
            @RequestParam String userId,
            @RequestParam String trailId) {
        FavoriteTrail favoriteTrail = new FavoriteTrail(userId, trailId, LocalDateTime.now());
        return ResponseEntity.ok(favoriteTrailService.addFavoriteTrail(favoriteTrail));
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



