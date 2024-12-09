package com.project3.project3.controller;

import com.project3.project3.model.Review;
import com.project3.project3.service.ReviewService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class ReviewControllerTest {

    @InjectMocks
    private ReviewController reviewController;

    @Mock
    private ReviewService reviewService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetTrailReviews() {
        // Arrange
        String trailId = "trail123";
        Review review1 = new Review(trailId, "user1", 3.5, 4.0, "Great trail!", LocalDateTime.now());
        Review review2 = new Review(trailId, "user2", 4.0, 5.0, "Amazing!", LocalDateTime.now());
        when(reviewService.getReviewsByTrailId(trailId)).thenReturn(Arrays.asList(review1, review2));

        // Act
        ResponseEntity<List<Review>> response = reviewController.getTrailReviews(trailId);

        // Assert
        assertEquals(200, response.getStatusCode().value());
        assertEquals(2, response.getBody().size());
        verify(reviewService, times(1)).getReviewsByTrailId(trailId);
    }

    @Test
    void testGetUserReviews() {
        // Arrange
        String userId = "user123";
        Review review1 = new Review("trail1", userId, 4.0, 5.0, "Awesome!", LocalDateTime.now());
        Review review2 = new Review("trail2", userId, 3.5, 4.0, "Good experience!", LocalDateTime.now());
        when(reviewService.getReviewsByUserId(userId)).thenReturn(Arrays.asList(review1, review2));

        // Act
        ResponseEntity<List<Review>> response = reviewController.getUserReviews(userId);

        // Assert
        assertEquals(200, response.getStatusCode().value());
        assertEquals(2, response.getBody().size());
        verify(reviewService, times(1)).getReviewsByUserId(userId);
    }

    @Test
    void testGetAverageDifficulty() {
        // Arrange
        String trailId = "trail123";
        when(reviewService.calculateAverageDifficulty(trailId)).thenReturn(4.0);

        // Act
        ResponseEntity<Double> response = reviewController.getAverageDifficulty(trailId);

        // Assert
        assertEquals(200, response.getStatusCode().value());
        assertEquals(4.0, response.getBody());
        verify(reviewService, times(1)).calculateAverageDifficulty(trailId);
    }

    @Test
    void testCreateReview() {
        // Arrange
        Review review = new Review("trail123", "user123", 4.0, 5.0, "Loved it!", LocalDateTime.now());
        when(reviewService.createReview(review)).thenReturn(review);

        // Act
        ResponseEntity<Review> response = reviewController.createReview(review);

        // Assert
        assertEquals(200, response.getStatusCode().value());
        assertEquals("user123", response.getBody().getUserId());
        verify(reviewService, times(1)).createReview(review);
    }

    @Test
    void testUpdateReview_Success() {
        // Arrange
        String reviewId = "review123";
        Review updatedReview = new Review("trail123", "user123", 3.5, 4.0, "Updated comment", LocalDateTime.now());
        when(reviewService.updateReview(reviewId, updatedReview)).thenReturn(Optional.of(updatedReview));

        // Act
        ResponseEntity<Review> response = reviewController.updateReview(reviewId, updatedReview);

        // Assert
        assertEquals(200, response.getStatusCode().value());
        assertEquals("Updated comment", response.getBody().getComment());
        verify(reviewService, times(1)).updateReview(reviewId, updatedReview);
    }

    @Test
    void testUpdateReview_NotFound() {
        // Arrange
        String reviewId = "nonexistent";
        Review updatedReview = new Review("trail123", "user123", 3.5, 4.0, "Updated comment", LocalDateTime.now());
        when(reviewService.updateReview(reviewId, updatedReview)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<Review> response = reviewController.updateReview(reviewId, updatedReview);

        // Assert
        assertEquals(404, response.getStatusCode().value());
        verify(reviewService, times(1)).updateReview(reviewId, updatedReview);
    }

    @Test
    void testDeleteReview_Success() {
        // Arrange
        String reviewId = "review123";
        when(reviewService.deleteReview(reviewId)).thenReturn(true);

        // Act
        ResponseEntity<Void> response = reviewController.deleteReview(reviewId);

        // Assert
        assertEquals(204, response.getStatusCode().value());
        verify(reviewService, times(1)).deleteReview(reviewId);
    }

    @Test
    void testDeleteReview_NotFound() {
        // Arrange
        String reviewId = "nonexistent";
        when(reviewService.deleteReview(reviewId)).thenReturn(false);

        // Act
        ResponseEntity<Void> response = reviewController.deleteReview(reviewId);

        // Assert
        assertEquals(404, response.getStatusCode().value());
        verify(reviewService, times(1)).deleteReview(reviewId);
    }
}
