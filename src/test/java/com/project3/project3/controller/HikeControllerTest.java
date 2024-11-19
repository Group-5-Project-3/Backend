package com.project3.project3.controller;

import com.project3.project3.model.Hike;
import com.project3.project3.service.HikeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class HikeControllerTest {

    @Mock
    private HikeService hikeService;

    @InjectMocks
    private HikeController hikeController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testStartHike() {
        // Given
        Hike hike = new Hike("user123", "trail123", "polyline123",
                LocalDateTime.now(), null, null, null);
        when(hikeService.startHike(hike)).thenReturn(hike);

        // When
        ResponseEntity<Hike> response = hikeController.startHike(hike);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(hike, response.getBody());
        verify(hikeService, times(1)).startHike(hike);
    }

    @Test
    void testCompleteHike() {
        // Given
        String hikeId = "hike123";
        double distance = 5.0;
        double elevationGain = 500.0;
        String polyline = "polyline123";
        Hike completedHike = new Hike("user123", "trail123", polyline,
                LocalDateTime.now(), LocalDateTime.now(), distance, elevationGain);

        when(hikeService.completeHike(hikeId, distance, elevationGain, polyline))
                .thenReturn(Optional.of(completedHike));

        // When
        ResponseEntity<Hike> response = hikeController.completeHike(hikeId, distance, elevationGain, polyline);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(completedHike, response.getBody());
        verify(hikeService, times(1)).completeHike(hikeId, distance, elevationGain, polyline);
    }

    @Test
    void testGetHikesByUserId() {
        // Given
        String userId = "user123";
        Hike hike1 = new Hike(userId, "trail1", "polyline1", LocalDateTime.now(), null, null, null);
        Hike hike2 = new Hike(userId, "trail2", "polyline2", LocalDateTime.now(), null, null, null);

        when(hikeService.getHikesByUserId(userId)).thenReturn(Arrays.asList(hike1, hike2));

        // When
        ResponseEntity<List<Hike>> response = hikeController.getHikesByUserId(userId);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
        verify(hikeService, times(1)).getHikesByUserId(userId);
    }

    @Test
    void testGetHikesByTrailId() {
        // Given
        String trailId = "trail123";
        Hike hike1 = new Hike("user1", trailId, "polyline1", LocalDateTime.now(), null, null, null);
        Hike hike2 = new Hike("user2", trailId, "polyline2", LocalDateTime.now(), null, null, null);

        when(hikeService.getHikesByTrailId(trailId)).thenReturn(Arrays.asList(hike1, hike2));

        // When
        ResponseEntity<List<Hike>> response = hikeController.getHikesByTrailId(trailId);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
        verify(hikeService, times(1)).getHikesByTrailId(trailId);
    }

    @Test
    void testGetHikeById() {
        // Given
        String hikeId = "hike123";
        Hike hike = new Hike("user123", "trail123", "polyline123", LocalDateTime.now(), null, null, null);

        when(hikeService.getHikeById(hikeId)).thenReturn(Optional.of(hike));

        // When
        ResponseEntity<Hike> response = hikeController.getHikeById(hikeId);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(hike, response.getBody());
        verify(hikeService, times(1)).getHikeById(hikeId);
    }

    @Test
    void testUpdateHike() {
        // Given
        String hikeId = "hike123";
        Hike updatedHike = new Hike("user123", "trail123", "updatedPolyline",
                LocalDateTime.now(), LocalDateTime.now(), 6.0, 600.0);

        when(hikeService.updateHike(hikeId, updatedHike)).thenReturn(Optional.of(updatedHike));

        // When
        ResponseEntity<Hike> response = hikeController.updateHike(hikeId, updatedHike);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updatedHike, response.getBody());
        verify(hikeService, times(1)).updateHike(hikeId, updatedHike);
    }

    @Test
    void testDeleteHike() {
        // Given
        String hikeId = "hike123";

        when(hikeService.deleteHikeById(hikeId)).thenReturn(true);

        // When
        ResponseEntity<Void> response = hikeController.deleteHike(hikeId);

        // Then
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(hikeService, times(1)).deleteHikeById(hikeId);
    }
}
