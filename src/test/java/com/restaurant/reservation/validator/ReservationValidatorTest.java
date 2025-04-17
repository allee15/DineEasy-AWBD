package com.restaurant.reservation.validator;

import com.restaurant.reservation.model.Reservation;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class ReservationValidatorTest {

    @Test
    void testValidReservation() {
        Reservation reservation = new Reservation();
        reservation.setReservationDate(LocalDateTime.now().plusDays(1));
        reservation.setNbOfPeople(4);

        assertTrue(ReservationValidator.isValidReservation(reservation));
    }

    @Test
    void testReservationWithNullDate() {
        Reservation reservation = new Reservation();
        reservation.setReservationDate(null);
        reservation.setNbOfPeople(2);

        assertFalse(ReservationValidator.isValidReservation(reservation));
    }

    @Test
    void testReservationWithZeroPeople() {
        Reservation reservation = new Reservation();
        reservation.setReservationDate(LocalDateTime.now().plusDays(1));
        reservation.setNbOfPeople(0);

        assertFalse(ReservationValidator.isValidReservation(reservation));
    }

    @Test
    void testReservationWithNegativePeople() {
        Reservation reservation = new Reservation();
        reservation.setReservationDate(LocalDateTime.now().plusDays(1));
        reservation.setNbOfPeople(-3);

        assertFalse(ReservationValidator.isValidReservation(reservation));
    }

    @Test
    void testReservationWithNullDateAndZeroPeople() {
        Reservation reservation = new Reservation();
        reservation.setReservationDate(null);
        reservation.setNbOfPeople(0);

        assertFalse(ReservationValidator.isValidReservation(reservation));
    }
}
