package com.project3.project3.utility;

import com.project3.project3.model.Milestones;
import com.project3.project3.service.MilestonesService;
import com.project3.project3.service.UserBadgeService;
import com.project3.project3.utility.events.CheckInEvent;
import com.project3.project3.utility.events.HikeEvent;
import com.project3.project3.utility.lists.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class BadgeObserver {

    private final MilestonesService milestonesService;
    private final UserBadgeService userBadgeService;

    @Autowired
    public BadgeObserver(MilestonesService milestonesService, UserBadgeService userBadgeService) {
        this.milestonesService = milestonesService;
        this.userBadgeService = userBadgeService;
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
        Milestones milestones = milestonesService.getMilestonesByUserId(userId);
        checkAndAwardCheckInBadge(milestones);
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

    public void checkAndAwardCheckInBadge(Milestones milestones) {
        String userId = milestones.getUserId();
        int totalCheckIns = milestones.getTotalCheckIn();

        for (Integer checkInCount : CheckInList.CHECKIN_MILESTONES.keySet()) {
            if (totalCheckIns >= checkInCount) {
                String badgeId = CheckInList.getBadgeIdForCheckIn(checkInCount);
                awardBadgeIfNotEarned(userId, badgeId);
            }
        }
    }

    private void awardBadgeIfNotEarned(String userId, String badgeId) {
        if (!userBadgeService.hasBadge(userId, badgeId)) {
            userBadgeService.awardBadgeToUser(userId, badgeId);
        }
    }
}
