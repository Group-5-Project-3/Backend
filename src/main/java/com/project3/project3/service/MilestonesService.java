package com.project3.project3.service;

import com.project3.project3.model.Milestones;
import com.project3.project3.repository.MilestonesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

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

    public Optional<Milestones> getMilestonesByUserId(String userId) {
        return milestonesRepository.findByUserId(userId);
    }

    public Milestones updateMilestones(String userId, Milestones updatedMilestones) {
        Optional<Milestones> existingMilestones = milestonesRepository.findByUserId(userId);
        if (existingMilestones.isPresent()) {
            Milestones milestones = existingMilestones.get();

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
        } else {
            return milestonesRepository.save(updatedMilestones);
        }
    }

    public Milestones incrementTotalHikes(String userId) {
        Optional<Milestones> milestones = milestonesRepository.findByUserId(userId);
        if (milestones.isPresent()) {
            Milestones m = milestones.get();
            m.setTotalHikes(m.getTotalHikes() + 1);
            return milestonesRepository.save(m);
        }
        return null;
    }

    public Milestones incrementNationalParksVisited(String userId) {
        Optional<Milestones> milestones = milestonesRepository.findByUserId(userId);
        if (milestones.isPresent()) {
            Milestones m = milestones.get();
            m.setNationalParksVisited(m.getNationalParksVisited() + 1);
            return milestonesRepository.save(m);
        }
        return null;
    }

    public void deleteMilestonesByUserId(String userId) {
        milestonesRepository.findByUserId(userId).ifPresent(milestonesRepository::delete);
    }
}

