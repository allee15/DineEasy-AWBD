package com.restaurant.reservation.controller;

import com.restaurant.reservation.service.RestaurantService;
import org.junit.jupiter.api.Test;
import com.restaurant.reservation.model.Restaurant;
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

@WebMvcTest(RestaurantController.class)
public class RestaurantControllerTest {

    @Mock
    private RestaurantService restaurantService;

    @InjectMocks
    private RestaurantController restaurantController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(restaurantController).build();
    }

    @Test
    public void testCreateRestaurant() throws Exception {
        Restaurant restaurant = new Restaurant();
        when(restaurantService.addRestaurant(any(Restaurant.class)))
                .thenReturn(restaurant);

        mockMvc.perform(post("/api/restaurants")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetAllRestaurants() throws Exception {
//        when(restaurantService.getAllRestaurants()).thenReturn(Collections.singletonList(new Restaurant()));
//
//        mockMvc.perform(get("/api/restaurants"))
//                .andExpect(status().isOk());
    }

    @Test
    public void testGetRestaurantById() throws Exception {
        Restaurant restaurant = new Restaurant();
        restaurant.setId(1L);
        when(restaurantService.getRestaurantById(1L)).thenReturn(Optional.of(restaurant));

        mockMvc.perform(get("/api/restaurants/1"))
                .andExpect(status().isOk());
    }

    @Test
    public void testUpdateRestaurant() throws Exception {
        Restaurant restaurant = new Restaurant();
        when(restaurantService.updateRestaurant(1L, any(Restaurant.class)))
                .thenReturn(restaurant);

        mockMvc.perform(put("/api/restaurants/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isOk());
    }

    @Test
    public void testDeleteRestaurant() throws Exception {
        doNothing().when(restaurantService).deleteRestaurant(1L);

        mockMvc.perform(delete("/api/restaurants/1"))
                .andExpect(status().isNoContent());
    }
}
