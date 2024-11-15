package com.project3.project3.controller;

import com.project3.project3.model.CheckIn;
import com.project3.project3.service.CheckInService;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class CheckInControllerTest {

    @Mock
    private CheckInService checkInService;

    @InjectMocks
    private CheckInController checkInController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetUserCheckIns() {
        String userId = "user123";
        CheckIn checkIn1 = new CheckIn("trail1", userId, LocalDateTime.now());
        CheckIn checkIn2 = new CheckIn("trail2", userId, LocalDateTime.now());

        when(checkInService.getCheckInsByUserId(userId)).thenReturn(Arrays.asList(checkIn1, checkIn2));

        ResponseEntity<List<CheckIn>> response = checkInController.getUserCheckIns(userId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
        verify(checkInService, times(1)).getCheckInsByUserId(userId);
    }

    @Test
    void testGetTrailCheckIns() {
        String trailId = "trail123";
        CheckIn checkIn1 = new CheckIn(trailId, "user1", LocalDateTime.now());
        CheckIn checkIn2 = new CheckIn(trailId, "user2", LocalDateTime.now());

        when(checkInService.getCheckInsByTrailId(trailId)).thenReturn(Arrays.asList(checkIn1, checkIn2));

        ResponseEntity<List<CheckIn>> response = checkInController.getTrailCheckIns(trailId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
        verify(checkInService, times(1)).getCheckInsByTrailId(trailId);
    }

    @Test
    void testCreateCheckIn() {
        CheckIn newCheckIn = new CheckIn("trail123", "user123", LocalDateTime.now());

        when(checkInService.createCheckIn(newCheckIn)).thenReturn(newCheckIn);

        ResponseEntity<CheckIn> response = checkInController.createCheckIn(newCheckIn);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(newCheckIn, response.getBody());
        verify(checkInService, times(1)).createCheckIn(newCheckIn);
    }

    @Test
    void testDeleteCheckIn() {
        String checkInId = "checkIn123";

        doNothing().when(checkInService).deleteCheckIn(checkInId);

        ResponseEntity<Void> response = checkInController.deleteCheckIn(checkInId);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(checkInService, times(1)).deleteCheckIn(checkInId);
    }
}
