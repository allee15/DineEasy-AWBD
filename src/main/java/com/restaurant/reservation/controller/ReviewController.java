package com.restaurant.reservation.controller;

import com.restaurant.reservation.exception.CustomException;
import com.restaurant.reservation.model.Review;
import com.restaurant.reservation.service.ReviewService;
import com.restaurant.reservation.validator.RestaurantValidator;
import com.restaurant.reservation.validator.ReviewValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/reviews")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @PostMapping("/add")
    public String addReview(
            @RequestParam Long restaurantId,
            @RequestParam Long userId,
            @RequestParam int rating,
            @RequestParam String comment) {

        Review review = new Review();
        review.setRating(rating);
        review.setComment(comment);

        if (!ReviewValidator.isValidReview(review)) {
            throw new CustomException("Invalid review");
        }

        reviewService.addReview(restaurantId, userId, review);

        return "redirect:/restaurants";
    }

    @GetMapping
    public List<Review> getAllReviews() {
        return reviewService.getAllReviews();
    }

    @GetMapping("/{id}") //TODO
    public ResponseEntity<Review> getReviewById(@PathVariable Long id) {
        Optional<Review> review = reviewService.getReviewById(id);
        return review.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}") //TODO
    public ResponseEntity<Review> updateReview(@PathVariable Long id, @RequestBody Review updatedReview) {
        Review updated = reviewService.updateReview(id, updatedReview);
        return updated != null ? ResponseEntity.ok(updated) : ResponseEntity.notFound().build();
    }

    @PostMapping("/delete")
    public String deleteReview(@RequestParam Long reviewId) {
        reviewService.deleteReview(reviewId);
        return "redirect:/restaurants";
    }
}
