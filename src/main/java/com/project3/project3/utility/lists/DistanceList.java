package com.project3.project3.utility.lists;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

public class DistanceList {
    public static final Map<Double, String> DISTANCE_MILESTONES = new HashMap<>();

    static {
        DISTANCE_MILESTONES.put(1.0, "6742c04d45979b31f37bad66");
        DISTANCE_MILESTONES.put(5.0, "6742c04d45979b31f37bad67");
        DISTANCE_MILESTONES.put(10.0, "6742c04d45979b31f37bad68");
        DISTANCE_MILESTONES.put(25.0, "6742c04d45979b31f37bad69");
        DISTANCE_MILESTONES.put(50.0, "6742c04d45979b31f37bad6a");
        DISTANCE_MILESTONES.put(75.0, "6742c04d45979b31f37bad6b");
        DISTANCE_MILESTONES.put(100.0, "6742c04d45979b31f37bad6c");
        DISTANCE_MILESTONES.put(150.0, "6742c04d45979b31f37bad6d");
        DISTANCE_MILESTONES.put(200.0, "6742c04d45979b31f37bad6e");
        DISTANCE_MILESTONES.put(300.0, "6742c04d45979b31f37bad6f");
        DISTANCE_MILESTONES.put(500.0, "6742c04d45979b31f37bad70");
        DISTANCE_MILESTONES.put(1000.0, "6742c04d45979b31f37bad71");
    }

    public static String getBadgeIdForDistance(double distance) {
        return DISTANCE_MILESTONES.get(distance);
    }
}

