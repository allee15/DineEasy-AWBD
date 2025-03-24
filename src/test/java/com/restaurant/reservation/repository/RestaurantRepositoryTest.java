package com.restaurant.reservation.repository;

import com.restaurant.reservation.model.FoodType;
import com.restaurant.reservation.model.Restaurant;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;

import java.awt.print.Pageable;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class RestaurantRepositoryTest {

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private FoodTypeRepository foodTypeRepository;

    private Restaurant restaurant;
    private FoodType foodType;
    private org.springframework.data.domain.Pageable pageable;

    @BeforeEach
    public void setUp() {
        foodType = new FoodType();
        foodType.setType("Italian");
        foodType = foodTypeRepository.save(foodType);

        restaurant = new Restaurant();
        restaurant.setName("Pasta Place");
        restaurant.setLocation("New York");
        restaurant.setId(1L);
        restaurantRepository.save(restaurant);
    }

    @Test
    public void testFindByLocation() {
        Page<Restaurant> restaurants = restaurantRepository.findByLocation("New York", pageable);

        assertFalse(restaurants.isEmpty(), "Restaurant list should not be empty");
        assertEquals("Pasta Place", restaurants.getContent().get(0).getName(), "Restaurant name should match");
    }

    @Test
    public void testFindByFoodType() {
        Page<Restaurant> restaurants = restaurantRepository.findByFoodType(foodType, pageable);

        assertFalse(restaurants.isEmpty(), "Restaurant list should not be empty");
        assertEquals("Pasta Place", restaurants.getContent().get(0).getName(), "Restaurant name should match");
    }

    @Test
    public void testFindByNonExistingLocation() {
        Page<Restaurant> restaurants = restaurantRepository.findByLocation("Non-Existing Location", pageable);

        assertTrue(restaurants.isEmpty(), "Restaurant list should be empty");
    }

    @Test
    public void testFindByNonExistingFoodType() {
        FoodType nonExistingFoodType = new FoodType();
        nonExistingFoodType.setType("Mexican");
        nonExistingFoodType = foodTypeRepository.save(nonExistingFoodType);

        Page<Restaurant> restaurants = restaurantRepository.findByFoodType(nonExistingFoodType, pageable);

        assertTrue(restaurants.isEmpty(), "Restaurant list should be empty");
    }
}
