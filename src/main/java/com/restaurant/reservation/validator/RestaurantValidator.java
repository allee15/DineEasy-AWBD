package com.restaurant.reservation.validator;

import com.restaurant.reservation.model.Restaurant;

public class RestaurantValidator {
    public static boolean isValidRestaurant(Restaurant restaurant) {
        if (restaurant.getName() == null || restaurant.getName().trim().isEmpty()) {
            return false;
        }

        if (restaurant.getLocation() == null || restaurant.getLocation().trim().isEmpty()) {
            return false;
        }

        return true;
    }
}
