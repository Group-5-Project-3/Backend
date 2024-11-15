package com.project3.project3.controller;

import com.project3.project3.model.Badge;
import com.project3.project3.model.BadgeType;
import com.project3.project3.service.BadgeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class BadgeControllerTest {

    @Mock
    private BadgeService badgeService;

    @InjectMocks
    private BadgeController badgeController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllBadges() {
        Badge badge1 = new Badge("Trail Explorer", "Completed 5 trails", BadgeType.TRAIL_MASTERY, "http://example.com/trail_explorer.png");
        Badge badge2 = new Badge("Park Visitor", "Visited a National Park", BadgeType.NATIONAL_PARKS, "http://example.com/park_visitor.png");

        when(badgeService.getAllBadges()).thenReturn(Arrays.asList(badge1, badge2));

        ResponseEntity<List<Badge>> response = badgeController.getAllBadges();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
        verify(badgeService, times(1)).getAllBadges();
    }

    @Test
    void testGetBadgeById() {
        String badgeId = "123";
        Badge badge = new Badge("Milestone Achiever", "Reached 100 miles", BadgeType.MILESTONE, "http://example.com/milestone_achiever.png");

        when(badgeService.getBadgeById(badgeId)).thenReturn(Optional.of(badge));

        ResponseEntity<Badge> response = badgeController.getBadgeById(badgeId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(badge, response.getBody());
        verify(badgeService, times(1)).getBadgeById(badgeId);
    }

    @Test
    void testCreateBadge() {
        Badge badge = new Badge("Event Participant", "Participated in a special event", BadgeType.SPECIAL_EVENTS, "http://example.com/event_participant.png");

        when(badgeService.createBadge(badge)).thenReturn(badge);

        ResponseEntity<Badge> response = badgeController.createBadge(badge);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(badge, response.getBody());
        verify(badgeService, times(1)).createBadge(badge);
    }

    @Test
    void testDeleteBadge() {
        String badgeId = "123";
        when(badgeService.deleteBadge(badgeId)).thenReturn(true);

        ResponseEntity<Void> response = badgeController.deleteBadge(badgeId);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(badgeService, times(1)).deleteBadge(badgeId);
    }

    @Test
    void testDeleteBadgeNotFound() {
        String badgeId = "123";
        when(badgeService.deleteBadge(badgeId)).thenReturn(false);

        ResponseEntity<Void> response = badgeController.deleteBadge(badgeId);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(badgeService, times(1)).deleteBadge(badgeId);
    }
}
