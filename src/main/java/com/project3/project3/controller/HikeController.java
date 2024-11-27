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

    @PostMapping("/start")
    public ResponseEntity<Hike> startHike(@RequestBody Hike hike) {
        Hike newHike = hikeService.startHike(hike);
        return ResponseEntity.ok(newHike);
    }

    @PutMapping("/complete/{id}")
    public ResponseEntity<Hike> completeHike(@PathVariable String id, @RequestParam Double distance, @RequestParam Double elevationGain, @RequestParam List<List<Double>> coordinates) {
        Optional<Hike> completedHike = hikeService.completeHike(id, distance, elevationGain, coordinates);
        if (completedHike.isPresent()) {
            return ResponseEntity.ok(completedHike.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Hike>> getHikesByUserId(@PathVariable String userId) {
        List<Hike> hikes = hikeService.getHikesByUserId(userId);
        return ResponseEntity.ok(hikes);
    }

    @GetMapping("/trail/{trailId}")
    public ResponseEntity<List<Hike>> getHikesByTrailId(@PathVariable String trailId) {
        List<Hike> hikes = hikeService.getHikesByTrailId(trailId);
        return ResponseEntity.ok(hikes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Hike> getHikeById(@PathVariable String id) {
        Optional<Hike> hike = hikeService.getHikeById(id);
        if (hike.isPresent()) {
            return ResponseEntity.ok(hike.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Hike> updateHike(@PathVariable String id, @RequestBody Hike updatedHike) {
        Optional<Hike> hike = hikeService.updateHike(id, updatedHike);
        if (hike.isPresent()) {
            return ResponseEntity.ok(hike.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHike(@PathVariable String id) {
        boolean deleted = hikeService.deleteHikeById(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}


