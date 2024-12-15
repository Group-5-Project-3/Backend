package com.project3.project3.service;

import com.project3.project3.model.UserBadge;
import com.project3.project3.repository.BadgeRepository;
import com.project3.project3.repository.UserBadgeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.time.LocalDateTime;

@Service
public class UserBadgeService {

    private final UserBadgeRepository userBadgeRepository;
    private final BadgeRepository badgeRepository;

    @Autowired
    public UserBadgeService(UserBadgeRepository userBadgeRepository, BadgeRepository badgeRepository) {
        this.userBadgeRepository = userBadgeRepository;
        this.badgeRepository = badgeRepository;
    }

    public List<UserBadge> getBadgesByUserId(String userId) {
        return userBadgeRepository.findByUserId(userId);
    }

    public Optional<UserBadge> getUserBadge(String userId, String badgeId) {
        return userBadgeRepository.findByUserIdAndBadgeId(userId, badgeId);
    }

    public UserBadge awardBadgeToUser(String userId, String badgeId) {
        UserBadge userBadge = UserBadge.userBadgeFactory(userId, badgeId, LocalDateTime.now());
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

    public void deleteByUserId(String userId) {
        List<UserBadge> userBadges = userBadgeRepository.findByUserId(userId);
        if (!userBadges.isEmpty()) {
            userBadgeRepository.deleteAll(userBadges);
        }
    }
}


