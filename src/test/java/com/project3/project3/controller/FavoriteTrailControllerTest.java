package com.project3.project3.controller;

import com.project3.project3.model.FavoriteTrail;
import com.project3.project3.service.FavoriteTrailService;
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

public class FavoriteTrailControllerTest {

    @Mock
    private FavoriteTrailService favoriteTrailService;

    @InjectMocks
    private FavoriteTrailController favoriteTrailController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetUserFavorites() {
        // Given
        String userId = "user123";
        FavoriteTrail trail1 = new FavoriteTrail(userId, "trail1", LocalDateTime.now());
        FavoriteTrail trail2 = new FavoriteTrail(userId, "trail2", LocalDateTime.now());

        when(favoriteTrailService.getFavoritesByUserId(userId)).thenReturn(Arrays.asList(trail1, trail2));

        // When
        ResponseEntity<List<FavoriteTrail>> response = favoriteTrailController.getUserFavorites(userId);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
        verify(favoriteTrailService, times(1)).getFavoritesByUserId(userId);
    }

    @Test
    void testAddFavoriteTrail() {
        // Given
        String userId = "user123";
        String trailId = "trail123";
        FavoriteTrail expectedFavorite = new FavoriteTrail(userId, trailId, LocalDateTime.now());

        // Mocking the service call
        when(favoriteTrailService.addFavoriteTrail(any(FavoriteTrail.class))).thenReturn(expectedFavorite);

        // When
        ResponseEntity<FavoriteTrail> response = favoriteTrailController.addFavoriteTrail(userId, trailId);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedFavorite.getUserId(), response.getBody().getUserId());
        assertEquals(expectedFavorite.getTrailId(), response.getBody().getTrailId());
        verify(favoriteTrailService, times(1)).addFavoriteTrail(any(FavoriteTrail.class));
    }

    @Test
    void testRemoveFavoriteTrail() {
        // Given
        String favoriteTrailId = "favTrail123";

        doNothing().when(favoriteTrailService).removeFavoriteTrail(favoriteTrailId);

        // When
        ResponseEntity<Void> response = favoriteTrailController.removeFavoriteTrail(favoriteTrailId);

        // Then
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(favoriteTrailService, times(1)).removeFavoriteTrail(favoriteTrailId);
    }
}
