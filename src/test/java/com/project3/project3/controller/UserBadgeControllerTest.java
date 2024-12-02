package com.project3.project3.controller;

import com.project3.project3.model.UserBadge;
import com.project3.project3.service.UserBadgeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class UserBadgeControllerTest {

    @InjectMocks
    private UserBadgeController userBadgeController;

    @Mock
    private UserBadgeService userBadgeService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetUserBadges() {
        // Arrange
        String userId = "user123";
        UserBadge badge1 = new UserBadge(userId, "badge1", LocalDateTime.now());
        UserBadge badge2 = new UserBadge(userId, "badge2", LocalDateTime.now());
        when(userBadgeService.getUserBadgesByUserId(userId)).thenReturn(Arrays.asList(badge1, badge2));

        // Act
        ResponseEntity<List<UserBadge>> response = userBadgeController.getUserBadges(userId);

        // Assert
        assertEquals(200, response.getStatusCode().value());
        assertEquals(2, response.getBody().size());
        verify(userBadgeService, times(1)).getUserBadgesByUserId(userId);
    }

    @Test
    void testGetUserBadge_Success() {
        // Arrange
        String userId = "user123";
        String badgeId = "badge123";
        UserBadge userBadge = new UserBadge(userId, badgeId, LocalDateTime.now());
        when(userBadgeService.getUserBadge(userId, badgeId)).thenReturn(Optional.of(userBadge));

        // Act
        ResponseEntity<UserBadge> response = userBadgeController.getUserBadge(userId, badgeId);

        // Assert
        assertEquals(200, response.getStatusCode().value());
        assertEquals(badgeId, response.getBody().getBadgeId());
        verify(userBadgeService, times(1)).getUserBadge(userId, badgeId);
    }

    @Test
    void testGetUserBadge_NotFound() {
        // Arrange
        String userId = "user123";
        String badgeId = "nonexistent";
        when(userBadgeService.getUserBadge(userId, badgeId)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<UserBadge> response = userBadgeController.getUserBadge(userId, badgeId);

        // Assert
        assertEquals(404, response.getStatusCode().value());
        verify(userBadgeService, times(1)).getUserBadge(userId, badgeId);
    }

    @Test
    void testAwardBadgeToUser() {
        // Arrange
        String userId = "user123";
        String badgeId = "badge123";
        UserBadge awardedBadge = new UserBadge(userId, badgeId, LocalDateTime.now());
        when(userBadgeService.awardBadgeToUser(userId, badgeId)).thenReturn(awardedBadge);

        // Act
        ResponseEntity<UserBadge> response = userBadgeController.awardBadgeToUser(userId, badgeId);

        // Assert
        assertEquals(200, response.getStatusCode().value());
        assertEquals(badgeId, response.getBody().getBadgeId());
        verify(userBadgeService, times(1)).awardBadgeToUser(userId, badgeId);
    }

    @Test
    void testRemoveUserBadge_Success() {
        // Arrange
        String badgeId = "badge123";
        when(userBadgeService.removeBadge(badgeId)).thenReturn(true);

        // Act
        ResponseEntity<Void> response = userBadgeController.removeUserBadge("user123", badgeId);

        // Assert
        assertEquals(204, response.getStatusCode().value());
        verify(userBadgeService, times(1)).removeBadge(badgeId);
    }

    @Test
    void testRemoveUserBadge_NotFound() {
        // Arrange
        String badgeId = "nonexistent";
        when(userBadgeService.removeBadge(badgeId)).thenReturn(false);

        // Act
        ResponseEntity<Void> response = userBadgeController.removeUserBadge("user123", badgeId);

        // Assert
        assertEquals(404, response.getStatusCode().value());
        verify(userBadgeService, times(1)).removeBadge(badgeId);
    }
}
