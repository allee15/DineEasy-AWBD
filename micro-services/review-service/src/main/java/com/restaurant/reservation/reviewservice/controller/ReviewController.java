package com.restaurant.reservation.reviewservice.controller;

import com.restaurant.reservation.reviewservice.exception.CustomException;
import com.restaurant.reservation.reviewservice.model.Review;
import com.restaurant.reservation.reviewservice.service.ReviewService;
import com.restaurant.reservation.reviewservice.validator.ReviewValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
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

        return "redirect:/restaurants/restaurantDetails/" + restaurantId + "?";
    }

    @GetMapping
    public List<Review> getAllReviews() {
        return reviewService.getAllReviews();
    }

    @PostMapping("/review/update/{id}")
    public String updateReview(@PathVariable Long id, @RequestBody Review updatedReview) {
        if (!ReviewValidator.isValidReview(updatedReview)) {
            throw new CustomException("Invalid review");
        }

        updatedReview.setId(id);
        reviewService.updateReview(id, updatedReview);

        return "redirect:/restaurants";
    }

    @PostMapping("/delete")
    public String deleteReview(@RequestParam Long reviewId) {
        reviewService.deleteReview(reviewId);
        return "redirect:/restaurants";
    }
}

