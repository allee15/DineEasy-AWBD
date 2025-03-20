package com.restaurant.reservation.exception;

public class ReservationNotFoundException extends CustomException {
    public ReservationNotFoundException(String message) {
        super(message);
    }
}
