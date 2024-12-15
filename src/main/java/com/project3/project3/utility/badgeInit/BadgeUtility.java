package com.project3.project3.utility.badgeInit;

import com.project3.project3.model.Badge;
import com.project3.project3.model.BadgeType;
import com.project3.project3.repository.BadgeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class BadgeUtility {

    private final BadgeRepository badgeRepository;

    @Autowired
    public BadgeUtility(BadgeRepository badgeRepository) {
        this.badgeRepository = badgeRepository;
    }

    // Method to create and save multiple National Parks badges
    public void createNationalParksBadges() {
        List<Badge> badges = Arrays.asList(
                Badge.badgeFactory("Yosemite National Park Badge", "Visit Yosemite National Park", BadgeType.NATIONAL_PARKS, "badge-pictures/Yosmite.jpeg"),
                Badge.badgeFactory("Sequoia National Park Badge", "Visit Sequoia National Park", BadgeType.NATIONAL_PARKS, "badge-pictures/Sequoia.jpeg"),
                Badge.badgeFactory("Redwood National Park Badge", "Visit Redwood National Park", BadgeType.NATIONAL_PARKS, "badge-pictures/Redwood.jpeg"),
                Badge.badgeFactory("Pinnacles National Park Badge", "Visit Pinnacles National Park", BadgeType.NATIONAL_PARKS, "badge-pictures/Pinnacles.jpeg"),
                Badge.badgeFactory("Lassen National Park Badge", "Visit Lassen Volcanic National Park", BadgeType.NATIONAL_PARKS, "badge-pictures/Lasso.jpeg"),
                Badge.badgeFactory("Joshua Tree National Park Badge", "Visit Joshua Tree National Park", BadgeType.NATIONAL_PARKS, "badge-pictures/JoshuaTree.jpeg"),
                Badge.badgeFactory("Death Valley National Park Badge", "Visit Death Valley National Park", BadgeType.NATIONAL_PARKS, "badge-pictures/DeathValley.jpeg"),
                Badge.badgeFactory("Channel Islands National Park Badge", "Visit Channel Islands National Park", BadgeType.NATIONAL_PARKS, "badge-pictures/ChannelIslands.jpeg"),
                Badge.badgeFactory("Kings Canyon National Park Badge", "Visit Kings Canyon Nation Park", BadgeType.NATIONAL_PARKS, "badge-pictures/KingsCanyon.jpeg")
        );
        badgeRepository.saveAll(badges);
    }

    // Method to create and save multiple Distance badges
    public void createDistanceBadges() {
        List<Badge> badges = Arrays.asList(
                Badge.badgeFactory("Trail Starter", "Hike 1 kilometer", BadgeType.DISTANCE, "badge-pictures/1km.jpeg"),
                Badge.badgeFactory("Path Pacer", "Hike 5 kilometer", BadgeType.DISTANCE, "badge-pictures/5km.jpeg"),
                Badge.badgeFactory("Wayfarer", "Hike 10 kilometer", BadgeType.DISTANCE, "badge-pictures/10km.jpeg"),
                Badge.badgeFactory("Trail Tracker", "Hike 25 kilometer", BadgeType.DISTANCE, "badge-pictures/25km.jpeg"),
                Badge.badgeFactory("Journey Voyager", "Hike 50 kilometer", BadgeType.DISTANCE, "badge-pictures/50km.jpeg"),
                Badge.badgeFactory("Distance Drifter", "Hike 75 kilometer", BadgeType.DISTANCE, "badge-pictures/75km.jpeg"),
                Badge.badgeFactory("Long Hauler", "Hike 100 kilometer", BadgeType.DISTANCE, "badge-pictures/100km.jpeg"),
                Badge.badgeFactory("Explorer", "Hike 150 kilometer", BadgeType.DISTANCE, "badge-pictures/150km.jpeg"),
                Badge.badgeFactory("Endurance Hiker", "Hike 200 kilometer", BadgeType.DISTANCE, "badge-pictures/200km.jpeg"),
                Badge.badgeFactory("Path Pioneer", "Hike 300 kilometer", BadgeType.DISTANCE, "badge-pictures/300km.jpeg"),
                Badge.badgeFactory("Trail Legend", "Hike 500 kilometer", BadgeType.DISTANCE, "badge-pictures/500km.jpeg"),
                Badge.badgeFactory("Trail Blazer", "Hike 1000 kilometer", BadgeType.DISTANCE, "badge-pictures/1000km.jpeg")
        );
        badgeRepository.saveAll(badges);
    }

    // Method to create and save multiple Elevation badges
    public void createElevationBadges() {
        List<Badge> badges = Arrays.asList(
                Badge.badgeFactory("Horizon Hiker", "Reach 100m elevation", BadgeType.ELEVATION, "badge-pictures/100m.jpeg"),
                Badge.badgeFactory("Base Trekker", "Reach 500m elevation", BadgeType.ELEVATION, "badge-pictures/500m.jpeg"),
                Badge.badgeFactory("Hill Climber", "Reach 1000m elevation", BadgeType.ELEVATION, "badge-pictures/1000km.jpeg"),
                Badge.badgeFactory("Altitude Adventurer", "Reach 2000m elevation", BadgeType.ELEVATION, "badge-pictures/2000m.jpeg"),
                Badge.badgeFactory("Sky Seeker", "Reach 5000m elevation", BadgeType.ELEVATION, "badge-pictures/5000m.jpeg"),
                Badge.badgeFactory("Cloud Wanderer", "Reach 10000m elevation", BadgeType.ELEVATION, "badge-pictures/10000m.jpeg"),
                Badge.badgeFactory("Summit Explorer", "Reach 15000m elevation", BadgeType.ELEVATION, "badge-pictures/15000m.jpeg"),
                Badge.badgeFactory("Highlander", "Reach 20000m elevation", BadgeType.ELEVATION, "badge-pictures/20000m.jpeg"),
                Badge.badgeFactory("Mountain Conqueror", "Reach 25000m elevation", BadgeType.ELEVATION, "badge-pictures/25000m.jpeg"),
                Badge.badgeFactory("Apex Ascender", "Reach 50000m elevation", BadgeType.ELEVATION, "badge-pictures/50000m.jpeg"),
                Badge.badgeFactory("Mountain Master", "Reach 75000m elevation", BadgeType.ELEVATION, "badge-pictures/75000m.jpeg"),
                Badge.badgeFactory("Elevation Elite", "Reach 100000m elevation", BadgeType.ELEVATION, "badge-pictures/100000m.jpeg")
        );
        badgeRepository.saveAll(badges);
    }

    // Method to create and save multiple Total Hikes badges
    public void createTotalHikesBadges() {
        List<Badge> badges = Arrays.asList(
                Badge.badgeFactory("First Hike Completed", "Complete 1 Hike", BadgeType.TOTAL_HIKES, "badge-pictures/1hike.jpeg"),
                Badge.badgeFactory("10 Hikes Completed", "Complete 10 hikes", BadgeType.TOTAL_HIKES, "badge-pictures/10hike.jpeg"),
                Badge.badgeFactory("25 Hikes Completed", "Complete 25 hikes", BadgeType.TOTAL_HIKES, "badge-pictures/25hike.jpeg"),
                Badge.badgeFactory("50 Hikes Completed", "Complete 50 Hikes", BadgeType.TOTAL_HIKES, "badge-pictures/50hike.jpeg"),
                Badge.badgeFactory("75 Hikes Completed", "Complete 75 Hikes", BadgeType.TOTAL_HIKES, "badge-pictures/75hike.jpeg"),
                Badge.badgeFactory("100 Hikes Completed", "Complete 100 Hikes", BadgeType.TOTAL_HIKES, "badge-pictures/100hike.jpeg"),
                Badge.badgeFactory("150 Hikes Completed", "Complete 150 Hikes", BadgeType.TOTAL_HIKES, "badge-pictures/150hike.jpeg"),
                Badge.badgeFactory("200 Hikes Completed", "Complete 200 Hikes", BadgeType.TOTAL_HIKES, "badge-pictures/200hike.jpeg"),
                Badge.badgeFactory("300 Hikes Completed", "Complete 300 Hikes", BadgeType.TOTAL_HIKES, "badge-pictures/300hike.jpeg"),
                Badge.badgeFactory("750 Hikes Completed", "Complete 750 Hikes", BadgeType.TOTAL_HIKES, "badge-pictures/750hike.jpeg"),
                Badge.badgeFactory("1000 Hikes Completed", "Complete 1000 Hikes", BadgeType.TOTAL_HIKES, "badge-pictures/1000hike.jpeg")
        );
        badgeRepository.saveAll(badges);
    }

    // Method to create and save multiple Check-in badges
    public void createCheckInBadges() {
        List<Badge> badges = Arrays.asList(
                Badge.badgeFactory("First Check-in Badge", "Complete your first check-in", BadgeType.CHECKIN, "badge-pictures/1check.webp"),
                Badge.badgeFactory("5 Check-ins Badge", "Complete 5 check-ins", BadgeType.CHECKIN, "badge-pictures/5check.webp"),
                Badge.badgeFactory("10 Check-ins Badge", "Complete 10 check-ins", BadgeType.CHECKIN, "badge-pictures/10check.webp"),
                Badge.badgeFactory("25 Check-ins Badge", "Complete 25 check-ins", BadgeType.CHECKIN, "badge-pictures/25check.webp"),
                Badge.badgeFactory("50 Check-ins Badge", "Complete 50 check-ins", BadgeType.CHECKIN, "badge-pictures/50check.webp"),
                Badge.badgeFactory("75 Check-ins Badge", "Complete 75 check-ins", BadgeType.CHECKIN, "badge-pictures/75check.webp"),
                Badge.badgeFactory("100 Check-ins Badge", "Complete 100 check-ins", BadgeType.CHECKIN, "badge-pictures/100check.webp"),
                Badge.badgeFactory("150 Check-ins Badge", "Complete 150 check-ins", BadgeType.CHECKIN, "badge-pictures/150check.webp"),
                Badge.badgeFactory("200 Check-ins Badge", "Complete 200 check-ins", BadgeType.CHECKIN, "badge-pictures/200check.webp"),
                Badge.badgeFactory("300 Check-ins Badge", "Complete 300 check-ins", BadgeType.CHECKIN, "badge-pictures/300check.webp"),
                Badge.badgeFactory("400 Check-ins Badge", "Complete 400 check-ins", BadgeType.CHECKIN, "badge-pictures/400check.webp"),
                Badge.badgeFactory("500 Check-ins Badge", "Complete 500 check-ins", BadgeType.CHECKIN, "badge-pictures/500check.webp")
        );
        badgeRepository.saveAll(badges);
    }
}



