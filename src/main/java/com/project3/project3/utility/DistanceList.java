package com.project3.project3.utility;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

public class DistanceList {
    public static final Map<Double, String> DISTANCE_MILESTONES = new HashMap<>();

    static {
        DISTANCE_MILESTONES.put(1.0, "673bc2f213b4332cc052517e");
        DISTANCE_MILESTONES.put(5.0, "673bc2f213b4332cc052517f");
        DISTANCE_MILESTONES.put(10.0, "673bc2f213b4332cc0525180");
        DISTANCE_MILESTONES.put(25.0, "673bc2f213b4332cc0525181");
        DISTANCE_MILESTONES.put(50.0, "673bc2f213b4332cc0525182");
        DISTANCE_MILESTONES.put(75.0, "673bc2f213b4332cc0525183");
        DISTANCE_MILESTONES.put(100.0, "673bc2f213b4332cc0525184");
        DISTANCE_MILESTONES.put(150.0, "673bc2f213b4332cc0525185");
        DISTANCE_MILESTONES.put(200.0, "673bc2f213b4332cc0525186");
        DISTANCE_MILESTONES.put(300.0, "673bc2f213b4332cc0525187");
        DISTANCE_MILESTONES.put(500.0, "673bc2f213b4332cc0525188");
        DISTANCE_MILESTONES.put(1000.0, "673bc2f213b4332cc0525189");
    }

    public static String getBadgeIdForDistance(double distance) {
        return DISTANCE_MILESTONES.get(distance);
    }
}

