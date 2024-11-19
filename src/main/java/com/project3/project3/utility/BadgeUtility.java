package com.project3.project3.utility;

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
                new Badge("Yosemite National Park Badge", "Visit Yosemite National Park", BadgeType.NATIONAL_PARKS, "https://cst438project3.s3.us-west-1.amazonaws.com/badge-pictures/Yosmite.jpeg"),
                new Badge("Sequoia National Park Badge", "Visit Sequoia National Park", BadgeType.NATIONAL_PARKS, "https://cst438project3.s3.us-west-1.amazonaws.com/badge-pictures/Sequoia.jpeg"),
                new Badge("Redwood National Park Badge", "Visit Redwood National Park", BadgeType.NATIONAL_PARKS, "https://cst438project3.s3.us-west-1.amazonaws.com/badge-pictures/Redwood.jpeg"),
                new Badge("Pinnacles National Park Badge", "Visit Pinnacles National Park", BadgeType.NATIONAL_PARKS, "https://cst438project3.s3.us-west-1.amazonaws.com/badge-pictures/Pinnacles.jpeg"),
                new Badge("Lassen National Park Badge", "Visit Lassen Volcanic National Park", BadgeType.NATIONAL_PARKS, "https://cst438project3.s3.us-west-1.amazonaws.com/badge-pictures/Lasso.jpeg"),
                new Badge("Joshua Tree National Park Badge", "Visit Joshua Tree National Park", BadgeType.NATIONAL_PARKS, "https://cst438project3.s3.us-west-1.amazonaws.com/badge-pictures/JoshuaTree.jpeg"),
                new Badge("Death Valley National Park Badge", "Visit Death Valley National Park", BadgeType.NATIONAL_PARKS, "https://cst438project3.s3.us-west-1.amazonaws.com/badge-pictures/DeathValley.jpeg"),
                new Badge("Channel Islands National Park Badge", "Visit Channel Islands National Park", BadgeType.NATIONAL_PARKS, "https://cst438project3.s3.us-west-1.amazonaws.com/badge-pictures/ChannelIslands.jpeg"),
                new Badge("Kings Canyon National Park Badge", "Visit Kings Canyon Nation Park", BadgeType.NATIONAL_PARKS, "https://cst438project3.s3.us-west-1.amazonaws.com/badge-pictures/KingsCanyon.jpeg")
        );
        badgeRepository.saveAll(badges);
    }

    // Method to create and save multiple Distance badges
    public void createDistanceBadges() {
        List<Badge> badges = Arrays.asList(
                new Badge("Trail Starter", "Hike 1 kilometer", BadgeType.DISTANCE, "https://cst438project3.s3.us-west-1.amazonaws.com/badge-pictures/1km.jpeg"),
                new Badge("Path Pacer", "Hike 5 kilometer", BadgeType.DISTANCE, "https://cst438project3.s3.us-west-1.amazonaws.com/badge-pictures/5km.jpeg"),
                new Badge("Wayfarer", "Hike 10 kilometer", BadgeType.DISTANCE, "https://cst438project3.s3.us-west-1.amazonaws.com/badge-pictures/10km.jpeg"),
                new Badge("Trail Tracker", "Hike 25 kilometer", BadgeType.DISTANCE, "https://cst438project3.s3.us-west-1.amazonaws.com/badge-pictures/25km.jpeg"),
                new Badge("Journey Voyager", "Hike 50 kilometer", BadgeType.DISTANCE, "https://cst438project3.s3.us-west-1.amazonaws.com/badge-pictures/50km.jpeg"),
                new Badge("Distance Drifter", "Hike 75 kilometer", BadgeType.DISTANCE, "https://cst438project3.s3.us-west-1.amazonaws.com/badge-pictures/75km.jpeg"),
                new Badge("Long Hauler", "Hike 100 kilometer", BadgeType.DISTANCE, "https://cst438project3.s3.us-west-1.amazonaws.com/badge-pictures/100km.jpeg"),
                new Badge("Explorer", "Hike 150 kilometer", BadgeType.DISTANCE, "https://cst438project3.s3.us-west-1.amazonaws.com/badge-pictures/150km.jpeg"),
                new Badge("Endurance Hiker", "Hike 200 kilometer", BadgeType.DISTANCE, "https://cst438project3.s3.us-west-1.amazonaws.com/badge-pictures/200km.jpeg"),
                new Badge("Path Pioneer", "Hike 300 kilometer", BadgeType.DISTANCE, "https://cst438project3.s3.us-west-1.amazonaws.com/badge-pictures/300km.jpeg"),
                new Badge("Trail Legend", "Hike 500 kilometer", BadgeType.DISTANCE, "https://cst438project3.s3.us-west-1.amazonaws.com/badge-pictures/500km.jpeg"),
                new Badge("Trail Blazer", "Hike 1000 kilometer", BadgeType.DISTANCE, "https://cst438project3.s3.us-west-1.amazonaws.com/badge-pictures/1000km.jpeg")
        );
        badgeRepository.saveAll(badges);
    }

    // Method to create and save multiple Elevation badges
    public void createElevationBadges() {
        List<Badge> badges = Arrays.asList(
                new Badge("Horizon Hiker", "Reach 100m elevation", BadgeType.ELEVATION, "https://cst438project3.s3.us-west-1.amazonaws.com/badge-pictures/100m.jpeg"),
                new Badge("Base Trekker", "Reach 500m elevation", BadgeType.ELEVATION, "https://cst438project3.s3.us-west-1.amazonaws.com/badge-pictures/500m.jpeg"),
                new Badge("Hill Climber", "Reach 1000m elevation", BadgeType.ELEVATION, "https://cst438project3.s3.us-west-1.amazonaws.com/badge-pictures/1000km.jpeg"),
                new Badge("Altitude Adventurer", "Reach 2000m elevation", BadgeType.ELEVATION, "https://cst438project3.s3.us-west-1.amazonaws.com/badge-pictures/2000m.jpeg"),
                new Badge("Sky Seeker", "Reach 5000m elevation", BadgeType.ELEVATION, "https://cst438project3.s3.us-west-1.amazonaws.com/badge-pictures/5000m.jpeg"),
                new Badge("Cloud Wanderer", "Reach 10000m elevation", BadgeType.ELEVATION, "https://cst438project3.s3.us-west-1.amazonaws.com/badge-pictures/10000m.jpeg"),
                new Badge("Summit Explorer", "Reach 15000m elevation", BadgeType.ELEVATION, "https://cst438project3.s3.us-west-1.amazonaws.com/badge-pictures/15000m.jpeg"),
                new Badge("Highlander", "Reach 20000m elevation", BadgeType.ELEVATION, "https://cst438project3.s3.us-west-1.amazonaws.com/badge-pictures/20000m.jpeg"),
                new Badge("Mountain Conqueror", "Reach 25000m elevation", BadgeType.ELEVATION, "https://cst438project3.s3.us-west-1.amazonaws.com/badge-pictures/25000m.jpeg"),
                new Badge("Apex Ascender", "Reach 50000m elevation", BadgeType.ELEVATION, "https://cst438project3.s3.us-west-1.amazonaws.com/badge-pictures/50000m.jpeg"),
                new Badge("Mountain Master", "Reach 75000m elevation", BadgeType.ELEVATION, "https://cst438project3.s3.us-west-1.amazonaws.com/badge-pictures/75000m.jpeg"),
                new Badge("Elevation Elite", "Reach 100000m elevation", BadgeType.ELEVATION, "https://cst438project3.s3.us-west-1.amazonaws.com/badge-pictures/100000m.jpeg")
        );
        badgeRepository.saveAll(badges);
    }

    // Method to create and save multiple Total Hikes badges
    public void createTotalHikesBadges() {
        List<Badge> badges = Arrays.asList(
                new Badge("First Hike Completed", "Complete 1 Hike", BadgeType.TOTAL_HIKES, "https://cst438project3.s3.us-west-1.amazonaws.com/badge-pictures/1hike.jpeg"),
                new Badge("10 Hikes Completed", "Complete 10 hikes", BadgeType.TOTAL_HIKES, "https://cst438project3.s3.us-west-1.amazonaws.com/badge-pictures/10hike.jpeg"),
                new Badge("25 Hikes Completed", "Complete 25 hikes", BadgeType.TOTAL_HIKES, "https://cst438project3.s3.us-west-1.amazonaws.com/badge-pictures/25hike.jpeg"),
                new Badge("50 Hikes Completed", "Complete 50 Hikes", BadgeType.TOTAL_HIKES, "https://cst438project3.s3.us-west-1.amazonaws.com/badge-pictures/50hike.jpeg"),
                new Badge("75 Hikes Completed", "Complete 75 Hikes", BadgeType.TOTAL_HIKES, "https://cst438project3.s3.us-west-1.amazonaws.com/badge-pictures/75hike.jpeg"),
                new Badge("100 Hikes Completed", "Complete 100 Hikes", BadgeType.TOTAL_HIKES, "https://cst438project3.s3.us-west-1.amazonaws.com/badge-pictures/100hike.jpeg"),
                new Badge("150 Hikes Completed", "Complete 150 Hikes", BadgeType.TOTAL_HIKES, "https://cst438project3.s3.us-west-1.amazonaws.com/badge-pictures/150hike.jpeg"),
                new Badge("200 Hikes Completed", "Complete 200 Hikes", BadgeType.TOTAL_HIKES, "https://cst438project3.s3.us-west-1.amazonaws.com/badge-pictures/200hike.jpeg"),
                new Badge("300 Hikes Completed", "Complete 300 Hikes", BadgeType.TOTAL_HIKES, "https://cst438project3.s3.us-west-1.amazonaws.com/badge-pictures/300hike.jpeg"),
                new Badge("750 Hikes Completed", "Complete 750 Hikes", BadgeType.TOTAL_HIKES, "https://cst438project3.s3.us-west-1.amazonaws.com/badge-pictures/750hike.jpeg"),
                new Badge("1000 Hikes Completed", "Complete 1000 Hikes", BadgeType.TOTAL_HIKES, "https://cst438project3.s3.us-west-1.amazonaws.com/badge-pictures/1000hike.jpeg")
        );
        badgeRepository.saveAll(badges);
    }
}



