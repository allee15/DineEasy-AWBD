package com.restaurant.reservation.controller;

import com.restaurant.reservation.service.ReviewService;
import org.junit.jupiter.api.Test;
import com.restaurant.reservation.model.Review;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(ReviewController.class)
public class ReviewControllerTest {

    @Mock
    private ReviewService reviewService;

    @InjectMocks
    private ReviewController reviewController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(reviewController).build();
    }

    @Test
    public void testCreateReview() throws Exception {
        Review review = new Review();
        when(reviewService.addReview(any(Review.class)))
                .thenReturn(review);

        mockMvc.perform(post("/api/reviews")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetAllReviews() throws Exception {
        when(reviewService.getAllReviews()).thenReturn(Collections.singletonList(new Review()));

        mockMvc.perform(get("/api/reviews"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetReviewById() throws Exception {
        Review review = new Review();
        review.setId(1L);
        when(reviewService.getReviewById(1L)).thenReturn(Optional.of(review));

        mockMvc.perform(get("/api/reviews/1"))
                .andExpect(status().isOk());
    }

    @Test
    public void testUpdateReview() throws Exception {
        Review review = new Review();
        when(reviewService.updateReview(1L, any(Review.class)))
                .thenReturn(review);

        mockMvc.perform(put("/api/reviews/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isOk());
    }

    @Test
    public void testDeleteReview() throws Exception {
        doNothing().when(reviewService).deleteReview(1L);

        mockMvc.perform(delete("/api/reviews/1"))
                .andExpect(status().isNoContent());
    }
}
