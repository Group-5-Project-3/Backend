package com.project3.project3.service;

import com.project3.project3.model.Milestones;
import com.project3.project3.repository.MilestonesRepository;
import com.project3.project3.repository.UserBadgeRepository;
import com.project3.project3.utility.lists.NationalParksList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        Milestones milestones = Milestones.milestonesFactory(userId, 0, 0.0, 0, 0, 0.0, 0);
        return milestonesRepository.save(milestones);
    }


    public Milestones getMilestonesByUserId(String userId) {
        Milestones milestones = milestonesRepository.findByUserId(userId);
        return milestones;
    }

    public Milestones incrementTotalHikes(String userId) {
        Milestones milestones = getMilestonesByUserId(userId);
        milestones.setTotalHikes(milestones.getTotalHikes() + 1);
        return milestonesRepository.save(milestones);
    }

    public Milestones incrementNationalParksVisited(String userId, String parkName) {
        if (NationalParksList.isCaliforniaNationalPark(parkName)) {
            String badgeId = NationalParksList.getBadgeIdForPark(parkName);
            boolean hasVisitedPark = userBadgeRepository.findByUserIdAndBadgeId(userId, badgeId).isPresent();
            if (badgeId != null && !hasVisitedPark) {
                Milestones milestones = getMilestonesByUserId(userId);
                milestones.setNationalParksVisited(milestones.getNationalParksVisited() + 1);
                milestonesRepository.save(milestones);
                return milestones;
            }
        }
        return getMilestonesByUserId(userId);
    }

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

    public void deleteByUserId(String userId) {
        Milestones milestones = milestonesRepository.findByUserId(userId);
        if (milestones != null) {
            milestonesRepository.delete(milestones);
        }
    }

    public Milestones incrementTotalCheckIn(String userId) {
        Milestones milestones = getMilestonesByUserId(userId);
        if (milestones == null) {
            throw new IllegalArgumentException("Milestones not found for user ID: " + userId);
        }
        milestones.setTotalCheckIn(milestones.getTotalCheckIn() + 1);
        return milestonesRepository.save(milestones);
    }

}
