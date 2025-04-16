package com.restaurant.reservation.reviewservice.validator;

import com.restaurant.reservation.reviewservice.model.Review;

public class ReviewValidator {
    public static boolean isValidReview(Review review) {
        if (review.getRating() < 1 || review.getRating() > 5) {
            return false;
        }

        if (review.getComment() == null || review.getComment().trim().isEmpty()) {
            return false;
        }

        return true;
    }
}
