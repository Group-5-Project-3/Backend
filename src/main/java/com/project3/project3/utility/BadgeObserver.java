package com.project3.project3.utility;

import com.project3.project3.model.Milestones;
import com.project3.project3.repository.MilestonesRepository;
import com.project3.project3.service.MilestonesService;
import com.project3.project3.service.UserBadgeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class BadgeObserver {

    private final MilestonesService milestonesService;
    private final UserBadgeService userBadgeService;
    private final MilestonesRepository milestonesRepository;

    @Autowired
    public BadgeObserver(MilestonesService milestonesService, UserBadgeService userBadgeService, MilestonesRepository milestonesRepository) {
        this.milestonesService = milestonesService;
        this.userBadgeService = userBadgeService;
        this.milestonesRepository = milestonesRepository;
    }

    @EventListener
    public void onHikeEvent(HikeEvent event) {
        String userId = event.getUserId();

        Milestones milestones = milestonesRepository.findByUserId(userId);
        checkAndAwardDistanceBadge(milestones);
        checkAndAwardElevationBadge(milestones);
        checkAndAwardTotalHikeBadge(milestones);
    }

    @EventListener
    public void onCheckInEvent(CheckInEvent event) {
        String userId = event.getUserId();
        String parkName = event.getParkName();
        checkAndAwardNationalParkBadge(userId, parkName);
    }

    public void checkAndAwardDistanceBadge(Milestones milestones) {
        String userId = milestones.getUserId();
        double totalDistance = milestones.getTotalDistance();

        for (Double distance : DistanceList.DISTANCE_MILESTONES.keySet()) {
            if (totalDistance >= distance) {
                String badgeId = DistanceList.getBadgeIdForDistance(distance);
                awardBadgeIfNotEarned(userId, badgeId);
            }
        }
    }

    public void checkAndAwardElevationBadge(Milestones milestones) {
        String userId = milestones.getUserId();
        double totalElevationGain = milestones.getTotalElevationGain();

        for (Double elevation : ElevationList.ELEVATION_MILESTONES.keySet()) {
            if (totalElevationGain >= elevation) {
                String badgeId = ElevationList.getBadgeIdForElevation(elevation);
                awardBadgeIfNotEarned(userId, badgeId);
            }
        }
    }

    public void checkAndAwardTotalHikeBadge(Milestones milestones) {
        String userId = milestones.getUserId();
        int totalHikes = milestones.getTotalHikes();

        for (Integer hikeCount : TotalHikeList.HIKE_MILESTONES.keySet()) {
            if (totalHikes >= hikeCount) {
                String badgeId = TotalHikeList.getBadgeIdForHikes(hikeCount);
                awardBadgeIfNotEarned(userId, badgeId);
            }
        }
    }

    public void checkAndAwardNationalParkBadge(String userId, String park) {
        if(NationalParksList.isCaliforniaNationalPark(park)) {
            awardBadgeIfNotEarned(userId, NationalParksList.getBadgeIdForPark(park));
        }
    }

    private void awardBadgeIfNotEarned(String userId, String badgeId) {
        if (!userBadgeService.hasBadge(userId, badgeId)) {
            userBadgeService.awardBadgeToUser(userId, badgeId);
        }
    }
}
