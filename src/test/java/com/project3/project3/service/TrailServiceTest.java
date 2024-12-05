package com.project3.project3.service;

import com.project3.project3.model.Trail;
import com.project3.project3.repository.TrailRepository;
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

class TrailServiceTest {

    @InjectMocks
    private TrailService trailService;

    @Mock
    private TrailRepository trailRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllTrails() {
        // Arrange
        List<Trail> trails = Arrays.asList(new Trail(), new Trail());
        when(trailRepository.findAll()).thenReturn(trails);

        // Act
        List<Trail> result = trailService.getAllTrails();

        // Assert
        assertEquals(2, result.size());
        verify(trailRepository, times(1)).findAll();
    }

    @Test
    void testGetTrailById_Found() {
        // Arrange
        String id = "trail123";
        Trail trail = new Trail();
        trail.setTrailId(id);
        when(trailRepository.findById(id)).thenReturn(Optional.of(trail));

        // Act
        Optional<Trail> result = trailService.getTrailById(id);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(id, result.get().getTrailId());
        verify(trailRepository, times(1)).findById(id);
    }

    @Test
    void testGetTrailById_NotFound() {
        // Arrange
        String id = "nonexistent";
        when(trailRepository.findById(id)).thenReturn(Optional.empty());

        // Act
        Optional<Trail> result = trailService.getTrailById(id);

        // Assert
        assertFalse(result.isPresent());
        verify(trailRepository, times(1)).findById(id);
    }

    @Test
    void testGetTrailByPlacesId_Found() {
        // Arrange
        String placesId = "places123";
        Trail trail = new Trail();
        trail.setPlacesId(placesId);
        when(trailRepository.findByPlacesId(placesId)).thenReturn(Optional.of(trail));

        // Act
        Optional<Trail> result = trailService.getTrailByPlacesId(placesId);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(placesId, result.get().getPlacesId());
        verify(trailRepository, times(1)).findByPlacesId(placesId);
    }

    @Test
    void testGetTrailByPlacesId_NotFound() {
        // Arrange
        String placesId = "nonexistent";
        when(trailRepository.findByPlacesId(placesId)).thenReturn(Optional.empty());

        // Act
        Optional<Trail> result = trailService.getTrailByPlacesId(placesId);

        // Assert
        assertFalse(result.isPresent());
        verify(trailRepository, times(1)).findByPlacesId(placesId);
    }

    @Test
    void testCreateTrail() {
        // Arrange
        Trail trail = new Trail();
        trail.setName("New Trail");
        when(trailRepository.save(any(Trail.class))).thenReturn(trail);

        // Act
        Trail result = trailService.createTrail(trail);

        // Assert
        assertEquals("New Trail", result.getName());
        verify(trailRepository, times(1)).save(trail);
    }

    @Test
    void testUpdateTrail() {
        // Arrange
        String id = "trail123";
        Trail trail = new Trail();
        trail.setName("Updated Trail");
        when(trailRepository.save(any(Trail.class))).thenReturn(trail);

        // Act
        Trail result = trailService.updateTrail(id, trail);

        // Assert
        assertEquals("Updated Trail", result.getName());
        assertEquals(id, result.getTrailId());
        verify(trailRepository, times(1)).save(trail);
    }

    @Test
    void testDeleteTrail_Success() {
        // Arrange
        String id = "trail123";
        when(trailRepository.existsById(id)).thenReturn(true);

        // Act
        boolean result = trailService.deleteTrail(id);

        // Assert
        assertTrue(result);
        verify(trailRepository, times(1)).existsById(id);
        verify(trailRepository, times(1)).deleteById(id);
    }

    @Test
    void testDeleteTrail_NotFound() {
        // Arrange
        String id = "nonexistent";
        when(trailRepository.existsById(id)).thenReturn(false);

        // Act
        boolean result = trailService.deleteTrail(id);

        // Assert
        assertFalse(result);
        verify(trailRepository, times(1)).existsById(id);
        verify(trailRepository, never()).deleteById(id);
    }
}
