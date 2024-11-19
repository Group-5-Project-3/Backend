package com.project3.project3.controller;

import com.project3.project3.model.Milestones;
import com.project3.project3.service.MilestonesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/milestones")
public class MilestonesController {

    private final MilestonesService milestonesService;

    @Autowired
    public MilestonesController(MilestonesService milestonesService) {
        this.milestonesService = milestonesService;
    }

    // Get milestones by user ID
    @GetMapping("/{userId}")
    public ResponseEntity<Milestones> getMilestonesByUserId(@PathVariable String userId) {
        Milestones milestones = milestonesService.getMilestonesByUserId(userId);
        return ResponseEntity.ok(milestones);
    }

    // Update milestones for a user
    @PutMapping("/{userId}")
    public ResponseEntity<Milestones> updateMilestones(
            @PathVariable String userId,
            @RequestBody Milestones updatedMilestones) {
        Milestones milestones = milestonesService.updateMilestones(userId, updatedMilestones);
        return ResponseEntity.ok(milestones);
    }

    // Delete milestones by user ID
    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteMilestonesByUserId(@PathVariable String userId) {
        milestonesService.deleteMilestonesByUserId(userId);
        return ResponseEntity.noContent().build();
    }
}
