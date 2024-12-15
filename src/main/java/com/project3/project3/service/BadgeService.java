package com.project3.project3.service;

import com.project3.project3.model.Badge;
import com.project3.project3.model.BadgeType;
import com.project3.project3.repository.BadgeRepository;
import com.project3.project3.utility.S3Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.tinylog.Logger;

@Service
public class BadgeService {

    private final BadgeRepository badgeRepository;
    private final S3Util s3Util;

    @Autowired
    public BadgeService(BadgeRepository badgeRepository, S3Util s3Util) {
        this.badgeRepository = badgeRepository;
        this.s3Util = s3Util;
    }

    public List<Badge> getAllBadges() {
        List<Badge> badges = badgeRepository.findAll();
        List<Badge> updatedBadges = new ArrayList<>();
        String bucketName = System.getenv("BUCKET_NAME");

        for (Badge badge : badges) {
            String presignedUrl = s3Util.generatePresignedUrl(bucketName, badge.getBadgeObjectKey());
            badge.setBadgeObjectKey(presignedUrl);
            updatedBadges.add(badge);
        }
        return updatedBadges;
    }


    public Optional<Badge> getBadgeById(String id) {
        return badgeRepository.findById(id);
    }

    public Badge createBadge(Badge badge) {
        return badgeRepository.save(badge);
    }

    public boolean deleteBadge(String id) {
        if (badgeRepository.existsById(id)) {
            badgeRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public List<Badge> getBadgesByType(BadgeType badgeType) {
        List<Badge> badges = badgeRepository.findByType(badgeType);
        List<Badge> updatedBadges = new ArrayList<>();
        String bucketName = System.getenv("BUCKET_NAME");

        for (Badge badge : badges) {
            String presignedUrl = s3Util.generatePresignedUrl(bucketName, badge.getBadgeObjectKey());
            badge.setBadgeObjectKey(presignedUrl);
            updatedBadges.add(badge);
        }

        return updatedBadges;
    }

}

