package com.project3.project3.service;

import com.project3.project3.model.Milestones;
import com.project3.project3.repository.MilestonesRepository;
import com.project3.project3.repository.UserBadgeRepository;
import com.project3.project3.utility.NationalParksList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class MilestonesService {

    private final MilestonesRepository milestonesRepository;
    private final UserBadgeRepository userBadgeRepository;

    @Autowired
    public MilestonesService(MilestonesRepository milestonesRepository, UserBadgeRepository userBadgeRepository) {
        this.milestonesRepository = milestonesRepository;
        this.userBadgeRepository = userBadgeRepository;
    }

    public Milestones createMilestones(String userId) {
        Milestones milestones = new Milestones(userId, LocalDateTime.now(), 0, 0, 0, 0.0, 0, 0.0, 0);
        return milestonesRepository.save(milestones);
    }

    public Milestones updateMilestones(String userId, Milestones updatedMilestones) {
        Milestones milestones = milestonesRepository.findByUserId(userId);

        if (updatedMilestones.getTotalHikes() != null) {
            milestones.setTotalHikes(updatedMilestones.getTotalHikes());
        }
        if (updatedMilestones.getTotalReviews() != null) {
            milestones.setTotalReviews(updatedMilestones.getTotalReviews());
        }
        if (updatedMilestones.getTotalCheckIns() != null) {
            milestones.setTotalCheckIns(updatedMilestones.getTotalCheckIns());
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
        Milestones milestones = milestonesRepository.findByUserId(userId);
        milestones.setTotalHikes(milestones.getTotalHikes() + 1);
        return milestonesRepository.save(milestones);
    }

    public Milestones incrementTotalReviews(String userId) {
        Milestones milestones = milestonesRepository.findByUserId(userId);
        milestones.setTotalReviews(milestones.getTotalReviews() + 1);
        return milestonesRepository.save(milestones);
    }

    public Milestones decrementTotalReviews(String userId) {
        Milestones milestones = milestonesRepository.findByUserId(userId);
        milestones.setTotalReviews(milestones.getTotalReviews() - 1);
        return milestonesRepository.save(milestones);
    }

    public Milestones incrementTotalCheckIns(String userId) {
        Milestones milestones = milestonesRepository.findByUserId(userId);
        milestones.setTotalCheckIns(milestones.getTotalCheckIns() + 1);
        return milestonesRepository.save(milestones);
    }

    public Milestones decrementTotalCheckIns(String userId) {
        Milestones milestones = milestonesRepository.findByUserId(userId);
        milestones.setTotalCheckIns(milestones.getTotalReviews() - 1);
        return milestonesRepository.save(milestones);
    }

    public Milestones incrementNationalParksVisited(String userId, String parkName) {
        if (NationalParksList.isCaliforniaNationalPark(parkName)) {
            String badgeId = NationalParksList.getBadgeIdForPark(parkName);
            boolean hasVisitedPark = userBadgeRepository.findByUserIdAndBadgeId(userId, badgeId).isPresent();
            if (badgeId != null && !hasVisitedPark) {
                Milestones milestones = milestonesRepository.findByUserId(userId);
                milestones.setNationalParksVisited(milestones.getNationalParksVisited() + 1);
                milestonesRepository.save(milestones);
                return milestones;
            }
        }
        return milestonesRepository.findByUserId(userId);
    }

    public Milestones incrementDistance(String userId, double distance) {
        Milestones milestones = milestonesRepository.findByUserId(userId);
        milestones.setTotalDistance(milestones.getTotalDistance() + distance);
        return milestonesRepository.save(milestones);
    }

    public Milestones incrementElevationGain(String userId, double elevationGain) {
        Milestones milestones = milestonesRepository.findByUserId(userId);
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

