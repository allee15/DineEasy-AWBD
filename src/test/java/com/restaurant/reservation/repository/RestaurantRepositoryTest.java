package com.restaurant.reservation.repository;

import com.restaurant.reservation.model.FoodType;
import com.restaurant.reservation.model.Restaurant;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

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
        List<Restaurant> restaurants = restaurantRepository.findByLocation("New York");

        assertFalse(restaurants.isEmpty(), "Restaurant list should not be empty");
        assertEquals("Pasta Place", restaurants.get(0).getName(), "Restaurant name should match");
    }

    @Test
    public void testFindByFoodType() {
        List<Restaurant> restaurants = restaurantRepository.findByFoodType(foodType);

        assertFalse(restaurants.isEmpty(), "Restaurant list should not be empty");
        assertEquals("Pasta Place", restaurants.get(0).getName(), "Restaurant name should match");
    }

    @Test
    public void testFindByNonExistingLocation() {
        List<Restaurant> restaurants = restaurantRepository.findByLocation("Non-Existing Location");

        assertTrue(restaurants.isEmpty(), "Restaurant list should be empty");
    }

    @Test
    public void testFindByNonExistingFoodType() {
        FoodType nonExistingFoodType = new FoodType();
        nonExistingFoodType.setType("Mexican");
        List<Restaurant> restaurants = restaurantRepository.findByFoodType(nonExistingFoodType);

        assertTrue(restaurants.isEmpty(), "Restaurant list should be empty");
    }
}
