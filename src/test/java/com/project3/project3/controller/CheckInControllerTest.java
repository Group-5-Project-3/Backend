package com.project3.project3.controller;

import com.project3.project3.model.CheckIn;
import com.project3.project3.service.CheckInService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class CheckInControllerTest {

    @InjectMocks
    private CheckInController checkInController;

    @Mock
    private CheckInService checkInService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetUserCheckIns() {
        // Arrange
        String userId = "user123";
        CheckIn checkIn1 = new CheckIn("trail1", userId, "Trail Name 1", LocalDateTime.now());
        CheckIn checkIn2 = new CheckIn("trail2", userId, "Trail Name 2", LocalDateTime.now());
        when(checkInService.getCheckInsByUserId(userId)).thenReturn(Arrays.asList(checkIn1, checkIn2));

        // Act
        ResponseEntity<List<CheckIn>> response = checkInController.getUserCheckIns(userId);

        // Assert
        assertEquals(200, response.getStatusCode().value());
        assertEquals(2, response.getBody().size());
        verify(checkInService, times(1)).getCheckInsByUserId(userId);
    }

    @Test
    void testGetTrailCheckIns() {
        // Arrange
        String trailId = "trail123";
        CheckIn checkIn1 = new CheckIn(trailId, "user1", "Trail Name 1", LocalDateTime.now());
        CheckIn checkIn2 = new CheckIn(trailId, "user2", "Trail Name 2", LocalDateTime.now());
        when(checkInService.getCheckInsByTrailId(trailId)).thenReturn(Arrays.asList(checkIn1, checkIn2));

        // Act
        ResponseEntity<List<CheckIn>> response = checkInController.getTrailCheckIns(trailId);

        // Assert
        assertEquals(200, response.getStatusCode().value());
        assertEquals(2, response.getBody().size());
        verify(checkInService, times(1)).getCheckInsByTrailId(trailId);
    }

    @Test
    void testCreateCheckIn() {
        // Arrange
        CheckIn checkIn = new CheckIn("trail123", "user123", "Trail Name", LocalDateTime.now());
        when(checkInService.createCheckIn(checkIn)).thenReturn(checkIn);

        // Act
        ResponseEntity<CheckIn> response = checkInController.createCheckIn(checkIn);

        // Assert
        assertEquals(200, response.getStatusCode().value());
        assertEquals(checkIn, response.getBody());
        verify(checkInService, times(1)).createCheckIn(checkIn);
    }

    @Test
    void testDeleteCheckIn_Success() {
        // Arrange
        String checkInId = "checkIn123";
        when(checkInService.deleteCheckIn(checkInId)).thenReturn(true);

        // Act
        ResponseEntity<Void> response = checkInController.deleteCheckIn(checkInId);

        // Assert
        assertEquals(204, response.getStatusCode().value());
        verify(checkInService, times(1)).deleteCheckIn(checkInId);
    }

    @Test
    void testDeleteCheckIn_NotFound() {
        // Arrange
        String checkInId = "checkInNotFound";
        when(checkInService.deleteCheckIn(checkInId)).thenReturn(false);

        // Act
        ResponseEntity<Void> response = checkInController.deleteCheckIn(checkInId);

        // Assert
        assertEquals(404, response.getStatusCode().value());
        verify(checkInService, times(1)).deleteCheckIn(checkInId);
    }
}
