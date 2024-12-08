package com.project3.project3.service;

import com.project3.project3.model.TrailImage;
import com.project3.project3.repository.TrailImageRepository;
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

class TrailImageServiceTest {

    @InjectMocks
    private TrailImageService trailImageService;

    @Mock
    private TrailImageRepository trailImageRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetImagesByTrailId() {
        // Arrange
        String trailId = "trail123";
        List<TrailImage> images = Arrays.asList(new TrailImage(), new TrailImage());
        when(trailImageRepository.findByTrailId(trailId)).thenReturn(images);

        // Act
        List<TrailImage> result = trailImageService.getImagesByTrailId(trailId);

        // Assert
        assertEquals(2, result.size());
        verify(trailImageRepository, times(1)).findByTrailId(trailId);
    }

    @Test
    void testGetImagesByUserId() {
        // Arrange
        String userId = "user123";
        List<TrailImage> images = Arrays.asList(new TrailImage(), new TrailImage());
        when(trailImageRepository.findByUserId(userId)).thenReturn(images);

        // Act
        List<TrailImage> result = trailImageService.getImagesByUserId(userId);

        // Assert
        assertEquals(2, result.size());
        verify(trailImageRepository, times(1)).findByUserId(userId);
    }

    @Test
    void testSaveTrailImage() {
        // Arrange
        TrailImage trailImage = new TrailImage();
        trailImage.setImageUrl("http://example.com/image.jpg");
        trailImage.setTrailId("trail123");
        trailImage.setUserId("user123");
        trailImage.setDescription("Beautiful view");
        when(trailImageRepository.save(any(TrailImage.class))).thenReturn(trailImage);

        // Act
        TrailImage result = trailImageService.saveTrailImage("http://example.com/image.jpg", "trail123", "user123", "Beautiful view");

        // Assert
        assertEquals("http://example.com/image.jpg", result.getImageUrl());
        assertEquals("trail123", result.getTrailId());
        assertEquals("user123", result.getUserId());
        assertEquals("Beautiful view", result.getDescription());
        verify(trailImageRepository, times(1)).save(any(TrailImage.class));
    }

    @Test
    void testGetTrailImageById_Found() {
        // Arrange
        String id = "image123";
        TrailImage trailImage = new TrailImage();
        trailImage.setTrailId("trail123");
        when(trailImageRepository.findById(id)).thenReturn(Optional.of(trailImage));

        // Act
        Optional<TrailImage> result = trailImageService.getTrailImageById(id);

        // Assert
        assertTrue(result.isPresent());
        assertEquals("trail123", result.get().getTrailId());
        verify(trailImageRepository, times(1)).findById(id);
    }

    @Test
    void testGetTrailImageById_NotFound() {
        // Arrange
        String id = "nonexistent";
        when(trailImageRepository.findById(id)).thenReturn(Optional.empty());

        // Act
        Optional<TrailImage> result = trailImageService.getTrailImageById(id);

        // Assert
        assertFalse(result.isPresent());
        verify(trailImageRepository, times(1)).findById(id);
    }

    @Test
    void testDeleteTrailImage_Success() {
        // Arrange
        String id = "image123";
        when(trailImageRepository.existsById(id)).thenReturn(true);

        // Act
        boolean result = trailImageService.deleteTrailImage(id);

        // Assert
        assertTrue(result);
        verify(trailImageRepository, times(1)).existsById(id);
        verify(trailImageRepository, times(1)).deleteById(id);
    }

    @Test
    void testDeleteTrailImage_NotFound() {
        // Arrange
        String id = "nonexistent";
        when(trailImageRepository.existsById(id)).thenReturn(false);

        // Act
        boolean result = trailImageService.deleteTrailImage(id);

        // Assert
        assertFalse(result);
        verify(trailImageRepository, times(1)).existsById(id);
        verify(trailImageRepository, never()).deleteById(id);
    }
}
