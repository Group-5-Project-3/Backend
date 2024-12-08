package com.project3.project3.controller;

import com.project3.project3.model.TrailImage;
import com.project3.project3.service.ImageService;
import com.project3.project3.service.TrailImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/trail-images")
public class TrailImageController {

    @Autowired
    private TrailImageService trailImageService;

    @Autowired
    private ImageService imageService;

    @GetMapping("/trail/{trailId}")
    public ResponseEntity<List<TrailImage>> getImagesByTrailId(@PathVariable String trailId) {
        List<TrailImage> trailImages = trailImageService.getImagesByTrailId(trailId);
        return ResponseEntity.ok(trailImages);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<TrailImage>> getImagesByUserId(@PathVariable String userId) {
        List<TrailImage> userImages = trailImageService.getImagesByUserId(userId);
        return ResponseEntity.ok(userImages);
    }

    @PostMapping("/upload")
    public ResponseEntity<TrailImage> uploadTrailImage(
            @RequestParam("file") MultipartFile file,
            @RequestParam("trailId") String trailId,
            @RequestParam("userId") String userId,
            @RequestParam("description") String description) {
        try {
            String objectKey = imageService.uploadImage(file, System.getenv("BUCKET_NAME"), System.getenv("TRAIL_PIC_FOLDER"));
            TrailImage savedTrailImage = trailImageService.saveTrailImage(objectKey, trailId, userId, description);
            return ResponseEntity.ok(savedTrailImage);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTrailImage(@PathVariable String id) {
        boolean deleted = trailImageService.deleteTrailImage(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}


