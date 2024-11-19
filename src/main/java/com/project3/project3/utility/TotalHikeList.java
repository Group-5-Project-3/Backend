package com.project3.project3.utility;

import java.util.HashMap;
import java.util.Map;

public class TotalHikeList {
    public static final Map<Integer, String> HIKE_MILESTONES = new HashMap<>();

    static {
        HIKE_MILESTONES.put(1, "673bc2f213b4332cc0525196");
        HIKE_MILESTONES.put(10, "673bc2f213b4332cc0525197");
        HIKE_MILESTONES.put(25, "673bc2f213b4332cc0525198");
        HIKE_MILESTONES.put(50, "673bc2f213b4332cc0525199");
        HIKE_MILESTONES.put(75, "673bc2f213b4332cc052519a");
        HIKE_MILESTONES.put(100, "673bc2f213b4332cc052519b");
        HIKE_MILESTONES.put(150, "673bc2f213b4332cc052519c");
        HIKE_MILESTONES.put(200, "673bc2f213b4332cc052519d");
        HIKE_MILESTONES.put(300, "673bc2f213b4332cc052519e");
        HIKE_MILESTONES.put(750, "673bc2f213b4332cc052519f");
        HIKE_MILESTONES.put(1000, "673bc2f213b4332cc05251a0");
    }

    public static String getBadgeIdForHikes(int hikes) {
        return HIKE_MILESTONES.get(hikes);
    }
}

