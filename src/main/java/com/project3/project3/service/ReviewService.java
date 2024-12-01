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

    public List<Review> getReviewsByTrailId(String trailId) {
        return reviewRepository.findByTrailId(trailId);
    }

    public List<Review> getReviewsByUserId(String userId) {
        return reviewRepository.findByUserId(userId);
    }

    private void calculateAndSaveAverageDifficulty(String trailId) {
        List<Review> reviews = reviewRepository.findByTrailId(trailId);
        double totalDifficulty = 0.0;
        int count = 0;
        for (Review review : reviews) {
            if (review.getDifficultyRating() != null) {
                totalDifficulty += review.getDifficultyRating();
                count++;
            }
        }
        double avgDifficulty = count > 0 ? totalDifficulty / count : 0.0;
        Trail trail = trailRepository.findByTrailId(trailId);
        trail.setAvgDifficulty(avgDifficulty);
        trailRepository.save(trail);
    }

    private void calculateAndSaveAverageRating(String trailId) {
        List<Review> reviews = reviewRepository.findByTrailId(trailId);
        double totalRating = 0.0;
        int count = 0;
        for (Review review : reviews) {
            if (review.getRating() != null) {
                totalRating += review.getRating();
                count++;
            }
        }
        double avgRating = count > 0 ? totalRating / count : 0.0;
        Trail trail = trailRepository.findByTrailId(trailId);
        trail.setAvgRating(avgRating);
        trailRepository.save(trail);
    }

    public Review createReview(Review review) {
        StringBuilder sb = new StringBuilder();
        List<Review> reviews = reviewRepository.findByTrailId(review.getTrailId());
        Trail trail = trailRepository.findByTrailId(review.getTrailId());
        for (int i = 0; i < reviews.size(); i++) {
            sb.append(i).append(". ").append(reviews.get(i).getComment()).append(" ");
        }
        sb.append(reviews.size() + 1).append(". ").append(review.getComment()).append(" ");
        String sentiments = ChatGPTUtil.getChatGPTTrailReviewSentiments(sb.toString());
        trail.setSentiments(sentiments);
        trailRepository.save(trail);
        review.setTimestamp(LocalDateTime.now());
        Review savedReview = reviewRepository.save(review);
        calculateAndSaveAverageDifficulty(review.getTrailId());
        calculateAndSaveAverageRating(review.getTrailId());

        return savedReview;
    }

    public boolean deleteReview(String id) {
        if (reviewRepository.existsById(id)) {
            reviewRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

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

    public void deleteByUserId(String userId) {
        List<Review> userReviews = reviewRepository.findByUserId(userId);
        if (!userReviews.isEmpty()) {
            reviewRepository.deleteAll(userReviews);
        }
    }
}



