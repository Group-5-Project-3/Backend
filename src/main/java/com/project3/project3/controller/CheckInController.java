package com.project3.project3.controller;

import com.project3.project3.model.CheckIn;
import com.project3.project3.service.CheckInService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/checkins")
public class CheckInController {

    @Autowired
    private CheckInService checkInService;

    // Get all check-ins for a specific user
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<CheckIn>> getUserCheckIns(@PathVariable String userId) {
        return ResponseEntity.ok(checkInService.getCheckInsByUserId(userId));
    }

    // Get all check-ins for a specific trail
    @GetMapping("/trail/{trailId}")
    public ResponseEntity<List<CheckIn>> getTrailCheckIns(@PathVariable String trailId) {
        return ResponseEntity.ok(checkInService.getCheckInsByTrailId(trailId));
    }

    // Create a new check-in
    @PostMapping
    public ResponseEntity<CheckIn> createCheckIn(@RequestBody CheckIn checkIn) {
        return ResponseEntity.ok(checkInService.createCheckIn(checkIn));
    }

    // Delete a check-in by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCheckIn(@PathVariable String id) {
        checkInService.deleteCheckIn(id);
        return ResponseEntity.noContent().build();
    }
}

