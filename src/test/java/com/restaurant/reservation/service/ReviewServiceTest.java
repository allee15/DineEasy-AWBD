package com.restaurant.reservation.service;

import com.restaurant.reservation.exception.CustomException;
import com.restaurant.reservation.model.Review;
import com.restaurant.reservation.repository.ReviewRepository;
import net.bytebuddy.asm.Advice;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ReviewServiceTest {

    @InjectMocks
    private ReviewService reviewService;

    @Mock
    private ReviewRepository reviewRepository;

    private Review review;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        review = new Review(5, "Great restaurant!", LocalDateTime.now());
    }

    @Test
    public void testAddReview() {
        when(reviewRepository.save(any(Review.class))).thenReturn(review);

        Review savedReview = reviewService.addReview(review);

        assertNotNull(savedReview);
        assertEquals("Great restaurant!", savedReview.getComment());
        verify(reviewRepository, times(1)).save(any(Review.class));
    }

    @Test
    public void testGetAllReviews() {
        List<Review> reviewList = new ArrayList<>();
        reviewList.add(review);
        when(reviewRepository.findAll()).thenReturn(reviewList);

        List<Review> result = reviewService.getAllReviews();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Great restaurant!", result.get(0).getComment());
        verify(reviewRepository, times(1)).findAll();
    }

    @Test
    public void testGetReviewById_Success() {
        when(reviewRepository.findById(1L)).thenReturn(Optional.of(review));

        Optional<Review> result = reviewService.getReviewById(1L);

        assertTrue(result.isPresent());
        assertEquals("Great restaurant!", result.get().getComment());
        verify(reviewRepository, times(1)).findById(1L);
    }

    @Test
    public void testUpdateReview_Success() {
        Review updatedReview = new Review(5, "Excellent service!", LocalDateTime.now());
        updatedReview.setId(1L);
        when(reviewRepository.existsById(1L)).thenReturn(true);
        when(reviewRepository.save(any(Review.class))).thenReturn(updatedReview);

        Review result = reviewService.updateReview(1L, updatedReview);

        assertNotNull(result);
        assertEquals("Excellent service!", result.getComment());
        verify(reviewRepository, times(1)).save(any(Review.class));
    }

    @Test
    public void testDeleteReview_Success() {
        doNothing().when(reviewRepository).deleteById(1L);

        reviewService.deleteReview(1L);

        verify(reviewRepository, times(1)).deleteById(1L);
    }
}
