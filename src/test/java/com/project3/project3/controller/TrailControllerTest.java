package com.project3.project3.controller;

import com.project3.project3.model.Trail;
import com.project3.project3.service.TrailService;
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

public class TrailControllerTest {

    @Mock
    private TrailService trailService;

    @InjectMocks
    private TrailController trailController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllTrails() {
        Trail trail1 = new Trail("Trail 1", "Location 1", "Easy", "Description 1", 4.5);
        Trail trail2 = new Trail("Trail 2", "Location 2", "Moderate", "Description 2", 5.0);

        when(trailService.getAllTrails()).thenReturn(Arrays.asList(trail1, trail2));

        ResponseEntity<List<Trail>> response = trailController.getAllTrails();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
        verify(trailService, times(1)).getAllTrails();
    }

    @Test
    void testGetTrailById() {
        String trailId = "trail123";
        Trail trail = new Trail("Trail Name", "Location", "Hard", "Description", 4.8);

        when(trailService.getTrailById(trailId)).thenReturn(Optional.of(trail));

        ResponseEntity<Trail> response = trailController.getTrailById(trailId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(trail, response.getBody());
        verify(trailService, times(1)).getTrailById(trailId);
    }

    @Test
    void testCreateTrail() {
        Trail trail = new Trail("Trail Name", "Location", "Hard", "Description", 4.8);

        when(trailService.createTrail(trail)).thenReturn(trail);

        ResponseEntity<Trail> response = trailController.createTrail(trail);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(trail, response.getBody());
        verify(trailService, times(1)).createTrail(trail);
    }

    @Test
    void testUpdateTrail() {
        String trailId = "trail123";
        Trail updatedTrail = new Trail("Updated Trail", "Updated Location", "Easy", "Updated Description", 4.0);

        when(trailService.updateTrail(trailId, updatedTrail)).thenReturn(updatedTrail);

        ResponseEntity<Trail> response = trailController.updateTrail(trailId, updatedTrail);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updatedTrail, response.getBody());
        verify(trailService, times(1)).updateTrail(trailId, updatedTrail);
    }

    @Test
    void testDeleteTrail() {
        String trailId = "trail123";

        doNothing().when(trailService).deleteTrail(trailId);

        ResponseEntity<Void> response = trailController.deleteTrail(trailId);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(trailService, times(1)).deleteTrail(trailId);
    }
}
