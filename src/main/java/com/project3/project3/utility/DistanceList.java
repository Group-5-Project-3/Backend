package com.project3.project3.utility;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
@Component
public class DistanceList {
    public static final Map<Double, String> DISTANCE_MILESTONES = new HashMap<>();

    static {
        DISTANCE_MILESTONES.put(1.0, "badge_id_for_1_km_distance");
        DISTANCE_MILESTONES.put(5.0, "badge_id_for_5_km_distance");
        DISTANCE_MILESTONES.put(10.0, "badge_id_for_10_km_distance");
        DISTANCE_MILESTONES.put(25.0, "badge_id_for_25_km_distance");
        DISTANCE_MILESTONES.put(50.0, "badge_id_for_50_km_distance");
        DISTANCE_MILESTONES.put(75.0, "badge_id_for_75_km_distance");
        DISTANCE_MILESTONES.put(100.0, "badge_id_for_100_km_distance");
        DISTANCE_MILESTONES.put(150.0, "badge_id_for_150_km_distance");
        DISTANCE_MILESTONES.put(200.0, "badge_id_for_200_km_distance");
        DISTANCE_MILESTONES.put(300.0, "badge_id_for_300_km_distance");
        DISTANCE_MILESTONES.put(500.0, "badge_id_for_500_km_distance");
        DISTANCE_MILESTONES.put(1000.0, "badge_id_for_1000_km_distance");
    }

    public static String getBadgeIdForDistance(double distance) {
        return DISTANCE_MILESTONES.get(distance);
    }
}

