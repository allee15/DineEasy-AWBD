package com.restaurant.reservation.repository;

import com.restaurant.reservation.model.Reservation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class ReservationRepositoryTest {

    @Autowired
    private ReservationRepository reservationRepository;

    private Reservation reservation;

    @BeforeEach
    public void setUp() {
        reservation = new Reservation();
        reservation.setNbOfPeople(4);
        reservation.setReservationDate(LocalDateTime.now());
        reservation.setId(1L);
        reservation.setStatus("Done");
    }

    @Test
    public void testSaveReservation() {
        Reservation savedReservation = reservationRepository.save(reservation);

        assertNotNull(savedReservation, "Saved reservation should not be null");
        assertEquals(4, savedReservation.getNbOfPeople(), "Number of people should be 4");
        assertEquals(LocalDateTime.now(), savedReservation.getReservationDate(), "Reservation date should be '2025-03-24T19:00'");
    }

    @Test
    public void testFindById() {
        Reservation savedReservation = reservationRepository.save(reservation);
        Reservation foundReservation = reservationRepository.findById(savedReservation.getId()).orElse(null);

        assertNotNull(foundReservation, "Found reservation should not be null");
        assertEquals(savedReservation.getId(), foundReservation.getId(), "IDs should match");
        assertEquals(savedReservation.getReservationDate(), foundReservation.getReservationDate(), "Reservation date should match");
    }

    @Test
    public void testDeleteReservation() {
        Reservation savedReservation = reservationRepository.save(reservation);
        reservationRepository.deleteById(savedReservation.getId());

        assertFalse(reservationRepository.existsById(savedReservation.getId()), "Reservation should be deleted");
    }

    @Test
    public void testFindByNonExistingId() {
        Reservation foundReservation = reservationRepository.findById(999L).orElse(null);

        assertNull(foundReservation, "No reservation should be found with this ID");
    }
}
