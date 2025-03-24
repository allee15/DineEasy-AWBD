package com.restaurant.reservation.repository;

import com.restaurant.reservation.model.Restaurant;
import com.restaurant.reservation.model.Review;
import net.bytebuddy.asm.Advice;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class ReviewRepositoryTest {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    private Restaurant restaurant;

    @BeforeEach
    public void setUp() {
        restaurant = new Restaurant();
        restaurant.setName("Pasta Place");
        restaurant.setLocation("New York");
        restaurant = restaurantRepository.save(restaurant);

        Review review1 = new Review();
        review1.setReviewDate(LocalDateTime.now());
        review1.setRating(5);
        review1.setComment("Excellent food!");
        reviewRepository.save(review1);

        Review review2 = new Review();
        review2.setReviewDate(LocalDateTime.now());
        review2.setRating(4);
        review2.setComment("Good food, but slow service.");
        reviewRepository.save(review2);
    }

    @Test
    public void testFindByRestaurantId() {
        List<Review> reviews = reviewRepository.findByRestaurantId(restaurant.getId());

        assertFalse(reviews.isEmpty(), "Review list should not be empty");
        assertEquals(2, reviews.size(), "There should be two reviews for the restaurant");
        assertEquals("Excellent food!", reviews.get(0).getComment(), "First review comment should match");
        assertEquals("Good food, but slow service.", reviews.get(1).getComment(), "Second review comment should match");
    }

    @Test
    public void testFindByNonExistingRestaurantId() {
        List<Review> reviews = reviewRepository.findByRestaurantId(Long.MAX_VALUE);

        assertTrue(reviews.isEmpty(), "Review list should be empty for non-existing restaurant");
    }
}
