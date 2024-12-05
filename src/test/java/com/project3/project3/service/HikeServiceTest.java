package com.project3.project3.service;

import com.project3.project3.model.Hike;
import com.project3.project3.repository.HikeRepository;
import com.project3.project3.utility.HikeEvent;
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

class HikeServiceTest {

    @InjectMocks
    private HikeService hikeService;

    @Mock
    private HikeRepository hikeRepository;

    @Mock
    private MilestonesService milestonesService;

    @Mock
    private ApplicationEventPublisher applicationEventPublisher;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testStartHike() {
        // Arrange
        Hike hike = new Hike();
        hike.setUserId("user123");
        when(hikeRepository.save(any(Hike.class))).thenReturn(hike);

        // Act
        Hike result = hikeService.startHike(hike);

        // Assert
        assertNotNull(result.getStartTime());
        verify(milestonesService, times(1)).incrementTotalHikes("user123");
        verify(hikeRepository, times(1)).save(hike);
    }

    @Test
    void testCompleteHike_Success() {
        // Arrange
        String hikeId = "hike123";
        Hike existingHike = new Hike();
        existingHike.setUserId("user123");
        List<List<Double>> coordinates = Arrays.asList(
                Arrays.asList(37.7749, -122.4194),
                Arrays.asList(34.0522, -118.2437)
        );
        when(hikeRepository.findById(hikeId)).thenReturn(Optional.of(existingHike));
        when(hikeRepository.save(any(Hike.class))).thenReturn(existingHike);

        // Act
        Optional<Hike> result = hikeService.completeHike(hikeId, 5.0, 500.0, coordinates);

        // Assert
        assertTrue(result.isPresent());
        assertNotNull(result.get().getEndTime());
        verify(milestonesService, times(1)).incrementDistance("user123", 5.0);
        verify(milestonesService, times(1)).incrementElevationGain("user123", 500.0);
        verify(applicationEventPublisher, times(1)).publishEvent(any(HikeEvent.class));
        verify(hikeRepository, times(1)).save(existingHike);
    }

    @Test
    void testCompleteHike_NotFound() {
        // Arrange
        String hikeId = "nonexistent";
        List<List<Double>> coordinates = Arrays.asList(
                Arrays.asList(37.7749, -122.4194),
                Arrays.asList(34.0522, -118.2437)
        );
        when(hikeRepository.findById(hikeId)).thenReturn(Optional.empty());

        // Act
        Optional<Hike> result = hikeService.completeHike(hikeId, 5.0, 500.0, coordinates);

        // Assert
        assertFalse(result.isPresent());
        verify(hikeRepository, times(1)).findById(hikeId);
        verifyNoMoreInteractions(hikeRepository, milestonesService, applicationEventPublisher);
    }

    @Test
    void testGetHikesByUserId() {
        // Arrange
        String userId = "user123";
        List<Hike> hikes = Arrays.asList(new Hike(), new Hike());
        when(hikeRepository.findByUserId(userId)).thenReturn(hikes);

        // Act
        List<Hike> result = hikeService.getHikesByUserId(userId);

        // Assert
        assertEquals(2, result.size());
        verify(hikeRepository, times(1)).findByUserId(userId);
    }

    @Test
    void testGetHikeById_Success() {
        // Arrange
        String hikeId = "hike123";
        Hike hike = new Hike();
        when(hikeRepository.findById(hikeId)).thenReturn(Optional.of(hike));

        // Act
        Optional<Hike> result = hikeService.getHikeById(hikeId);

        // Assert
        assertTrue(result.isPresent());
        verify(hikeRepository, times(1)).findById(hikeId);
    }

    @Test
    void testDeleteHikeById_Success() {
        // Arrange
        String hikeId = "hike123";
        when(hikeRepository.existsById(hikeId)).thenReturn(true);

        // Act
        boolean result = hikeService.deleteHikeById(hikeId);

        // Assert
        assertTrue(result);
        verify(hikeRepository, times(1)).existsById(hikeId);
        verify(hikeRepository, times(1)).deleteById(hikeId);
    }

    @Test
    void testDeleteHikeById_NotFound() {
        // Arrange
        String hikeId = "nonexistent";
        when(hikeRepository.existsById(hikeId)).thenReturn(false);

        // Act
        boolean result = hikeService.deleteHikeById(hikeId);

        // Assert
        assertFalse(result);
        verify(hikeRepository, times(1)).existsById(hikeId);
        verify(hikeRepository, never()).deleteById(hikeId);
    }
}
