package com.project3.project3.controller;

import com.project3.project3.model.FavoriteTrail;
import com.project3.project3.service.FavoriteTrailService;
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

class FavoriteTrailControllerTest {

    @InjectMocks
    private FavoriteTrailController favoriteTrailController;

    @Mock
    private FavoriteTrailService favoriteTrailService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetUserFavorites() {
        // Arrange
        String userId = "user123";
        FavoriteTrail favorite1 = new FavoriteTrail(userId, "trail1", LocalDateTime.now());
        FavoriteTrail favorite2 = new FavoriteTrail(userId, "trail2", LocalDateTime.now());
        when(favoriteTrailService.getFavoritesByUserId(userId)).thenReturn(Arrays.asList(favorite1, favorite2));

        // Act
        ResponseEntity<List<FavoriteTrail>> response = favoriteTrailController.getUserFavorites(userId);

        // Assert
        assertEquals(200, response.getStatusCode().value());
        assertEquals(2, response.getBody().size());
        verify(favoriteTrailService, times(1)).getFavoritesByUserId(userId);
    }

    @Test
    void testAddFavoriteTrail() {
        // Arrange
        String userId = "user123";
        String trailId = "trail123";
        FavoriteTrail favoriteTrail = new FavoriteTrail(userId, trailId, LocalDateTime.now());
        when(favoriteTrailService.addFavoriteTrail(any(FavoriteTrail.class))).thenReturn(favoriteTrail);

        // Act
        ResponseEntity<FavoriteTrail> response = favoriteTrailController.addFavoriteTrail(userId, trailId);

        // Assert
        assertEquals(200, response.getStatusCode().value());
        assertEquals(userId, response.getBody().getUserId());
        assertEquals(trailId, response.getBody().getTrailId());
        verify(favoriteTrailService, times(1)).addFavoriteTrail(any(FavoriteTrail.class));
    }

    @Test
    void testRemoveFavoriteTrail_Success() {
        // Arrange
        String favoriteId = "favorite123";
        when(favoriteTrailService.removeFavoriteTrail(favoriteId)).thenReturn(true);

        // Act
        ResponseEntity<Void> response = favoriteTrailController.removeFavoriteTrail(favoriteId);

        // Assert
        assertEquals(204, response.getStatusCode().value());
        verify(favoriteTrailService, times(1)).removeFavoriteTrail(favoriteId);
    }

    @Test
    void testRemoveFavoriteTrail_NotFound() {
        // Arrange
        String favoriteId = "nonexistent";
        when(favoriteTrailService.removeFavoriteTrail(favoriteId)).thenReturn(false);

        // Act
        ResponseEntity<Void> response = favoriteTrailController.removeFavoriteTrail(favoriteId);

        // Assert
        assertEquals(404, response.getStatusCode().value());
        verify(favoriteTrailService, times(1)).removeFavoriteTrail(favoriteId);
    }
}
