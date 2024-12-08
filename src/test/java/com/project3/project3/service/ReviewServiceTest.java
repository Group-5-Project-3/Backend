package com.project3.project3.service;

import com.project3.project3.model.Review;
import com.project3.project3.repository.ReviewRepository;
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

class ReviewServiceTest {

    @InjectMocks
    private ReviewService reviewService;

    @Mock
    private ReviewRepository reviewRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllReviews() {
        // Arrange
        List<Review> reviews = Arrays.asList(new Review(), new Review());
        when(reviewRepository.findAll()).thenReturn(reviews);

        // Act
        List<Review> result = reviewService.getAllReviews();

        // Assert
        assertEquals(2, result.size());
        verify(reviewRepository, times(1)).findAll();
    }

    @Test
    void testGetReviewsByTrailId() {
        // Arrange
        String trailId = "trail123";
        List<Review> reviews = Arrays.asList(new Review(), new Review());
        when(reviewRepository.findByTrailId(trailId)).thenReturn(reviews);

        // Act
        List<Review> result = reviewService.getReviewsByTrailId(trailId);

        // Assert
        assertEquals(2, result.size());
        verify(reviewRepository, times(1)).findByTrailId(trailId);
    }

    @Test
    void testCalculateAverageDifficulty() {
        // Arrange
        String trailId = "trail123";
        List<Review> reviews = Arrays.asList(
                new Review("trail123", "user1", 3.5, 4.0, "Good trail", LocalDateTime.now()),
                new Review("trail123", "user2", 4.5, 5.0, "Great trail", LocalDateTime.now())
        );
        when(reviewRepository.findByTrailId(trailId)).thenReturn(reviews);

        // Act
        Double result = reviewService.calculateAverageDifficulty(trailId);

        // Assert
        assertEquals(4.0, result);
        verify(reviewRepository, times(1)).findByTrailId(trailId);
    }

    @Test
    void testGetReviewsByUserId() {
        // Arrange
        String userId = "user123";
        List<Review> reviews = Arrays.asList(new Review(), new Review());
        when(reviewRepository.findByUserId(userId)).thenReturn(reviews);

        // Act
        List<Review> result = reviewService.getReviewsByUserId(userId);

        // Assert
        assertEquals(2, result.size());
        verify(reviewRepository, times(1)).findByUserId(userId);
    }

    @Test
    void testCreateReview() {
        // Arrange
        Review review = new Review("trail123", "user123", 5.0, 4.5, "Awesome trail!", LocalDateTime.now());
        when(reviewRepository.save(any(Review.class))).thenReturn(review);

        // Act
        Review result = reviewService.createReview(review);

        // Assert
        assertNotNull(result.getTimestamp());
        verify(reviewRepository, times(1)).save(review);
    }

    @Test
    void testDeleteReview_Success() {
        // Arrange
        String reviewId = "review123";
        when(reviewRepository.existsById(reviewId)).thenReturn(true);

        // Act
        boolean result = reviewService.deleteReview(reviewId);

        // Assert
        assertTrue(result);
        verify(reviewRepository, times(1)).existsById(reviewId);
        verify(reviewRepository, times(1)).deleteById(reviewId);
    }

    @Test
    void testDeleteReview_NotFound() {
        // Arrange
        String reviewId = "nonexistent";
        when(reviewRepository.existsById(reviewId)).thenReturn(false);

        // Act
        boolean result = reviewService.deleteReview(reviewId);

        // Assert
        assertFalse(result);
        verify(reviewRepository, times(1)).existsById(reviewId);
        verify(reviewRepository, never()).deleteById(reviewId);
    }

    @Test
    void testUpdateReview_Success() {
        // Arrange
        String reviewId = "review123";
        Review existingReview = new Review("trail123", "user123", 4.0, 3.5, "Nice trail", LocalDateTime.now());
        Review updatedReview = new Review("trail123", "user123", 5.0, 4.5, "Updated comment", LocalDateTime.now());

        when(reviewRepository.findById(reviewId)).thenReturn(Optional.of(existingReview));
        when(reviewRepository.save(any(Review.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        Optional<Review> result = reviewService.updateReview(reviewId, updatedReview);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(4.5, result.get().getRating());
        assertEquals("Updated comment", result.get().getComment());
        verify(reviewRepository, times(1)).findById(reviewId);
        verify(reviewRepository, times(1)).save(existingReview);
    }
}

