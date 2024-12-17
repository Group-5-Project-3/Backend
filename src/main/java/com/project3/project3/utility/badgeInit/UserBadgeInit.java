package com.project3.project3.utility.badgeInit;

import com.project3.project3.utility.AdminBadges.AnthonyUserBadges;
import com.project3.project3.utility.AdminBadges.WilliamUserBadges;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class UserBadgeInit implements CommandLineRunner {

    private final AnthonyUserBadges anthonyUserBadges;
    private final WilliamUserBadges williamUserBadges;

    public UserBadgeInit(AnthonyUserBadges anthonyUserBadges, WilliamUserBadges williamUserBadges) {
        this.anthonyUserBadges = anthonyUserBadges;
        this.williamUserBadges = williamUserBadges;
    }

    @Override
    public void run(String... args) {
//        String anthonyId = "6742ae228a71ea0c1f6ab2f7";
//        anthonyUserBadges.awardRandomBadges(anthonyId);
//        String williamId = "6742ccfa6b88cb4ff1ff0ba5";
//        williamUserBadges.getAllBadges(williamId);
    }
}
