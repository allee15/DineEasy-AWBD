package com.restaurant.reservation.service;

import com.restaurant.reservation.exception.CustomException;
import com.restaurant.reservation.model.FoodType;
import com.restaurant.reservation.model.Restaurant;
import com.restaurant.reservation.repository.RestaurantRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class RestaurantServiceTest {

    @InjectMocks
    private RestaurantService restaurantService;

    @Mock
    private RestaurantRepository restaurantRepository;

    private Restaurant restaurant;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        restaurant = new Restaurant("The Bistro", "123 Main St", new FoodType("Pizza"));
    }

    @Test
    public void testAddRestaurant() {
        when(restaurantRepository.save(any(Restaurant.class))).thenReturn(restaurant);

        Restaurant savedRestaurant = restaurantService.addRestaurant(restaurant);

        assertNotNull(savedRestaurant);
        assertEquals("The Bistro", savedRestaurant.getName());
        verify(restaurantRepository, times(1)).save(any(Restaurant.class));
    }

    @Test
    public void testGetAllRestaurants() {
        List<Restaurant> restaurantList = new ArrayList<>();
        restaurantList.add(restaurant);
        when(restaurantRepository.findAll()).thenReturn(restaurantList);

        List<Restaurant> result = restaurantService.getAllRestaurants();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("The Bistro", result.get(0).getName());
        verify(restaurantRepository, times(1)).findAll();
    }

    @Test
    public void testGetRestaurantById_Success() {
        when(restaurantRepository.findById(1L)).thenReturn(Optional.of(restaurant));

        Optional<Restaurant> result = restaurantService.getRestaurantById(1L);

        assertTrue(result.isPresent());
        assertEquals("The Bistro", result.get().getName());
        verify(restaurantRepository, times(1)).findById(1L);
    }

    @Test
    public void testUpdateRestaurant_Success() {
        Restaurant updatedRestaurant = new Restaurant("The Bistro Updated", "123 Main St", new FoodType("Pizza"));
        updatedRestaurant.setId(1L);
        when(restaurantRepository.existsById(1L)).thenReturn(true);
        when(restaurantRepository.save(any(Restaurant.class))).thenReturn(updatedRestaurant);

        Restaurant result = restaurantService.updateRestaurant(1L, updatedRestaurant);

        assertNotNull(result);
        assertEquals("The Bistro Updated", result.getName());
        verify(restaurantRepository, times(1)).save(any(Restaurant.class));
    }

    @Test
    public void testDeleteRestaurant_Success() {
        doNothing().when(restaurantRepository).deleteById(1L);

        restaurantService.deleteRestaurant(1L);

        verify(restaurantRepository, times(1)).deleteById(1L);
    }
}
