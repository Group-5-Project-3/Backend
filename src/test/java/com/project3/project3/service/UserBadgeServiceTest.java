package com.project3.project3.service;

import com.project3.project3.model.UserBadge;
import com.project3.project3.repository.UserBadgeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserBadgeServiceTest {

    @InjectMocks
    private UserBadgeService userBadgeService;

    @Mock
    private UserBadgeRepository userBadgeRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetUserBadgesByUserId() {
        // Arrange
        String userId = "user123";
        List<UserBadge> badges = Arrays.asList(new UserBadge(), new UserBadge());
        when(userBadgeRepository.findByUserId(userId)).thenReturn(badges);

        // Act
        List<UserBadge> result = userBadgeService.getUserBadgesByUserId(userId);

        // Assert
        assertEquals(2, result.size());
        verify(userBadgeRepository, times(1)).findByUserId(userId);
    }

    @Test
    void testGetUserBadge_Found() {
        // Arrange
        String userId = "user123";
        String badgeId = "badge123";
        UserBadge badge = new UserBadge(userId, badgeId, LocalDateTime.now());
        when(userBadgeRepository.findByUserIdAndBadgeId(userId, badgeId)).thenReturn(Optional.of(badge));

        // Act
        Optional<UserBadge> result = userBadgeService.getUserBadge(userId, badgeId);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(badgeId, result.get().getBadgeId());
        verify(userBadgeRepository, times(1)).findByUserIdAndBadgeId(userId, badgeId);
    }

    @Test
    void testGetUserBadge_NotFound() {
        // Arrange
        String userId = "user123";
        String badgeId = "nonexistent";
        when(userBadgeRepository.findByUserIdAndBadgeId(userId, badgeId)).thenReturn(Optional.empty());

        // Act
        Optional<UserBadge> result = userBadgeService.getUserBadge(userId, badgeId);

        // Assert
        assertFalse(result.isPresent());
        verify(userBadgeRepository, times(1)).findByUserIdAndBadgeId(userId, badgeId);
    }

    @Test
    void testAwardBadgeToUser() {
        // Arrange
        String userId = "user123";
        String badgeId = "badge123";
        UserBadge userBadge = new UserBadge(userId, badgeId, LocalDateTime.now());
        when(userBadgeRepository.save(any(UserBadge.class))).thenReturn(userBadge);

        // Act
        UserBadge result = userBadgeService.awardBadgeToUser(userId, badgeId);

        // Assert
        assertEquals(userId, result.getUserId());
        assertEquals(badgeId, result.getBadgeId());
        assertNotNull(result.getAwardedTimestamp());
        verify(userBadgeRepository, times(1)).save(any(UserBadge.class));
    }

    @Test
    void testHasBadge_True() {
        // Arrange
        String userId = "user123";
        String badgeId = "badge123";
        when(userBadgeRepository.findByUserIdAndBadgeId(userId, badgeId)).thenReturn(Optional.of(new UserBadge()));

        // Act
        boolean result = userBadgeService.hasBadge(userId, badgeId);

        // Assert
        assertTrue(result);
        verify(userBadgeRepository, times(1)).findByUserIdAndBadgeId(userId, badgeId);
    }

    @Test
    void testHasBadge_False() {
        // Arrange
        String userId = "user123";
        String badgeId = "nonexistent";
        when(userBadgeRepository.findByUserIdAndBadgeId(userId, badgeId)).thenReturn(Optional.empty());

        // Act
        boolean result = userBadgeService.hasBadge(userId, badgeId);

        // Assert
        assertFalse(result);
        verify(userBadgeRepository, times(1)).findByUserIdAndBadgeId(userId, badgeId);
    }

    @Test
    void testRemoveBadge_Success() {
        // Arrange
        String id = "badge123";
        when(userBadgeRepository.existsById(id)).thenReturn(true);

        // Act
        boolean result = userBadgeService.removeBadge(id);

        // Assert
        assertTrue(result);
        verify(userBadgeRepository, times(1)).existsById(id);
        verify(userBadgeRepository, times(1)).deleteById(id);
    }

    @Test
    void testRemoveBadge_NotFound() {
        // Arrange
        String id = "nonexistent";
        when(userBadgeRepository.existsById(id)).thenReturn(false);

        // Act
        boolean result = userBadgeService.removeBadge(id);

        // Assert
        assertFalse(result);
        verify(userBadgeRepository, times(1)).existsById(id);
        verify(userBadgeRepository, never()).deleteById(id);
    }
}
