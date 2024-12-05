package com.project3.project3.service;

import com.project3.project3.model.CheckIn;
import com.project3.project3.repository.CheckInRepository;
import com.project3.project3.utility.CheckInEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.context.ApplicationEventPublisher;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CheckInServiceTest {

    @InjectMocks
    private CheckInService checkInService;

    @Mock
    private CheckInRepository checkInRepository;

    @Mock
    private MilestonesService milestonesService;

    @Mock
    private ApplicationEventPublisher applicationEventPublisher;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllCheckIns() {
        // Arrange
        List<CheckIn> checkIns = Arrays.asList(
                new CheckIn("trail1", "user1", "Trail Name 1", LocalDateTime.now()),
                new CheckIn("trail2", "user2", "Trail Name 2", LocalDateTime.now())
        );
        when(checkInRepository.findAll()).thenReturn(checkIns);

        // Act
        List<CheckIn> result = checkInService.getAllCheckIns();

        // Assert
        assertEquals(2, result.size());
        assertEquals("Trail Name 1", result.get(0).getName());
        verify(checkInRepository, times(1)).findAll();
    }

    @Test
    void testGetCheckInsByTrailId() {
        // Arrange
        String trailId = "trail123";
        List<CheckIn> checkIns = Arrays.asList(
                new CheckIn(trailId, "user1", "Trail Name 1", LocalDateTime.now()),
                new CheckIn(trailId, "user2", "Trail Name 2", LocalDateTime.now())
        );
        when(checkInRepository.findByTrailId(trailId)).thenReturn(checkIns);

        // Act
        List<CheckIn> result = checkInService.getCheckInsByTrailId(trailId);

        // Assert
        assertEquals(2, result.size());
        verify(checkInRepository, times(1)).findByTrailId(trailId);
    }

    @Test
    void testGetCheckInsByUserId() {
        // Arrange
        String userId = "user123";
        List<CheckIn> checkIns = Arrays.asList(
                new CheckIn("trail1", userId, "Trail Name 1", LocalDateTime.now()),
                new CheckIn("trail2", userId, "Trail Name 2", LocalDateTime.now())
        );
        when(checkInRepository.findByUserId(userId)).thenReturn(checkIns);

        // Act
        List<CheckIn> result = checkInService.getCheckInsByUserId(userId);

        // Assert
        assertEquals(2, result.size());
        verify(checkInRepository, times(1)).findByUserId(userId);
    }

    @Test
    void testCreateCheckIn() {
        // Arrange
        CheckIn checkIn = new CheckIn("trail123", "user123", "Trail Name", LocalDateTime.now());
        when(checkInRepository.save(any(CheckIn.class))).thenReturn(checkIn);

        // Act
        CheckIn result = checkInService.createCheckIn(checkIn);

        // Assert
        assertNotNull(result.getTimestamp());
        verify(milestonesService, times(1)).incrementNationalParksVisited("user123", "Trail Name");
        verify(applicationEventPublisher, times(1)).publishEvent(any(CheckInEvent.class));
        verify(checkInRepository, times(1)).save(checkIn);
    }

    @Test
    void testDeleteCheckIn_Success() {
        // Arrange
        String checkInId = "checkIn123";
        when(checkInRepository.existsById(checkInId)).thenReturn(true);

        // Act
        boolean result = checkInService.deleteCheckIn(checkInId);

        // Assert
        assertTrue(result);
        verify(checkInRepository, times(1)).existsById(checkInId);
        verify(checkInRepository, times(1)).deleteById(checkInId);
    }

    @Test
    void testDeleteCheckIn_NotFound() {
        // Arrange
        String checkInId = "checkInNotFound";
        when(checkInRepository.existsById(checkInId)).thenReturn(false);

        // Act
        boolean result = checkInService.deleteCheckIn(checkInId);

        // Assert
        assertFalse(result);
        verify(checkInRepository, times(1)).existsById(checkInId);
        verify(checkInRepository, never()).deleteById(checkInId);
    }
}
