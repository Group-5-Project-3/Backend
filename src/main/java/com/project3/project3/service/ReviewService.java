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

    public List<Review> getAllReviews() {
        return reviewRepository.findAll();
    }

    public List<Review> getReviewsByTrailId(String trailId) {
        return reviewRepository.findByTrailId(trailId);
    }

    public List<Review> getReviewsByUserId(String userId) {
        return reviewRepository.findByUserId(userId);
    }

    public Review createReview(Review review) {
        review.setTimestamp(LocalDateTime.now());
        return reviewRepository.save(review);
    }

    public boolean deleteReview(String id) {
        if (reviewRepository.existsById(id)) {
            reviewRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    // Update an existing review by ID
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
            return reviewRepository.save(existingReview);
        });
    }
}


