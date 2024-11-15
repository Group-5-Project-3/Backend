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

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<CheckIn>> getUserCheckIns(@PathVariable String userId) {
        List<CheckIn> checkIns = checkInService.getCheckInsByUserId(userId);
        return ResponseEntity.ok(checkIns);
    }

    @GetMapping("/trail/{trailId}")
    public ResponseEntity<List<CheckIn>> getTrailCheckIns(@PathVariable String trailId) {
        List<CheckIn> checkIns = checkInService.getCheckInsByTrailId(trailId);
        return ResponseEntity.ok(checkIns);
    }

    @PostMapping
    public ResponseEntity<CheckIn> createCheckIn(@RequestBody CheckIn checkIn) {
        CheckIn createdCheckIn = checkInService.createCheckIn(checkIn);
        return ResponseEntity.ok(createdCheckIn);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCheckIn(@PathVariable String id) {
        boolean deleted = checkInService.deleteCheckIn(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}


