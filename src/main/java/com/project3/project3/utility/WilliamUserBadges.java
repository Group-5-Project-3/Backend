package com.project3.project3.utility;

import com.project3.project3.model.UserBadge;
import com.project3.project3.repository.UserBadgeRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class WilliamUserBadges {

    private final UserBadgeRepository userBadgeRepository;

    public WilliamUserBadges(UserBadgeRepository userBadgeRepository) {
        this.userBadgeRepository = userBadgeRepository;
    }

    public List<UserBadge> getAllBadges(String userId) {
        List<UserBadge> allBadges = new ArrayList<>();

        for (Integer milestone : TotalHikeList.HIKE_MILESTONES.keySet()) {
            String badgeId = TotalHikeList.getBadgeIdForHikes(milestone);
            allBadges.add(createUserBadge(userId, badgeId));
        }

        // Get all Distance badges
        for (Double milestone : DistanceList.DISTANCE_MILESTONES.keySet()) {
            String badgeId = DistanceList.getBadgeIdForDistance(milestone);
            allBadges.add(createUserBadge(userId, badgeId));
        }

        // Get all National Parks badges
        for (String parkName : NationalParksList.CA_NATIONAL_PARKS) {
            String badgeId = NationalParksList.getBadgeIdForPark(parkName);
            if (badgeId != null) {
                allBadges.add(createUserBadge(userId, badgeId));
            }
        }

        // Save all badges to the repository
        userBadgeRepository.saveAll(allBadges);

        return allBadges;
    }

    private UserBadge createUserBadge(String userId, String badgeId) {
        return new UserBadge(userId, badgeId, LocalDateTime.now());
    }
}


