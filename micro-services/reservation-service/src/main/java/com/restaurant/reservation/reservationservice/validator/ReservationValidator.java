package com.restaurant.reservation.reservationservice.validator;

import com.restaurant.reservation.reservationservice.model.Reservation;

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

