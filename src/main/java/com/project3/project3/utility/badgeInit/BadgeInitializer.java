package com.project3.project3.utility.badgeInit;

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
        // Uncomment to reload badges into MongoDB if changes are made to badges
//        badgeUtility.createNationalParksBadges();
//        badgeUtility.createDistanceBadges();
//        badgeUtility.createElevationBadges();
//        badgeUtility.createTotalHikesBadges();
//        badgeUtility.createCheckInBadges();
    }
}




