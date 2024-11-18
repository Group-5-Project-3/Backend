package com.project3.project3.utility;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

public class ElevationList {

    public static final Map<Double, String> ELEVATION_MILESTONES = new HashMap<>();

    static {
        ELEVATION_MILESTONES.put(100.0, "badge_id_for_100m");
        ELEVATION_MILESTONES.put(500.0, "badge_id_for_500m");
        ELEVATION_MILESTONES.put(1000.0, "badge_id_for_1000m");
        ELEVATION_MILESTONES.put(2000.0, "badge_id_for_2000m");
        ELEVATION_MILESTONES.put(5000.0, "badge_id_for_5000m");
        ELEVATION_MILESTONES.put(10000.0, "badge_id_for_10000m");
        ELEVATION_MILESTONES.put(15000.0, "badge_id_for_15000m");
        ELEVATION_MILESTONES.put(20000.0, "badge_id_for_20000m");
        ELEVATION_MILESTONES.put(25000.0, "badge_id_for_25000m");
        ELEVATION_MILESTONES.put(50000.0, "badge_id_for_50000m");
        ELEVATION_MILESTONES.put(75000.0, "badge_id_for_75000m");
        ELEVATION_MILESTONES.put(100000.0, "badge_id_for_100000m");
    }

    public static String getBadgeIdForElevation(double elevationGain) {
        return ELEVATION_MILESTONES.get(elevationGain);
    }
}

