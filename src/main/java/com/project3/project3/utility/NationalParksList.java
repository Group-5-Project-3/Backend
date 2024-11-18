package com.project3.project3.utility;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class NationalParksList {

    public static final Set<String> CA_NATIONAL_PARKS = Set.of(
            "Channel Islands National Park",
            "Death Valley National Park",
            "Joshua Tree National Park",
            "Kings Canyon National Park",
            "Lassen Volcanic National Park",
            "Pinnacles National Park",
            "Redwood National and State Parks",
            "Sequoia National Park",
            "Yosemite National Park"
    );

    private static final Map<String, String> PARK_BADGE_IDS = new HashMap<>();

    static {
        // Replace Trail Name with PlacesId of these individual national parks
        PARK_BADGE_IDS.put("Channel Islands National Park", "673bc2f213b4332cc052517c");
        PARK_BADGE_IDS.put("Death Valley National Park", "673bc2f213b4332cc052517b");
        PARK_BADGE_IDS.put("Joshua Tree National Park", "673bc2f213b4332cc052517a");
        PARK_BADGE_IDS.put("Kings Canyon National Park", "673bc2f213b4332cc052517d");
        PARK_BADGE_IDS.put("Lassen Volcanic National Park", "673bc2f213b4332cc0525179");
        PARK_BADGE_IDS.put("Pinnacles National Park", "673bc2f213b4332cc0525178");
        PARK_BADGE_IDS.put("Redwood National and State Parks", "673bc2f213b4332cc0525177");
        PARK_BADGE_IDS.put("Sequoia National Park", "673bc2f213b4332cc0525176");
        PARK_BADGE_IDS.put("Yosemite National Park", "673bc2f213b4332cc0525175");
    }

    public static boolean isCaliforniaNationalPark(String parkName) {
        return CA_NATIONAL_PARKS.contains(parkName);
    }

    public static String getBadgeIdForPark(String parkName) {
        return PARK_BADGE_IDS.get(parkName);
    }
}


