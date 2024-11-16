package com.project3.project3.service;

import com.project3.project3.model.Milestones;
import com.project3.project3.repository.MilestonesRepository;
import com.project3.project3.utility.BadgeObserver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MilestonesService {

    private final MilestonesRepository milestonesRepository;
    private final BadgeObserver badgeObserver;

    @Autowired
    public MilestonesService(MilestonesRepository milestonesRepository, BadgeObserver badgeObserver) {
        this.milestonesRepository = milestonesRepository;
        this.badgeObserver = badgeObserver;
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
            // Ensure updatedMilestones has the correct userId if creating a new one
            updatedMilestones.setUserId(userId);
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

    public Milestones incrementNationalParksVisited(String userId, String nationalPark) {
        Optional<Milestones> milestones = milestonesRepository.findByUserId(userId);
        if (milestones.isPresent()) {
            Milestones m = milestones.get();
            m.setNationalParksVisited(m.getNationalParksVisited() + 1);
            badgeObserver.checkAndAwardNationalParkBadge(userId, nationalPark);
            return milestonesRepository.save(m);
        }
        return null;
    }

    // Additional methods to increment distance and elevation gain based on new hikes
    public Milestones incrementDistance(String userId, double distance) {
        Optional<Milestones> milestones = milestonesRepository.findByUserId(userId);
        if (milestones.isPresent()) {
            Milestones m = milestones.get();
            m.setTotalDistance(m.getTotalDistance() + distance);
            Milestones updatedMilestones = milestonesRepository.save(m);
            badgeObserver.checkAndAwardDistanceBadge(updatedMilestones);
            return updatedMilestones;
        }
        return null;
    }

    public Milestones incrementElevationGain(String userId, double elevationGain) {
        Optional<Milestones> milestones = milestonesRepository.findByUserId(userId);
        if (milestones.isPresent()) {
            Milestones m = milestones.get();
            m.setTotalElevationGain(m.getTotalElevationGain() + elevationGain);
            Milestones updatedMilestones = milestonesRepository.save(m);
            badgeObserver.checkAndAwardElevationBadge(updatedMilestones);
            return updatedMilestones;
        }
        return null;
    }

    public void deleteMilestonesByUserId(String userId) {
        milestonesRepository.findByUserId(userId).ifPresent(milestonesRepository::delete);
    }
}
