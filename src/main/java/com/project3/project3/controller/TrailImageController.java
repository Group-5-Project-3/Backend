package com.project3.project3.controller;

import com.project3.project3.model.TrailImage;
import com.project3.project3.service.TrailImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/trail-images")
public class TrailImageController {

    @Autowired
    private TrailImageService trailImageService;

    // Get all images for a specific trail
    @GetMapping("/trail/{trailId}")
    public ResponseEntity<List<TrailImage>> getImagesByTrailId(@PathVariable String trailId) {
        return ResponseEntity.ok(trailImageService.getImagesByTrailId(trailId));
    }

    // Get all images uploaded by a specific user
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<TrailImage>> getImagesByUserId(@PathVariable String userId) {
        return ResponseEntity.ok(trailImageService.getImagesByUserId(userId));
    }

    // Upload a new trail image
    @PostMapping
    public ResponseEntity<TrailImage> uploadTrailImage(@RequestBody TrailImage trailImage) {
        return ResponseEntity.ok(trailImageService.saveTrailImage(trailImage));
    }

    // Delete a trail image by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTrailImage(@PathVariable String id) {
        trailImageService.deleteTrailImage(id);
        return ResponseEntity.noContent().build();
    }
}

