package com.restaurant.reservation.service;

import com.restaurant.reservation.exception.CustomException;
import com.restaurant.reservation.model.Review;
import com.restaurant.reservation.repository.ReviewRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class ReviewService {
    @Autowired
    private ReviewRepository reviewRepository;

    public Review addReview(Review review) {
        log.info("Adding new Review: {}", review);
        return reviewRepository.save(review);
    }

    public List<Review> getAllReviews() {
        log.info("Fetching all Reviews");
        return reviewRepository.findAll();
    }

    public Optional<Review> getReviewById(Long id) {
        log.info("Fetching Review with ID: {}", id);
        return Optional.ofNullable(reviewRepository.findById(id)
                .orElseThrow(() -> new CustomException("Review with ID " + id + " not found")));
    }

    public void deleteReview(Long id) {
        log.info("Deleting Review with ID: {}", id);
        reviewRepository.deleteById(id);
    }

    public Review updateReview(Long id, Review updatedReview) {
        log.info("Updating Review with ID: {}", id);
        if (reviewRepository.existsById(id)) {
            updatedReview.setId(id);
            return reviewRepository.save(updatedReview);
        }
        return null;
    }
}
