package com.restaurant.reservation.service;

import com.restaurant.reservation.exception.CustomException;
import com.restaurant.reservation.model.Restaurant;
import com.restaurant.reservation.model.Review;
import com.restaurant.reservation.model.User;
import com.restaurant.reservation.repository.ReviewRepository;
import com.restaurant.reservation.repository.RestaurantRepository;
import com.restaurant.reservation.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class ReviewServiceTest {

    @Mock
    private ReviewRepository reviewRepository;

    @Mock
    private RestaurantRepository restaurantRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private ReviewService reviewService;

    private Review review;
    private Restaurant restaurant;
    private User user;

    @BeforeEach
    void setUp() {
        restaurant = new Restaurant();
        restaurant.setId(1L);
        restaurant.setName("Restaurant A");

        user = new User();
        user.setId(1L);
        user.setName("userA");

        review = new Review();
        review.setId(1L);
        review.setRating(5);
        review.setComment("Great food!");
    }

    @Test
    void testAddReview_Success() {
        when(restaurantRepository.findById(1L)).thenReturn(Optional.of(restaurant));
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(reviewRepository.save(review)).thenReturn(review);

        Review savedReview = reviewService.addReview(1L, 1L, review);

        assertNotNull(savedReview);
        assertEquals("Great food!", savedReview.getComment());
        verify(restaurantRepository).findById(1L);
        verify(userRepository).findById(1L);
        verify(reviewRepository).save(review);
    }

    @Test
    void testAddReview_EmptyComment() {
        review.setComment("  ");
        CustomException ex = assertThrows(CustomException.class, () -> {
            reviewService.addReview(1L, 1L, review);
        });

        assertEquals("Comentariul nu poate fi gol.", ex.getMessage());
    }

    @Test
    void testAddReview_RestaurantNotFound() {
        when(restaurantRepository.findById(1L)).thenReturn(Optional.empty());

        CustomException ex = assertThrows(CustomException.class, () -> {
            reviewService.addReview(1L, 1L, review);
        });

        assertEquals("Restaurant with ID 1 not found", ex.getMessage());
        verify(restaurantRepository).findById(1L);
    }

    @Test
    void testAddReview_UserNotFound() {
        when(restaurantRepository.findById(1L)).thenReturn(Optional.of(restaurant));
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        CustomException ex = assertThrows(CustomException.class, () -> {
            reviewService.addReview(1L, 1L, review);
        });

        assertEquals("User with ID 1 not found", ex.getMessage());
        verify(restaurantRepository).findById(1L);
        verify(userRepository).findById(1L);
    }

    @Test
    void testGetAllReviews() {
        List<Review> reviews = Arrays.asList(review);
        when(reviewRepository.findAll()).thenReturn(reviews);

        List<Review> result = reviewService.getAllReviews();

        assertEquals(1, result.size());
        assertEquals("Great food!", result.get(0).getComment());
        verify(reviewRepository).findAll();
    }

    @Test
    void testGetReviewById_Found() {
        when(reviewRepository.findById(1L)).thenReturn(Optional.of(review));

        Optional<Review> result = reviewService.getReviewById(1L);

        assertTrue(result.isPresent());
        assertEquals("Great food!", result.get().getComment());
        verify(reviewRepository).findById(1L);
    }

    @Test
    void testGetReviewById_NotFound() {
        when(reviewRepository.findById(2L)).thenReturn(Optional.empty());

        CustomException ex = assertThrows(CustomException.class, () -> {
            reviewService.getReviewById(2L);
        });

        assertEquals("Review with ID 2 not found", ex.getMessage());
        verify(reviewRepository).findById(2L);
    }

    @Test
    void testDeleteReview_Success() {
        doNothing().when(reviewRepository).deleteById(1L);

        reviewService.deleteReview(1L);

        verify(reviewRepository).deleteById(1L);
    }

    @Test
    void testUpdateReview_Found() {
        review.setComment("Updated comment");
        when(reviewRepository.existsById(1L)).thenReturn(true);
        when(reviewRepository.save(review)).thenReturn(review);

        Review updatedReview = reviewService.updateReview(1L, review);

        assertNotNull(updatedReview);
        assertEquals("Updated comment", updatedReview.getComment());
        verify(reviewRepository).existsById(1L);
        verify(reviewRepository).save(review);
    }

    @Test
    void testUpdateReview_NotFound() {
        when(reviewRepository.existsById(1L)).thenReturn(false);

        Review updatedReview = reviewService.updateReview(1L, review);

        assertNull(updatedReview);
        verify(reviewRepository).existsById(1L);
    }
}