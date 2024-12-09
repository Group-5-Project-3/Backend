package com.project3.project3.service;

import com.project3.project3.model.FavoriteTrail;
import com.project3.project3.repository.FavoriteTrailRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FavoriteTrailServiceTest {

    @InjectMocks
    private FavoriteTrailService favoriteTrailService;

    @Mock
    private FavoriteTrailRepository favoriteTrailRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetFavoritesByUserId() {
        // Arrange
        String userId = "user123";
        List<FavoriteTrail> favorites = Arrays.asList(
                new FavoriteTrail(userId, "trail1", LocalDateTime.now()),
                new FavoriteTrail(userId, "trail2", LocalDateTime.now())
        );
        when(favoriteTrailRepository.findByUserId(userId)).thenReturn(favorites);

        // Act
        List<FavoriteTrail> result = favoriteTrailService.getFavoritesByUserId(userId);

        // Assert
        assertEquals(2, result.size());
        assertEquals("trail1", result.get(0).getTrailId());
        assertEquals("trail2", result.get(1).getTrailId());
        verify(favoriteTrailRepository, times(1)).findByUserId(userId);
    }

    @Test
    void testAddFavoriteTrail() {
        // Arrange
        FavoriteTrail favoriteTrail = new FavoriteTrail("user123", "trail123", null);
        FavoriteTrail savedFavorite = new FavoriteTrail("user123", "trail123", LocalDateTime.now());
        when(favoriteTrailRepository.save(any(FavoriteTrail.class))).thenReturn(savedFavorite);

        // Act
        FavoriteTrail result = favoriteTrailService.addFavoriteTrail(favoriteTrail);

        // Assert
        assertNotNull(result.getFavoritedTimestamp());
        assertEquals("trail123", result.getTrailId());
        verify(favoriteTrailRepository, times(1)).save(favoriteTrail);
    }

    @Test
    void testRemoveFavoriteTrail_Success() {
        // Arrange
        String favoriteId = "favorite123";
        when(favoriteTrailRepository.existsById(favoriteId)).thenReturn(true);

        // Act
        boolean result = favoriteTrailService.removeFavoriteTrail(favoriteId);

        // Assert
        assertTrue(result);
        verify(favoriteTrailRepository, times(1)).existsById(favoriteId);
        verify(favoriteTrailRepository, times(1)).deleteById(favoriteId);
    }

    @Test
    void testRemoveFavoriteTrail_NotFound() {
        // Arrange
        String favoriteId = "nonexistent";
        when(favoriteTrailRepository.existsById(favoriteId)).thenReturn(false);

        // Act
        boolean result = favoriteTrailService.removeFavoriteTrail(favoriteId);

        // Assert
        assertFalse(result);
        verify(favoriteTrailRepository, times(1)).existsById(favoriteId);
        verify(favoriteTrailRepository, never()).deleteById(favoriteId);
    }
}
