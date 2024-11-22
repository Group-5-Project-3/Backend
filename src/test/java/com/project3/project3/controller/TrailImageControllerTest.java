package com.project3.project3.controller;

import com.project3.project3.model.TrailImage;
import com.project3.project3.service.ImageService;
import com.project3.project3.service.TrailImageService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class TrailImageControllerTest {

    @InjectMocks
    private TrailImageController trailImageController;

    @Mock
    private TrailImageService trailImageService;

    @Mock
    private ImageService imageService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetImagesByTrailId() {
        // Arrange
        String trailId = "trail123";
        TrailImage image1 = new TrailImage(trailId, "imageUrl1", "user1", "Description 1");
        TrailImage image2 = new TrailImage(trailId, "imageUrl2", "user2", "Description 2");
        when(trailImageService.getImagesByTrailId(trailId)).thenReturn(Arrays.asList(image1, image2));

        // Act
        ResponseEntity<List<TrailImage>> response = trailImageController.getImagesByTrailId(trailId);

        // Assert
        assertEquals(200, response.getStatusCode().value());
        assertEquals(2, response.getBody().size());
        verify(trailImageService, times(1)).getImagesByTrailId(trailId);
    }

    @Test
    void testGetImagesByUserId() {
        // Arrange
        String userId = "user123";
        TrailImage image1 = new TrailImage("trail1", "imageUrl1", userId, "Description 1");
        TrailImage image2 = new TrailImage("trail2", "imageUrl2", userId, "Description 2");
        when(trailImageService.getImagesByUserId(userId)).thenReturn(Arrays.asList(image1, image2));

        // Act
        ResponseEntity<List<TrailImage>> response = trailImageController.getImagesByUserId(userId);

        // Assert
        assertEquals(200, response.getStatusCode().value());
        assertEquals(2, response.getBody().size());
        verify(trailImageService, times(1)).getImagesByUserId(userId);
    }

    @Test
    void testUploadTrailImage_Success() throws Exception {
        // Arrange
        MultipartFile file = mock(MultipartFile.class);
        String trailId = "trail123";
        String userId = "user123";
        String description = "A beautiful trail";
        String imageUrl = "https://example.com/image.jpg";
        TrailImage trailImage = new TrailImage(trailId, imageUrl, userId, description);

        when(imageService.uploadImage(file, System.getenv("BUCKET_NAME"), System.getenv("TRAIL_PIC_FOLDER")))
                .thenReturn(imageUrl);
        when(trailImageService.saveTrailImage(imageUrl, trailId, userId, description))
                .thenReturn(trailImage);

        // Act
        ResponseEntity<TrailImage> response = trailImageController.uploadTrailImage(file, trailId, userId, description);

        // Assert
        assertEquals(200, response.getStatusCode().value());
        assertEquals(imageUrl, response.getBody().getImageUrl());
        verify(imageService, times(1)).uploadImage(file, System.getenv("BUCKET_NAME"), System.getenv("TRAIL_PIC_FOLDER"));
        verify(trailImageService, times(1)).saveTrailImage(imageUrl, trailId, userId, description);
    }

    @Test
    void testUploadTrailImage_Failure() throws Exception {
        // Arrange
        MultipartFile file = mock(MultipartFile.class);
        String trailId = "trail123";
        String userId = "user123";
        String description = "A beautiful trail";

        when(imageService.uploadImage(file, System.getenv("BUCKET_NAME"), System.getenv("TRAIL_PIC_FOLDER")))
                .thenThrow(new RuntimeException("Upload failed"));

        // Act
        ResponseEntity<TrailImage> response = trailImageController.uploadTrailImage(file, trailId, userId, description);

        // Assert
        assertEquals(500, response.getStatusCode().value());
        verify(imageService, times(1)).uploadImage(file, System.getenv("BUCKET_NAME"), System.getenv("TRAIL_PIC_FOLDER"));
        verify(trailImageService, times(0)).saveTrailImage(anyString(), anyString(), anyString(), anyString());
    }

    @Test
    void testDeleteTrailImage_Success() {
        // Arrange
        String imageId = "image123";
        when(trailImageService.deleteTrailImage(imageId)).thenReturn(true);

        // Act
        ResponseEntity<Void> response = trailImageController.deleteTrailImage(imageId);

        // Assert
        assertEquals(204, response.getStatusCode().value());
        verify(trailImageService, times(1)).deleteTrailImage(imageId);
    }

    @Test
    void testDeleteTrailImage_NotFound() {
        // Arrange
        String imageId = "nonexistent";
        when(trailImageService.deleteTrailImage(imageId)).thenReturn(false);

        // Act
        ResponseEntity<Void> response = trailImageController.deleteTrailImage(imageId);

        // Assert
        assertEquals(404, response.getStatusCode().value());
        verify(trailImageService, times(1)).deleteTrailImage(imageId);
    }
}
