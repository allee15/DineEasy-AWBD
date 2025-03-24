package com.restaurant.reservation.repository;

import com.restaurant.reservation.model.ReservationConfirmation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest  // Anotare pentru testarea cu o bază de date in-memory
public class ReservationConfirmationRepositoryTest {

    @Autowired
    private ReservationConfirmationRepository reservationConfirmationRepository;

    private ReservationConfirmation reservationConfirmation;

    @BeforeEach
    public void setUp() {
        reservationConfirmation = new ReservationConfirmation();
        reservationConfirmation.setEmailSent(true);
        reservationConfirmation.setId(1L);
    }

    @Test
    public void testSaveReservationConfirmation() {
        ReservationConfirmation savedReservationConfirmation = reservationConfirmationRepository.save(reservationConfirmation);

        assertNotNull(savedReservationConfirmation, "Saved reservation confirmation should not be null");
        assertEquals(true, savedReservationConfirmation.getEmailSent(), "Confirmation code should be 'ABC123'");
        assertEquals(1L, savedReservationConfirmation.getId(), "Reservation ID should be 1");
    }

    @Test
    public void testFindById() {
        ReservationConfirmation savedReservationConfirmation = reservationConfirmationRepository.save(reservationConfirmation);
        ReservationConfirmation foundReservationConfirmation = reservationConfirmationRepository.findById(savedReservationConfirmation.getId()).orElse(null);

        assertNotNull(foundReservationConfirmation, "Found reservation confirmation should not be null");
        assertEquals(savedReservationConfirmation.getId(), foundReservationConfirmation.getId(), "IDs should match");
        assertEquals(savedReservationConfirmation.getEmailSent(), foundReservationConfirmation.getEmailSent(), "Confirmation code should match");
    }

    @Test
    public void testDeleteReservationConfirmation() {
        ReservationConfirmation savedReservationConfirmation = reservationConfirmationRepository.save(reservationConfirmation);
        reservationConfirmationRepository.deleteById(savedReservationConfirmation.getId());

        assertFalse(reservationConfirmationRepository.existsById(savedReservationConfirmation.getId()), "Reservation confirmation should be deleted");
    }

    @Test
    public void testFindByNonExistingId() {
        ReservationConfirmation foundReservationConfirmation = reservationConfirmationRepository.findById(999L).orElse(null);

        assertNull(foundReservationConfirmation, "No reservation confirmation should be found with this ID");
    }
}
