package com.restaurant.reservation.controller;

import com.restaurant.reservation.model.Review;
import com.restaurant.reservation.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
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

        reviewService.addReview(restaurantId, userId, review);

        return "Your request was handled successfully. Go back to previous page.";
    }

    @GetMapping
    public List<Review> getAllReviews() {
        return reviewService.getAllReviews();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Review> getReviewById(@PathVariable Long id) {
        Optional<Review> review = reviewService.getReviewById(id);
        return review.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Review> updateReview(@PathVariable Long id, @RequestBody Review updatedReview) {
        Review updated = reviewService.updateReview(id, updatedReview);
        return updated != null ? ResponseEntity.ok(updated) : ResponseEntity.notFound().build();
    }

    @PostMapping("/delete")
    public String deleteReview(@RequestParam Long reviewId) {
        reviewService.deleteReview(reviewId);
        return "Your request was handled successfully. Go back to previous page.";
    }
}
