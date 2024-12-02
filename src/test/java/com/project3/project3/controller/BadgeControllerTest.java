package com.project3.project3.controller;

import com.project3.project3.model.Badge;
import com.project3.project3.model.BadgeType;
import com.project3.project3.service.BadgeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BadgeControllerTest {

    @InjectMocks
    private BadgeController badgeController;

    @Mock
    private BadgeService badgeService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllBadges() {
        List<Badge> badges = new ArrayList<>();
        badges.add(new Badge("National Parks Badge", "Visit 5 national parks", BadgeType.NATIONAL_PARKS, "url1"));
        badges.add(new Badge("Distance Badge", "Hike 100 miles", BadgeType.DISTANCE, "url2"));

        when(badgeService.getAllBadges()).thenReturn(badges);

        ResponseEntity<List<Badge>> response = badgeController.getAllBadges();
        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
        verify(badgeService, times(1)).getAllBadges();
    }

    @Test
    void testGetBadgeById_Found() {
        Badge badge = new Badge("Elevation Badge", "Climb 10,000 feet", BadgeType.ELEVATION, "url3");
        when(badgeService.getBadgeById("1")).thenReturn(Optional.of(badge));

        ResponseEntity<Badge> response = badgeController.getBadgeById("1");
        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals("Elevation Badge", response.getBody().getName());
        verify(badgeService, times(1)).getBadgeById("1");
    }

    @Test
    void testGetBadgeById_NotFound() {
        when(badgeService.getBadgeById("99")).thenReturn(Optional.empty());

        ResponseEntity<Badge> response = badgeController.getBadgeById("99");
        assertEquals(404, response.getStatusCode().value());
        assertNull(response.getBody());
        verify(badgeService, times(1)).getBadgeById("99");
    }

    @Test
    void testCreateBadge() {
        Badge badge = new Badge("Total Hikes Badge", "Complete 50 hikes", BadgeType.TOTAL_HIKES, "url4");
        when(badgeService.createBadge(badge)).thenReturn(badge);

        ResponseEntity<Badge> response = badgeController.createBadge(badge);
        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals("Total Hikes Badge", response.getBody().getName());
        verify(badgeService, times(1)).createBadge(badge);
    }

    @Test
    void testDeleteBadge_Success() {
        when(badgeService.deleteBadge("1")).thenReturn(true);

        ResponseEntity<Void> response = badgeController.deleteBadge("1");
        assertEquals(204, response.getStatusCode().value());
        verify(badgeService, times(1)).deleteBadge("1");
    }

    @Test
    void testDeleteBadge_NotFound() {
        when(badgeService.deleteBadge("99")).thenReturn(false);

        ResponseEntity<Void> response = badgeController.deleteBadge("99");
        assertEquals(404, response.getStatusCode().value());
        verify(badgeService, times(1)).deleteBadge("99");
    }
}
