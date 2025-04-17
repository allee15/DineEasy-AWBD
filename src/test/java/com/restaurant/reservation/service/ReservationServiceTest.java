package com.restaurant.reservation.service;

import com.restaurant.reservation.exception.CustomException;
import com.restaurant.reservation.model.Reservation;
import com.restaurant.reservation.repository.ReservationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;;

@SpringBootTest
class ReservationServiceTest {

    @Mock
    private ReservationRepository reservationRepository;

    @InjectMocks
    private ReservationService reservationService;

    private Reservation reservation;

    @BeforeEach
    void setUp() {
        reservation = new Reservation();
        reservation.setId(1L);
        reservation.setReservationDate(LocalDateTime.now());
        reservation.setNbOfPeople(4);
    }

    @Test
    void testAddReservation() {
        when(reservationRepository.save(reservation)).thenReturn(reservation);

        reservationService.addReservation(reservation);

        verify(reservationRepository).save(reservation);
    }

    @Test
    void testGetAllReservations() {
        when(reservationRepository.findAll()).thenReturn(List.of(reservation));

        List<Reservation> result = reservationService.getAllReservations();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(reservation, result.get(0));
        verify(reservationRepository).findAll();
    }

    @Test
    void testGetReservationById_Found() {
        when(reservationRepository.findById(1L)).thenReturn(Optional.of(reservation));

        Optional<Reservation> result = reservationService.getReservationById(1L);

        assertTrue(result.isPresent());
        assertEquals(reservation, result.get());
        verify(reservationRepository).findById(1L);
    }

    @Test
    void testGetReservationById_NotFound() {
        when(reservationRepository.findById(1L)).thenReturn(Optional.empty());

        CustomException ex = assertThrows(CustomException.class, () -> {
            reservationService.getReservationById(1L);
        });

        assertEquals("Reservation with ID 1 not found", ex.getMessage());
        verify(reservationRepository).findById(1L);
    }

    @Test
    void testDeleteReservation_Found() {
        when(reservationRepository.existsById(1L)).thenReturn(true);

        reservationService.deleteReservation(1L);

        verify(reservationRepository).deleteById(1L);
    }

    @Test
    void testDeleteReservation_NotFound() {
        when(reservationRepository.existsById(1L)).thenReturn(false);

        CustomException ex = assertThrows(CustomException.class, () -> {
            reservationService.deleteReservation(1L);
        });

        assertEquals("Reservation with ID 1 not found", ex.getMessage());
        verify(reservationRepository).existsById(1L);
    }

    @Test
    void testUpdateReservation_Found() {
        when(reservationRepository.existsById(reservation.getId())).thenReturn(true);
        when(reservationRepository.save(reservation)).thenReturn(reservation);

        Reservation updated = reservationService.updateReservation(reservation);

        assertNotNull(updated);
        assertEquals(reservation.getId(), updated.getId());
        verify(reservationRepository).existsById(reservation.getId());
        verify(reservationRepository).save(reservation);
    }

    @Test
    void testUpdateReservation_NotFound() {
        when(reservationRepository.existsById(reservation.getId())).thenReturn(false);

        Reservation updated = reservationService.updateReservation(reservation);

        assertNull(updated);
        verify(reservationRepository).existsById(reservation.getId());
    }
}