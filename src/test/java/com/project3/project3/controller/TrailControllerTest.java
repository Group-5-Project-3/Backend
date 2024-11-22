package com.project3.project3.controller;

import com.project3.project3.model.Trail;
import com.project3.project3.service.TrailService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class TrailControllerTest {

    @InjectMocks
    private TrailController trailController;

    @Mock
    private TrailService trailService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllTrails() {
        // Arrange
        Trail trail1 = new Trail("places1", "Trail 1", "Location 1", "Description 1", null);
        trail1.setTrailId("trail1");
        Trail trail2 = new Trail("places2", "Trail 2", "Location 2", "Description 2", null);
        trail2.setTrailId("trail2");
        when(trailService.getAllTrails()).thenReturn(Arrays.asList(trail1, trail2));

        // Act
        ResponseEntity<List<Trail>> response = trailController.getAllTrails();

        // Assert
        assertEquals(200, response.getStatusCode().value());
        assertEquals(2, response.getBody().size());
        verify(trailService, times(1)).getAllTrails();
    }

    @Test
    void testGetTrailById_Success() {
        // Arrange
        String trailId = "trail123";
        Trail trail = new Trail("places1", "Trail Name", "Location", "Description", null);
        trail.setTrailId(trailId);
        when(trailService.getTrailById(trailId)).thenReturn(Optional.of(trail));

        // Act
        ResponseEntity<Trail> response = trailController.getTrailById(trailId);

        // Assert
        assertEquals(200, response.getStatusCode().value());
        assertEquals("Trail Name", response.getBody().getName());
        verify(trailService, times(1)).getTrailById(trailId);
    }

    @Test
    void testGetTrailById_NotFound() {
        // Arrange
        String trailId = "nonexistent";
        when(trailService.getTrailById(trailId)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<Trail> response = trailController.getTrailById(trailId);

        // Assert
        assertEquals(404, response.getStatusCode().value());
        verify(trailService, times(1)).getTrailById(trailId);
    }

    @Test
    void testGetTrailByPlacesId_Success() {
        // Arrange
        String placesId = "places1";
        Trail trail = new Trail(placesId, "Trail Name", "Location", "Description", null);
        trail.setTrailId("trail1");
        when(trailService.getTrailByPlacesId(placesId)).thenReturn(Optional.of(trail));

        // Act
        ResponseEntity<Trail> response = trailController.getTrailByPlacesId(placesId);

        // Assert
        assertEquals(200, response.getStatusCode().value());
        assertEquals(placesId, response.getBody().getPlacesId());
        verify(trailService, times(1)).getTrailByPlacesId(placesId);
    }

    @Test
    void testGetTrailByPlacesId_NotFound() {
        // Arrange
        String placesId = "nonexistent";
        when(trailService.getTrailByPlacesId(placesId)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<Trail> response = trailController.getTrailByPlacesId(placesId);

        // Assert
        assertEquals(404, response.getStatusCode().value());
        verify(trailService, times(1)).getTrailByPlacesId(placesId);
    }

    @Test
    void testCreateTrail() {
        // Arrange
        Trail trail = new Trail("places1", "Trail Name", "Location", "Description", null);
        trail.setTrailId("trail1");
        when(trailService.createTrail(trail)).thenReturn(trail);

        // Act
        ResponseEntity<Trail> response = trailController.createTrail(trail);

        // Assert
        assertEquals(200, response.getStatusCode().value());
        assertEquals("Trail Name", response.getBody().getName());
        verify(trailService, times(1)).createTrail(trail);
    }

    @Test
    void testUpdateTrail() {
        // Arrange
        String trailId = "trail123";
        Trail updatedTrail = new Trail("places1", "Updated Trail", "Updated Location", "Updated Description", null);
        updatedTrail.setTrailId(trailId);
        when(trailService.updateTrail(trailId, updatedTrail)).thenReturn(updatedTrail);

        // Act
        ResponseEntity<Trail> response = trailController.updateTrail(trailId, updatedTrail);

        // Assert
        assertEquals(200, response.getStatusCode().value());
        assertEquals("Updated Trail", response.getBody().getName());
        verify(trailService, times(1)).updateTrail(trailId, updatedTrail);
    }

    @Test
    void testDeleteTrail_Success() {
        // Arrange
        String trailId = "trail123";
        when(trailService.deleteTrail(trailId)).thenReturn(true);

        // Act
        ResponseEntity<Void> response = trailController.deleteTrail(trailId);

        // Assert
        assertEquals(204, response.getStatusCode().value());
        verify(trailService, times(1)).deleteTrail(trailId);
    }

    @Test
    void testDeleteTrail_NotFound() {
        // Arrange
        String trailId = "nonexistent";
        when(trailService.deleteTrail(trailId)).thenReturn(false);

        // Act
        ResponseEntity<Void> response = trailController.deleteTrail(trailId);

        // Assert
        assertEquals(404, response.getStatusCode().value());
        verify(trailService, times(1)).deleteTrail(trailId);
    }
}
