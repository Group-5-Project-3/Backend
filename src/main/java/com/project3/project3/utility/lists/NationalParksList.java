package com.project3.project3.utility.lists;

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
        PARK_BADGE_IDS.put("Channel Islands National Park", "6742c04d45979b31f37bad64");
        PARK_BADGE_IDS.put("Death Valley National Park", "6742c04d45979b31f37bad63");
        PARK_BADGE_IDS.put("Joshua Tree National Park", "6742c04d45979b31f37bad62");
        PARK_BADGE_IDS.put("Kings Canyon National Park", "6742c04d45979b31f37bad65");
        PARK_BADGE_IDS.put("Lassen Volcanic National Park", "6742c04d45979b31f37bad61");
        PARK_BADGE_IDS.put("Pinnacles National Park", "6742c04d45979b31f37bad60");
        PARK_BADGE_IDS.put("Redwood National and State Parks", "6742c04d45979b31f37bad5f");
        PARK_BADGE_IDS.put("Sequoia National Park", "6742c04d45979b31f37bad5e");
        PARK_BADGE_IDS.put("Yosemite National Park", "6742c04d45979b31f37bad5d");
    }

    public static boolean isCaliforniaNationalPark(String parkName) {
        return CA_NATIONAL_PARKS.contains(parkName);
    }

    public static String getBadgeIdForPark(String parkName) {
        return PARK_BADGE_IDS.get(parkName);
    }
}


