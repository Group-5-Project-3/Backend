package com.project3.project3.utility;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
@Component
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
        PARK_BADGE_IDS.put("Channel Islands National Park", "badge_id_channel_islands");
        PARK_BADGE_IDS.put("Death Valley National Park", "badge_id_death_valley");
        PARK_BADGE_IDS.put("Joshua Tree National Park", "badge_id_joshua_tree");
        PARK_BADGE_IDS.put("Kings Canyon National Park", "badge_id_kings_canyon");
        PARK_BADGE_IDS.put("Lassen Volcanic National Park", "badge_id_lassen_volcanic");
        PARK_BADGE_IDS.put("Pinnacles National Park", "badge_id_pinnacles");
        PARK_BADGE_IDS.put("Redwood National and State Parks", "badge_id_redwood");
        PARK_BADGE_IDS.put("Sequoia National Park", "badge_id_sequoia");
        PARK_BADGE_IDS.put("Yosemite National Park", "badge_id_yosemite");
    }

    public static boolean isCaliforniaNationalPark(String parkName) {
        return CA_NATIONAL_PARKS.contains(parkName);
    }

    public static String getBadgeIdForPark(String parkName) {
        return PARK_BADGE_IDS.get(parkName);
    }
}


