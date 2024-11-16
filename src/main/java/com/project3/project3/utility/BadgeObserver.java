package com.project3.project3.utility;

import com.project3.project3.model.Milestones;
import com.project3.project3.service.MilestonesService;
import com.project3.project3.service.UserBadgeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class BadgeObserver {

    private final MilestonesService milestonesService;
    private final UserBadgeService userBadgeService;
    private final NationalParksList nationalParksList;
    private final DistanceList distanceList;
    private final ElevationList elevationList;
    private final TotalHikeList totalHikeList;

    @Autowired
    public BadgeObserver(MilestonesService m, UserBadgeService u, NationalParksList np, DistanceList d, ElevationList e, TotalHikeList t) {
        this.milestonesService = m;
        this.userBadgeService = u;
        this.nationalParksList = np;
        this.distanceList = d;
        this.elevationList = e;
        this.totalHikeList = t;
    }

    @EventListener
    public void onHikeEvent(HikeEvent event) {
        String userId = event.getUserId();

        Milestones milestones = milestonesService.getMilestonesByUserId(userId);
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

        for (Double distance : distanceList.DISTANCE_MILESTONES.keySet()) {
            if (totalDistance >= distance) {
                String badgeId = distanceList.getBadgeIdForDistance(distance);
                awardBadgeIfNotEarned(userId, badgeId);
            }
        }
    }

    public void checkAndAwardElevationBadge(Milestones milestones) {
        String userId = milestones.getUserId();
        double totalElevationGain = milestones.getTotalElevationGain();

        for (Double elevation : elevationList.ELEVATION_MILESTONES.keySet()) {
            if (totalElevationGain >= elevation) {
                String badgeId = elevationList.getBadgeIdForElevation(elevation);
                awardBadgeIfNotEarned(userId, badgeId);
            }
        }
    }

    public void checkAndAwardTotalHikeBadge(Milestones milestones) {
        String userId = milestones.getUserId();
        int totalHikes = milestones.getTotalHikes();

        for (Integer hikeCount : totalHikeList.HIKE_MILESTONES.keySet()) {
            if (totalHikes >= hikeCount) {
                String badgeId = totalHikeList.getBadgeIdForHikes(hikeCount);
                awardBadgeIfNotEarned(userId, badgeId);
            }
        }
    }

    public void checkAndAwardNationalParkBadge(String userId, String park) {
        if(nationalParksList.isCaliforniaNationalPark(park)) {
            awardBadgeIfNotEarned(userId, nationalParksList.getBadgeIdForPark(park));
        }
    }

    private void awardBadgeIfNotEarned(String userId, String badgeId) {
        if (!userBadgeService.hasBadge(userId, badgeId)) {
            userBadgeService.awardBadgeToUser(userId, badgeId);
        }
    }
}
