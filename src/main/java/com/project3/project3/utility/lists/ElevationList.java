package com.project3.project3.utility.lists;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

public class ElevationList {

    public static final Map<Double, String> ELEVATION_MILESTONES = new HashMap<>();

    static {
        ELEVATION_MILESTONES.put(100.0, "673bc2f213b4332cc052518a");
        ELEVATION_MILESTONES.put(500.0, "673bc2f213b4332cc052518b");
        ELEVATION_MILESTONES.put(1000.0, "673bc2f213b4332cc052518c");
        ELEVATION_MILESTONES.put(2000.0, "673bc2f213b4332cc052518d");
        ELEVATION_MILESTONES.put(5000.0, "673bc2f213b4332cc052518e");
        ELEVATION_MILESTONES.put(10000.0, "673bc2f213b4332cc052518f");
        ELEVATION_MILESTONES.put(15000.0, "673bc2f213b4332cc0525190");
        ELEVATION_MILESTONES.put(20000.0, "673bc2f213b4332cc0525191");
        ELEVATION_MILESTONES.put(25000.0, "673bc2f213b4332cc0525192");
        ELEVATION_MILESTONES.put(50000.0, "673bc2f213b4332cc0525193");
        ELEVATION_MILESTONES.put(75000.0, "673bc2f213b4332cc0525194");
        ELEVATION_MILESTONES.put(100000.0, "673bc2f213b4332cc0525195");
    }

    public static String getBadgeIdForElevation(double elevationGain) {
        return ELEVATION_MILESTONES.get(elevationGain);
    }
}

