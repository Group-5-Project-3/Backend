package com.project3.project3.service;

import com.project3.project3.model.Review;
import com.project3.project3.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;

    @Autowired
    public ReviewService(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    // Retrieve all reviews
    public List<Review> getAllReviews() {
        return reviewRepository.findAll();
    }

    // Retrieve reviews by trail ID
    public List<Review> getReviewsByTrailId(String trailId) {
        return reviewRepository.findByTrailId(trailId);
    }
    public Double calculateAverageDifficulty(String trailId) {
        List<Review> reviews = reviewRepository.findByTrailId(trailId);
        double totalDifficulty = 0.0;
        int count = 0;

        for (Review review : reviews) {
            if (review.getDifficultyRating() != null) {
                totalDifficulty += review.getDifficultyRating();
                count++;
            }
        }
        return count > 0 ? totalDifficulty / count : 0.0;
    }

    // Retrieve reviews by user ID
    public List<Review> getReviewsByUserId(String userId) {
        return reviewRepository.findByUserId(userId);
    }

    // Create a new review
    public Review createReview(Review review) {
        review.setTimestamp(LocalDateTime.now());
        return reviewRepository.save(review);
    }

    // Delete a review by ID
    public boolean deleteReview(String id) {
        if (reviewRepository.existsById(id)) {
            reviewRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    // Update an existing review
    public Optional<Review> updateReview(String id, Review updatedReview) {
        return reviewRepository.findById(id).map(existingReview -> {
            if (updatedReview.getRating() != null) {
                existingReview.setRating(updatedReview.getRating());
            }
            if (updatedReview.getComment() != null) {
                existingReview.setComment(updatedReview.getComment());
            }
            if (updatedReview.getTimestamp() != null) {
                existingReview.setTimestamp(updatedReview.getTimestamp());
            }
            if (updatedReview.getDifficultyRating() != null) {
                existingReview.setDifficultyRating(updatedReview.getDifficultyRating());
            }
            return reviewRepository.save(existingReview);
        });
    }
}



