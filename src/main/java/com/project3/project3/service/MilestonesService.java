package com.project3.project3.service;

import com.project3.project3.model.Milestones;
import com.project3.project3.repository.MilestonesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MilestonesService {

    private final MilestonesRepository milestonesRepository;

    @Autowired
    public MilestonesService(MilestonesRepository milestonesRepository) {
        this.milestonesRepository = milestonesRepository;
    }

    public Milestones createMilestones(String userId) {
        Milestones milestones = new Milestones(userId, 0, 0.0, 0, 0.0, 0);
        return milestonesRepository.save(milestones);
    }

    // Fetches milestones by userId, creating a new record if it doesn't exist
    public Milestones getMilestonesByUserId(String userId) {
        Milestones milestones = milestonesRepository.findByUserId(userId);
        if (milestones == null) {
            milestones = createMilestones(userId);
        }
        return milestones;
    }

    public Milestones updateMilestones(String userId, Milestones updatedMilestones) {
        Milestones milestones = getMilestonesByUserId(userId);

        if (updatedMilestones.getTotalHikes() != null) {
            milestones.setTotalHikes(updatedMilestones.getTotalHikes());
        }
        if (updatedMilestones.getTotalDistance() != null) {
            milestones.setTotalDistance(updatedMilestones.getTotalDistance());
        }
        if (updatedMilestones.getUniqueTrails() != null) {
            milestones.setUniqueTrails(updatedMilestones.getUniqueTrails());
        }
        if (updatedMilestones.getTotalElevationGain() != null) {
            milestones.setTotalElevationGain(updatedMilestones.getTotalElevationGain());
        }
        if (updatedMilestones.getNationalParksVisited() != null) {
            milestones.setNationalParksVisited(updatedMilestones.getNationalParksVisited());
        }

        return milestonesRepository.save(milestones);
    }

    public Milestones incrementTotalHikes(String userId) {
        Milestones milestones = getMilestonesByUserId(userId);
        milestones.setTotalHikes(milestones.getTotalHikes() + 1);
        return milestonesRepository.save(milestones);
    }

    public Milestones incrementNationalParksVisited(String userId, String nationalPark) {
        Milestones milestones = getMilestonesByUserId(userId);
        milestones.setNationalParksVisited(milestones.getNationalParksVisited() + 1);
        return milestonesRepository.save(milestones);
    }

    // Additional methods to increment distance and elevation gain based on new hikes
    public Milestones incrementDistance(String userId, double distance) {
        Milestones milestones = getMilestonesByUserId(userId);
        milestones.setTotalDistance(milestones.getTotalDistance() + distance);
        return milestonesRepository.save(milestones);
    }

    public Milestones incrementElevationGain(String userId, double elevationGain) {
        Milestones milestones = getMilestonesByUserId(userId);
        milestones.setTotalElevationGain(milestones.getTotalElevationGain() + elevationGain);
        return milestonesRepository.save(milestones);
    }

    public void deleteMilestonesByUserId(String userId) {
        Milestones milestones = milestonesRepository.findByUserId(userId);
        if (milestones != null) {
            milestonesRepository.delete(milestones);
        }
    }
}
