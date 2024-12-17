package com.project3.project3.controller;

import com.project3.project3.model.Trail;
import com.project3.project3.model.TrailDTO;
import com.project3.project3.service.TrailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.tinylog.Logger;

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
        Trail trail = trailService.getTrailById(id);
        if (trail != null) {
            return ResponseEntity.ok(trail);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/places/{placesId}")
    public ResponseEntity<TrailDTO> getTrailByPlacesId(@PathVariable String placesId) {
        return ResponseEntity.ok(trailService.getTrailByPlacesId(placesId));
    }

    @PostMapping
    public ResponseEntity<TrailDTO> createTrail(@RequestBody Trail trail) {
        Logger.info("Entering trail controller. Creating trail for place id: {}", trail.getPlacesId());
        return ResponseEntity.ok(trailService.createTrail(trail));
    }

    @PutMapping("/{id}/coordinates/{latitude}/{longitude}")
    public ResponseEntity<Trail> updateTrailCoordinates(@PathVariable String id, @PathVariable Double latitude, @PathVariable Double longitude) {
        String coordinates = latitude + "," + longitude;
        Logger.info("Updating trail ID {} with coordinates: {}", id, coordinates);
        Trail updatedTrail = trailService.updateTrailCoordinates(id, coordinates);
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

