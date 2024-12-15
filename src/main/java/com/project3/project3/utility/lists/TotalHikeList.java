package com.project3.project3.utility.lists;

import java.util.HashMap;
import java.util.Map;

public class TotalHikeList {
    public static final Map<Integer, String> HIKE_MILESTONES = new HashMap<>();

    static {
        HIKE_MILESTONES.put(1, "6742c04e45979b31f37bad7e");
        HIKE_MILESTONES.put(10, "6742c04e45979b31f37bad7f");
        HIKE_MILESTONES.put(25, "6742c04e45979b31f37bad80");
        HIKE_MILESTONES.put(50, "6742c04e45979b31f37bad81");
        HIKE_MILESTONES.put(75, "6742c04e45979b31f37bad82");
        HIKE_MILESTONES.put(100, "6742c04e45979b31f37bad83");
        HIKE_MILESTONES.put(150, "6742c04e45979b31f37bad84");
        HIKE_MILESTONES.put(200, "6742c04e45979b31f37bad85");
        HIKE_MILESTONES.put(300, "6742c04e45979b31f37bad86");
        HIKE_MILESTONES.put(750, "6742c04e45979b31f37bad87");
        HIKE_MILESTONES.put(1000, "6742c04e45979b31f37bad88");
    }

    public static String getBadgeIdForHikes(int hikes) {
        return HIKE_MILESTONES.get(hikes);
    }
}

