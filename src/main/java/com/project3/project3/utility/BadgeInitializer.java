package com.project3.project3.utility;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class BadgeInitializer implements CommandLineRunner {

    private final BadgeUtility badgeUtility;

    @Autowired
    public BadgeInitializer(BadgeUtility badgeUtility) {
        this.badgeUtility = badgeUtility;
    }

    @Override
    public void run(String... args) throws Exception {
        // Call each badge creation method (only runs once at startup)
        badgeUtility.createNationalParksBadges();
        badgeUtility.createDistanceBadges();
        badgeUtility.createElevationBadges();
        badgeUtility.createTotalHikesBadges();
    }
}




