package com.restaurant.reservation.validator;

import com.restaurant.reservation.model.Reservation;

public class ReservationValidator {

    public static boolean isValidReservation(Reservation reservation) {
        if (reservation.getReservationDate() == null) {
            return false;
        }

        if (reservation.getNbOfPeople() <= 0) {
            return false;
        }
        return true;
    }
}
