package com.project3.project3.controller;

import com.project3.project3.model.Trail;
import com.project3.project3.service.TrailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/trails")
public class TrailController {

    @Autowired
    private TrailService trailService;

    @GetMapping
    public ResponseEntity<List<Trail>> getAllTrails() {
        List<Trail> trails = trailService.getAllTrails();
        return ResponseEntity.ok(trails);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Trail> getTrailById(@PathVariable String id) {
        Optional<Trail> trail = trailService.getTrailById(id);
        if (trail.isPresent()) {
            return ResponseEntity.ok(trail.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    @GetMapping("/places/{placesId}")
    public ResponseEntity<Trail> getTrailByPlacesId(@PathVariable String placesId) {
        Optional<Trail> trail = trailService.getTrailByPlacesId(placesId);
        if (trail.isPresent()) {
            return ResponseEntity.ok(trail.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Trail> createTrail(@RequestBody Trail trail) {
        Trail createdTrail = trailService.createTrail(trail);
        return ResponseEntity.ok(createdTrail);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Trail> updateTrail(@PathVariable String id, @RequestBody Trail trail) {
        Trail updatedTrail = trailService.updateTrail(id, trail);
        return ResponseEntity.ok(updatedTrail);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTrail(@PathVariable String id) {
        boolean deleted = trailService.deleteTrail(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}

