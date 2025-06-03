package com.restaurant.reservation.controller;

import com.restaurant.reservation.model.Review;
import com.restaurant.reservation.service.ReviewService;
import com.restaurant.reservation.validator.ReviewValidator;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.mockStatic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class ReviewControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ReviewService reviewService;

    @Test
    public void addReview_shouldRedirect_whenValidReview() throws Exception {
        Long restaurantId = 1L;
        Long userId = 2L;
        int rating = 5;
        String comment = "Great place!";

        try (var mockedValidator = mockStatic(ReviewValidator.class)) {
            mockedValidator.when(() -> ReviewValidator.isValidReview(any())).thenReturn(true);

            mockMvc.perform(post("/reviews/add")
                            .param("restaurantId", restaurantId.toString())
                            .param("userId", userId.toString())
                            .param("rating", String.valueOf(rating))
                            .param("comment", comment))
                    .andExpect(status().is3xxRedirection())
                    .andExpect(redirectedUrl("/restaurants/restaurantDetails/" + restaurantId + "?"));

            ArgumentCaptor<Review> captor = ArgumentCaptor.forClass(Review.class);
            verify(reviewService).addReview(eq(restaurantId), eq(userId), captor.capture());

            Review savedReview = captor.getValue();
            assertEquals(rating, savedReview.getRating());
            assertEquals(comment, savedReview.getComment());
        }
    }

    @Test
    public void addReview_shouldThrowException_whenInvalidReview() throws Exception {
        try (var mockedValidator = mockStatic(ReviewValidator.class)) {
            mockedValidator.when(() -> ReviewValidator.isValidReview(any())).thenReturn(false);

            mockMvc.perform(post("/reviews/add")
                            .param("restaurantId", "1")
                            .param("userId", "1")
                            .param("rating", "3")
                            .param("comment", "Okay"))
                    .andExpect(status().isInternalServerError());

            verify(reviewService, never()).addReview(anyLong(), anyLong(), any());
        }
    }

    @Test
    public void getAllReviews_shouldReturnList() throws Exception {
        Review r1 = new Review();
        Review r2 = new Review();
        List<Review> reviews = List.of(r1, r2);

        when(reviewService.getAllReviews()).thenReturn(reviews);

        mockMvc.perform(get("/reviews"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));

        verify(reviewService).getAllReviews();
    }

    @Test
    public void updateReview_shouldRedirect_whenValidReview() throws Exception {
        Long reviewId = 1L;
        String jsonReview = """
            {
              "rating": 4,
              "comment": "Updated comment"
            }
            """;

        try (var mockedValidator = mockStatic(ReviewValidator.class)) {
            mockedValidator.when(() -> ReviewValidator.isValidReview(any())).thenReturn(true);

            mockMvc.perform(post("/reviews/review/update/{id}", reviewId)
                            .contentType("application/json")
                            .content(jsonReview))
                    .andExpect(status().is3xxRedirection())
                    .andExpect(redirectedUrl("/restaurants"));

            ArgumentCaptor<Review> captor = ArgumentCaptor.forClass(Review.class);
            verify(reviewService).updateReview(eq(reviewId), captor.capture());

            Review updated = captor.getValue();
            assertEquals(reviewId, updated.getId());
            assertEquals(4, updated.getRating());
            assertEquals("Updated comment", updated.getComment());
        }
    }

    @Test
    public void updateReview_shouldThrowException_whenInvalidReview() throws Exception {
        Long reviewId = 1L;
        String jsonReview = """
            {
              "rating": 0,
              "comment": ""
            }
            """;

        try (var mockedValidator = mockStatic(ReviewValidator.class)) {
            mockedValidator.when(() -> ReviewValidator.isValidReview(any())).thenReturn(false);

            mockMvc.perform(post("/reviews/review/update/{id}", reviewId)
                            .contentType("application/json")
                            .content(jsonReview))
                    .andExpect(status().isInternalServerError());

            verify(reviewService, never()).updateReview(anyLong(), any());
        }
    }

    @Test
    public void deleteReview_shouldRedirect() throws Exception {
        Long reviewId = 1L;

        mockMvc.perform(post("/reviews/delete")
                        .param("reviewId", reviewId.toString()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/restaurants"));

        verify(reviewService).deleteReview(reviewId);
    }
}
