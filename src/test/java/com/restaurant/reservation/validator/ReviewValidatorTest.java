package com.restaurant.reservation.validator;

import com.restaurant.reservation.model.Review;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ReviewValidatorTest {

    @Test
    void testValidReview() {
        Review review = new Review();
        review.setRating(4);
        review.setComment("Mâncarea a fost excelentă!");

        assertTrue(ReviewValidator.isValidReview(review));
    }

    @Test
    void testReviewWithRatingTooLow() {
        Review review = new Review();
        review.setRating(0);
        review.setComment("Nu a fost bun.");

        assertFalse(ReviewValidator.isValidReview(review));
    }

    @Test
    void testReviewWithRatingTooHigh() {
        Review review = new Review();
        review.setRating(6);
        review.setComment("Perfect!");

        assertFalse(ReviewValidator.isValidReview(review));
    }

    @Test
    void testReviewWithNullComment() {
        Review review = new Review();
        review.setRating(3);
        review.setComment(null);

        assertFalse(ReviewValidator.isValidReview(review));
    }

    @Test
    void testReviewWithEmptyComment() {
        Review review = new Review();
        review.setRating(3);
        review.setComment("   ");

        assertFalse(ReviewValidator.isValidReview(review));
    }

    @Test
    void testReviewWithInvalidRatingAndComment() {
        Review review = new Review();
        review.setRating(0);
        review.setComment("");

        assertFalse(ReviewValidator.isValidReview(review));
    }
}
