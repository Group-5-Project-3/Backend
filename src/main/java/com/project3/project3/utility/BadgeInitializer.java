package com.project3.project3.utility;

public class BadgeInitializer {

    private static final BadgeUtility badgeUtility = new BadgeUtility();

    public static void initializeBadges() {
        badgeUtility.createNationalParksBadge();
        badgeUtility.createDistanceBadge();
        badgeUtility.createElevationBadge();
        badgeUtility.createTotalHikesBadge();
    }
}


