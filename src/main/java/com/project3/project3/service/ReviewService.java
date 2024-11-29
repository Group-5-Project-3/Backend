package com.project3.project3.service;

import com.project3.project3.model.Review;
import com.project3.project3.model.Trail;
import com.project3.project3.repository.ReviewRepository;
import com.project3.project3.repository.TrailRepository;
import com.project3.project3.utility.ChatGPTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final TrailRepository trailRepository;

    @Autowired
    public ReviewService(ReviewRepository reviewRepository, TrailRepository trailRepository) {
        this.reviewRepository = reviewRepository;
        this.trailRepository = trailRepository;
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
        StringBuilder sb = new StringBuilder();
        List<Review> reviews = reviewRepository.findByTrailId(review.getTrailId());
        Trail trail = trailRepository.findByTrailId(review.getTrailId());
        for(int i = 0; i < reviews.size(); i++) {
            sb.append(i).append(". ").append(reviews.get(i).getComment()).append(" ");
        }
        sb.append(reviews.size() + 1).append(". ").append(review.getComment()).append(" ");
        String sentiments = ChatGPTUtil.getChatGPTTrailReviewSentiments(sb.toString());
        trail.setSentiments(sentiments);
        trailRepository.save(trail);
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



