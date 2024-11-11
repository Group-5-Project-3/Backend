package com.project3.project3.controller;

import com.project3.project3.model.TrailImage;
import com.project3.project3.service.ImageService;
import com.project3.project3.service.TrailImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/trail-images")
public class TrailImageController {

    @Autowired
    private TrailImageService trailImageService;

    @Autowired
    private ImageService imageService;

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
    @PostMapping("/upload")
    public ResponseEntity<TrailImage> uploadTrailImage(
            @RequestParam("file") MultipartFile file,
            @RequestParam("trailId") String trailId,
            @RequestParam("userId") String userId) {
        try {
            String imageUrl = imageService.uploadImage(file, System.getenv("BUCKET_NAME"), System.getenv("TRAIL_PIC_FOLDER"));
            TrailImage savedTrailImage = trailImageService.saveTrailImage(imageUrl, trailId, userId);
            return ResponseEntity.ok(savedTrailImage);
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

    // Delete a trail image by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTrailImage(@PathVariable String id) {
        trailImageService.deleteTrailImage(id);
        return ResponseEntity.noContent().build();
    }
}

