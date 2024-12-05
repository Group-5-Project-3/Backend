package com.project3.project3.controller;

import com.project3.project3.model.Hike;
import com.project3.project3.service.HikeService;
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

class HikeControllerTest {

    @InjectMocks
    private HikeController hikeController;

    @Mock
    private HikeService hikeService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testStartHike() {
        // Arrange
        Hike hike = new Hike("user123", "trail123", null, LocalDateTime.now(), null, null, null);
        when(hikeService.startHike(hike)).thenReturn(hike);

        // Act
        ResponseEntity<Hike> response = hikeController.startHike(hike);

        // Assert
        assertEquals(200, response.getStatusCode().value());
        assertEquals("user123", response.getBody().getUserId());
        verify(hikeService, times(1)).startHike(hike);
    }

    @Test
    void testCompleteHike_Success() {
        // Arrange
        String hikeId = "hike123";
        Double distance = 5.0;
        Double elevationGain = 200.0;
        List<List<Double>> coordinates = Arrays.asList(Arrays.asList(36.0, -121.0), Arrays.asList(36.1, -121.1));
        Hike completedHike = new Hike("user123", "trail123", "encodedPolyline", LocalDateTime.now(), LocalDateTime.now(), distance, elevationGain);
        when(hikeService.completeHike(hikeId, distance, elevationGain, coordinates)).thenReturn(Optional.of(completedHike));

        // Act
        ResponseEntity<Hike> response = hikeController.completeHike(hikeId, distance, elevationGain, coordinates);

        // Assert
        assertEquals(200, response.getStatusCode().value());
        assertEquals(distance, response.getBody().getDistance());
        verify(hikeService, times(1)).completeHike(hikeId, distance, elevationGain, coordinates);
    }

    @Test
    void testCompleteHike_NotFound() {
        // Arrange
        String hikeId = "nonexistent";
        when(hikeService.completeHike(hikeId, 5.0, 200.0, null)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<Hike> response = hikeController.completeHike(hikeId, 5.0, 200.0, null);

        // Assert
        assertEquals(404, response.getStatusCode().value());
        verify(hikeService, times(1)).completeHike(hikeId, 5.0, 200.0, null);
    }

    @Test
    void testGetHikesByUserId() {
        // Arrange
        String userId = "user123";
        List<Hike> hikes = Arrays.asList(new Hike(), new Hike());
        when(hikeService.getHikesByUserId(userId)).thenReturn(hikes);

        // Act
        ResponseEntity<List<Hike>> response = hikeController.getHikesByUserId(userId);

        // Assert
        assertEquals(200, response.getStatusCode().value());
        assertEquals(2, response.getBody().size());
        verify(hikeService, times(1)).getHikesByUserId(userId);
    }

    @Test
    void testDeleteHike_Success() {
        // Arrange
        String hikeId = "hike123";
        when(hikeService.deleteHikeById(hikeId)).thenReturn(true);

        // Act
        ResponseEntity<Void> response = hikeController.deleteHike(hikeId);

        // Assert
        assertEquals(204, response.getStatusCode().value());
        verify(hikeService, times(1)).deleteHikeById(hikeId);
    }

    @Test
    void testDeleteHike_NotFound() {
        // Arrange
        String hikeId = "nonexistent";
        when(hikeService.deleteHikeById(hikeId)).thenReturn(false);

        // Act
        ResponseEntity<Void> response = hikeController.deleteHike(hikeId);

        // Assert
        assertEquals(404, response.getStatusCode().value());
        verify(hikeService, times(1)).deleteHikeById(hikeId);
    }
}
