package com.project3.project3.service;

import com.project3.project3.model.Badge;
import com.project3.project3.model.BadgeType;
import com.project3.project3.repository.BadgeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BadgeServiceTest {

    @InjectMocks
    private BadgeService badgeService;

    @Mock
    private BadgeRepository badgeRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllBadges() {
        // Arrange
        List<Badge> badges = Arrays.asList(
                new Badge("National Parks Badge", "Visit 5 national parks", BadgeType.NATIONAL_PARKS, "url1"),
                new Badge("Distance Badge", "Hike 100 miles", BadgeType.DISTANCE, "url2")
        );
        when(badgeRepository.findAll()).thenReturn(badges);

        // Act
        List<Badge> result = badgeService.getAllBadges();

        // Assert
        assertEquals(2, result.size());
        assertEquals("National Parks Badge", result.get(0).getName());
        assertEquals("Distance Badge", result.get(1).getName());
        verify(badgeRepository, times(1)).findAll();
    }

    @Test
    void testGetBadgeById_Found() {
        // Arrange
        Badge badge = new Badge("Elevation Badge", "Climb 10,000 feet", BadgeType.ELEVATION, "url3");
        when(badgeRepository.findById("1")).thenReturn(Optional.of(badge));

        // Act
        Optional<Badge> result = badgeService.getBadgeById("1");

        // Assert
        assertTrue(result.isPresent());
        assertEquals("Elevation Badge", result.get().getName());
        verify(badgeRepository, times(1)).findById("1");
    }

    @Test
    void testGetBadgeById_NotFound() {
        // Arrange
        when(badgeRepository.findById("99")).thenReturn(Optional.empty());

        // Act
        Optional<Badge> result = badgeService.getBadgeById("99");

        // Assert
        assertFalse(result.isPresent());
        verify(badgeRepository, times(1)).findById("99");
    }

    @Test
    void testCreateBadge() {
        // Arrange
        Badge badge = new Badge("Total Hikes Badge", "Complete 50 hikes", BadgeType.TOTAL_HIKES, "url4");
        when(badgeRepository.save(badge)).thenReturn(badge);

        // Act
        Badge result = badgeService.createBadge(badge);

        // Assert
        assertNotNull(result);
        assertEquals("Total Hikes Badge", result.getName());
        verify(badgeRepository, times(1)).save(badge);
    }

    @Test
    void testDeleteBadge_Success() {
        // Arrange
        String badgeId = "1";
        when(badgeRepository.existsById(badgeId)).thenReturn(true);

        // Act
        boolean result = badgeService.deleteBadge(badgeId);

        // Assert
        assertTrue(result);
        verify(badgeRepository, times(1)).existsById(badgeId);
        verify(badgeRepository, times(1)).deleteById(badgeId);
    }

    @Test
    void testDeleteBadge_NotFound() {
        // Arrange
        String badgeId = "99";
        when(badgeRepository.existsById(badgeId)).thenReturn(false);

        // Act
        boolean result = badgeService.deleteBadge(badgeId);

        // Assert
        assertFalse(result);
        verify(badgeRepository, times(1)).existsById(badgeId);
        verify(badgeRepository, never()).deleteById(badgeId);
    }
}
