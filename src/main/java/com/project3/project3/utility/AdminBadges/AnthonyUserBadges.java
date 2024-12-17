package com.project3.project3.utility.AdminBadges;

import com.project3.project3.model.UserBadge;
import com.project3.project3.repository.UserBadgeRepository;
import com.project3.project3.utility.lists.DistanceList;
import com.project3.project3.utility.lists.NationalParksList;
import com.project3.project3.utility.lists.TotalHikeList;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class AnthonyUserBadges {

    private final UserBadgeRepository userBadgeRepository;

    public AnthonyUserBadges(UserBadgeRepository userBadgeRepository) {
        this.userBadgeRepository = userBadgeRepository;
    }

    public List<UserBadge> awardRandomBadges(String userId) {
        List<UserBadge> awardedBadges = new ArrayList<>();
        Random random = new Random();

        List<Integer> hikeMilestones = new ArrayList<>(TotalHikeList.HIKE_MILESTONES.keySet());
        int randomMaxHikeBadges = random.nextInt(hikeMilestones.size()) + 1;
        for (int i = 0; i < randomMaxHikeBadges; i++) {
            int milestone = hikeMilestones.get(i);
            String badgeId = TotalHikeList.getBadgeIdForHikes(milestone);
            awardedBadges.add(createUserBadge(userId, badgeId));
        }

        List<Double> distanceMilestones = new ArrayList<>(DistanceList.DISTANCE_MILESTONES.keySet());
        int randomMaxDistanceBadges = random.nextInt(distanceMilestones.size()) + 1;
        for (int i = 0; i < randomMaxDistanceBadges; i++) {
            double milestone = distanceMilestones.get(i);
            String badgeId = DistanceList.getBadgeIdForDistance(milestone);
            awardedBadges.add(createUserBadge(userId, badgeId));
        }

        List<String> parkNames = new ArrayList<>(NationalParksList.CA_NATIONAL_PARKS);
        for (int i = 0; i < 3; i++) {
            int randomParkIndex = random.nextInt(parkNames.size());
            String randomParkName = parkNames.get(randomParkIndex);
            String badgeId = NationalParksList.getBadgeIdForPark(randomParkName);
            awardedBadges.add(createUserBadge(userId, badgeId));
        }

        userBadgeRepository.saveAll(awardedBadges);

        return awardedBadges;
    }

    private UserBadge createUserBadge(String userId, String badgeId) {
        UserBadge userBadge = UserBadge.userBadgeFactory(userId, badgeId, LocalDateTime.now());
        return userBadge;
    }
}


