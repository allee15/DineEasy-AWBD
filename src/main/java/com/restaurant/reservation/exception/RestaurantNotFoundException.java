package com.restaurant.reservation.exception;

public class RestaurantNotFoundException extends CustomException {
    public RestaurantNotFoundException(String message) {
        super(message);
    }
}
