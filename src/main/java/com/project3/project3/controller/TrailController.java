package com.project3.project3.controller;

import com.project3.project3.model.Trail;
import com.project3.project3.service.TrailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/trails")
public class TrailController {

    @Autowired
    private TrailService trailService;

    // Get all trails
    @GetMapping
    public ResponseEntity<List<Trail>> getAllTrails() {
        return ResponseEntity.ok(trailService.getAllTrails());
    }

    // Get a specific trail by ID
    @GetMapping("/{id}")
    public ResponseEntity<Trail> getTrailById(@PathVariable String id) {
        return trailService.getTrailById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Create a new trail
    @PostMapping
    public ResponseEntity<Trail> createTrail(@RequestBody Trail trail) {
        return ResponseEntity.ok(trailService.createTrail(trail));
    }

    // Update a trail
    @PutMapping("/{id}")
    public ResponseEntity<Trail> updateTrail(@PathVariable String id, @RequestBody Trail trail) {
        return ResponseEntity.ok(trailService.updateTrail(id, trail));
    }

    // Delete a trail by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTrail(@PathVariable String id) {
        trailService.deleteTrail(id);
        return ResponseEntity.noContent().build();
    }
}
