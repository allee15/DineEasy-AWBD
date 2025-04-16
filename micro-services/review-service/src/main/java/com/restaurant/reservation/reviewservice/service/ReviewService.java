package com.restaurant.reservation.reviewservice.service;

import com.restaurant.reservation.reviewservice.exception.CustomException;
import com.restaurant.reservation.reviewservice.model.Review;
import com.restaurant.reservation.reviewservice.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReviewService {
    @Autowired
    private ReviewRepository reviewRepository;

    public Review addReview(Long restaurantId, Long userId, Review review) {
        if (review.getComment() == null || review.getComment().trim().isEmpty()) {
            throw new CustomException("Comentariul nu poate fi gol.");
        }

        review.setRestaurantId(restaurantId);
        review.setUserId(userId);

        return reviewRepository.save(review);
    }

    public List<Review> getAllReviews() {
        return reviewRepository.findAll();
    }

    public Optional<Review> getReviewById(Long id) {
        return Optional.ofNullable(reviewRepository.findById(id)
                .orElseThrow(() -> new CustomException("Review with ID " + id + " not found")));
    }

    public void deleteReview(Long id) {
        reviewRepository.deleteById(id);
    }

    public Review updateReview(Long id, Review updatedReview) {
        if (reviewRepository.existsById(id)) {
            updatedReview.setId(id);
            return reviewRepository.save(updatedReview);
        }
        return null;
    }
}
