package com.restaurant.reservation.validator;

import com.restaurant.reservation.model.Restaurant;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RestaurantValidatorTest {

    @Test
    void testValidRestaurant() {
        Restaurant restaurant = new Restaurant();
        restaurant.setName("La Bella");
        restaurant.setLocation("București");

        assertTrue(RestaurantValidator.isValidRestaurant(restaurant));
    }

    @Test
    void testRestaurantWithNullName() {
        Restaurant restaurant = new Restaurant();
        restaurant.setName(null);
        restaurant.setLocation("Cluj");

        assertFalse(RestaurantValidator.isValidRestaurant(restaurant));
    }

    @Test
    void testRestaurantWithEmptyName() {
        Restaurant restaurant = new Restaurant();
        restaurant.setName("   ");
        restaurant.setLocation("Timișoara");

        assertFalse(RestaurantValidator.isValidRestaurant(restaurant));
    }

    @Test
    void testRestaurantWithNullLocation() {
        Restaurant restaurant = new Restaurant();
        restaurant.setName("Casa Veche");
        restaurant.setLocation(null);

        assertFalse(RestaurantValidator.isValidRestaurant(restaurant));
    }

    @Test
    void testRestaurantWithEmptyLocation() {
        Restaurant restaurant = new Restaurant();
        restaurant.setName("Hanul lui Manuc");
        restaurant.setLocation("  ");

        assertFalse(RestaurantValidator.isValidRestaurant(restaurant));
    }

    @Test
    void testRestaurantWithNullNameAndLocation() {
        Restaurant restaurant = new Restaurant();
        restaurant.setName(null);
        restaurant.setLocation(null);

        assertFalse(RestaurantValidator.isValidRestaurant(restaurant));
    }
}
