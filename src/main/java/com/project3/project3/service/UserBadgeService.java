package com.project3.project3.service;

import com.project3.project3.model.UserBadge;
import com.project3.project3.repository.UserBadgeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserBadgeService {

    private final UserBadgeRepository userBadgeRepository;

    @Autowired
    public UserBadgeService(UserBadgeRepository userBadgeRepository) {
        this.userBadgeRepository = userBadgeRepository;
    }

    public List<UserBadge> getUserBadgesByUserId(String userId) {
        return userBadgeRepository.findByUserId(userId);
    }

    public UserBadge awardBadgeToUser(UserBadge userBadge) {
        return userBadgeRepository.save(userBadge);
    }

    public void removeBadge(String id) {
        userBadgeRepository.deleteById(id);
    }
}

