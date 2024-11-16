package com.project3.project3.utility;

import java.util.HashMap;
import java.util.Map;

public class TotalHikeList {
    public static final Map<Integer, String> HIKE_MILESTONES = new HashMap<>();

    static {
        HIKE_MILESTONES.put(1, "badge_id_for_1_hike");
        HIKE_MILESTONES.put(10, "badge_id_for_10_hikes");
        HIKE_MILESTONES.put(25, "badge_id_for_25_hikes");
        HIKE_MILESTONES.put(50, "badge_id_for_50_hikes");
        HIKE_MILESTONES.put(75, "badge_id_for_75_hikes");
        HIKE_MILESTONES.put(100, "badge_id_for_100_hikes");
        HIKE_MILESTONES.put(150, "badge_id_for_150_hikes");
        HIKE_MILESTONES.put(200, "badge_id_for_200_hikes");
        HIKE_MILESTONES.put(300, "badge_id_for_300_hikes");
        HIKE_MILESTONES.put(750, "badge_id_for_750_hikes");
        HIKE_MILESTONES.put(1000, "badge_id_for_1000_hikes");
    }

    public static String getBadgeIdForHikes(int hikes) {
        return HIKE_MILESTONES.get(hikes);
    }
}

