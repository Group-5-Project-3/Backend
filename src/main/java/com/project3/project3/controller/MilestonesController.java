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

    @GetMapping("/{userId}")
    public ResponseEntity<Milestones> getMilestonesByUserId(@PathVariable String userId) {
        Milestones milestones = milestonesService.getMilestonesByUserId(userId);
        return ResponseEntity.ok(milestones);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteMilestonesByUserId(@PathVariable String userId) {
        milestonesService.deleteByUserId(userId);
        return ResponseEntity.noContent().build();
    }
}
