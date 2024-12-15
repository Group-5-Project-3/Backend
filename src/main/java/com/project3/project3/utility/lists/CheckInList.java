package com.project3.project3.utility.lists;

import java.util.HashMap;
import java.util.Map;

public class CheckInList {
    public static final Map<Integer, String> CHECKIN_MILESTONES = new HashMap<>();

    static {
        CHECKIN_MILESTONES.put(1, "675689e3b876503c9b24f39e");
        CHECKIN_MILESTONES.put(5, "675689e3b876503c9b24f39f");
        CHECKIN_MILESTONES.put(10, "675689e3b876503c9b24f3a0");
        CHECKIN_MILESTONES.put(25, "675689e3b876503c9b24f3a1");
        CHECKIN_MILESTONES.put(50, "675689e3b876503c9b24f3a2");
        CHECKIN_MILESTONES.put(75, "675689e3b876503c9b24f3a3");
        CHECKIN_MILESTONES.put(100, "675689e3b876503c9b24f3a4");
        CHECKIN_MILESTONES.put(150, "675689e3b876503c9b24f3a5");
        CHECKIN_MILESTONES.put(200, "675689e3b876503c9b24f3a6");
        CHECKIN_MILESTONES.put(300, "675689e3b876503c9b24f3a7");
        CHECKIN_MILESTONES.put(400, "675689e3b876503c9b24f3a8");
        CHECKIN_MILESTONES.put(500, "675689e3b876503c9b24f3a9");
    }

    public static String getBadgeIdForCheckIn(int checkIns) {
        return CHECKIN_MILESTONES.get(checkIns);
    }
}

