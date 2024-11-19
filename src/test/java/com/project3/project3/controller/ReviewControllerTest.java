package com.project3.project3.controller;

import com.project3.project3.model.Review;
import com.project3.project3.service.ReviewService;
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
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class ReviewControllerTest {

    @Mock
    private ReviewService reviewService;

    @InjectMocks
    private ReviewController reviewController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetTrailReviews() {
        String trailId = "trail123";
        Review review1 = new Review(trailId, "user1", 4.5, "Great trail!", LocalDateTime.now());
        Review review2 = new Review(trailId, "user2", 5.0, "Amazing experience!", LocalDateTime.now());

        when(reviewService.getReviewsByTrailId(trailId)).thenReturn(Arrays.asList(review1, review2));

        ResponseEntity<List<Review>> response = reviewController.getTrailReviews(trailId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
        verify(reviewService, times(1)).getReviewsByTrailId(trailId);
    }

    @Test
    void testGetUserReviews() {
        String userId = "user123";
        Review review1 = new Review("trail1", userId, 4.0, "Good trail", LocalDateTime.now());
        Review review2 = new Review("trail2", userId, 3.5, "Decent trail", LocalDateTime.now());

        when(reviewService.getReviewsByUserId(userId)).thenReturn(Arrays.asList(review1, review2));

        ResponseEntity<List<Review>> response = reviewController.getUserReviews(userId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
        verify(reviewService, times(1)).getReviewsByUserId(userId);
    }

    @Test
    void testCreateReview() {
        Review review = new Review("trail123", "user123", 5.0, "Fantastic trail!", LocalDateTime.now());

        when(reviewService.createReview(review)).thenReturn(review);

        ResponseEntity<Review> response = reviewController.createReview(review);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(review, response.getBody());
        verify(reviewService, times(1)).createReview(review);
    }

    @Test
    void testUpdateReview() {
        String reviewId = "review123";
        Review updatedReview = new Review("trail123", "user123", 4.5, "Updated review", LocalDateTime.now());

        when(reviewService.updateReview(reviewId, updatedReview)).thenReturn(Optional.of(updatedReview));

        ResponseEntity<Optional<Review>> response = reviewController.updateReview(reviewId, updatedReview);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(Optional.of(updatedReview), response.getBody());
        verify(reviewService, times(1)).updateReview(reviewId, updatedReview);
    }

    @Test
    void testDeleteReview() {
        String reviewId = "review123";

        doNothing().when(reviewService).deleteReview(reviewId);

        ResponseEntity<Void> response = reviewController.deleteReview(reviewId);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(reviewService, times(1)).deleteReview(reviewId);
    }
}
