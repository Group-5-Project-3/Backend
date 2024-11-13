package com.project3.project3.controller;

import com.project3.project3.model.Hike;
import com.project3.project3.service.HikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/hikes")
public class HikeController {

    @Autowired
    private HikeService hikeService;

    // Start a new hike
    @PostMapping("/start")
    public ResponseEntity<Hike> startHike(@RequestBody Hike hike) {
        Hike newHike = hikeService.startHike(hike);
        return ResponseEntity.ok(newHike);
    }

    // Complete an existing hike by setting end time, distance, elevation gain, and polyline
    @PutMapping("/complete/{id}")
    public ResponseEntity<Hike> completeHike(
            @PathVariable String id,
            @RequestParam double distance,
            @RequestParam double elevationGain,
            @RequestParam String polyline) {
        Optional<Hike> completedHike = hikeService.completeHike(id, distance, elevationGain, polyline);
        return completedHike.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Get all hikes by user ID
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Hike>> getHikesByUserId(@PathVariable String userId) {
        List<Hike> hikes = hikeService.getHikesByUserId(userId);
        return ResponseEntity.ok(hikes);
    }

    // Get all hikes by trail ID
    @GetMapping("/trail/{trailId}")
    public ResponseEntity<List<Hike>> getHikesByTrailId(@PathVariable String trailId) {
        List<Hike> hikes = hikeService.getHikesByTrailId(trailId);
        return ResponseEntity.ok(hikes);
    }

    // Get a specific hike by ID
    @GetMapping("/{id}")
    public ResponseEntity<Hike> getHikeById(@PathVariable String id) {
        Optional<Hike> hike = hikeService.getHikeById(id);
        return hike.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Update an existing hike
    @PutMapping("/{id}")
    public ResponseEntity<Hike> updateHike(@PathVariable String id, @RequestBody Hike updatedHike) {
        Optional<Hike> hike = hikeService.updateHike(id, updatedHike);
        return hike.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Delete a hike by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHike(@PathVariable String id) {
        boolean deleted = hikeService.deleteHikeById(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}

