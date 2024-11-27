package com.project3.project3.service;

import com.project3.project3.model.Badge;
import com.project3.project3.model.UserBadge;
import com.project3.project3.repository.BadgeRepository;
import com.project3.project3.repository.UserBadgeRepository;
import com.project3.project3.utility.S3Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.time.LocalDateTime;

@Service
public class UserBadgeService {

    private final UserBadgeRepository userBadgeRepository;
    private final BadgeRepository badgeRepository;
    private final S3Util s3Util;

    @Autowired
    public UserBadgeService(UserBadgeRepository userBadgeRepository, BadgeRepository badgeRepository, S3Util s3Util) {
        this.userBadgeRepository = userBadgeRepository;
        this.badgeRepository = badgeRepository;
        this.s3Util = s3Util;
    }

    public List<Badge> getBadgesByUserId(String userId) {
        List<UserBadge> userBadges = userBadgeRepository.findByUserId(userId);
        List<Badge> badges = new ArrayList<>();

        for (UserBadge userBadge : userBadges) {
            Badge badge = badgeRepository.findByBadgeId(userBadge.getBadgeId());
            String bucketName = System.getenv("BUCKET_NAME");
            String presignedUrl = s3Util.generatePresignedUrl(bucketName, badge.getBadgeUrl());
            badge.setBadgeUrl(presignedUrl);
            badges.add(badge);
        }
        return badges;
    }

    public Optional<UserBadge> getUserBadge(String userId, String badgeId) {
        return userBadgeRepository.findByUserIdAndBadgeId(userId, badgeId);
    }

    public UserBadge awardBadgeToUser(String userId, String badgeId) {
        UserBadge userBadge = new UserBadge(userId, badgeId, LocalDateTime.now());
        return userBadgeRepository.save(userBadge);
    }

    public boolean hasBadge(String userId, String badgeId) {
        return userBadgeRepository.findByUserIdAndBadgeId(userId, badgeId).isPresent();
    }

    public boolean removeBadge(String id) {
        if (userBadgeRepository.existsById(id)) {
            userBadgeRepository.deleteById(id);
            return true;
        }
        return false;
    }
}


