package com.restaurant.reservation.service;

import com.restaurant.reservation.exception.CustomException;
import com.restaurant.reservation.model.Reservation;
import com.restaurant.reservation.repository.ReservationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ReservationServiceTest {

    @InjectMocks
    private ReservationService reservationService;

    @Mock
    private ReservationRepository reservationRepository;

    private Reservation reservation;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        reservation = new Reservation(LocalDateTime.now(), 4, "Done");
    }

    @Test
    public void testAddReservation() {
        when(reservationRepository.save(any(Reservation.class))).thenReturn(reservation);

        Reservation savedReservation = reservationService.addReservation(reservation);

        assertNotNull(savedReservation);
        assertEquals(LocalDateTime.now(), savedReservation.getReservationDate());
        verify(reservationRepository, times(1)).save(any(Reservation.class));
    }

    @Test
    public void testGetAllReservations() {
        List<Reservation> reservationList = new ArrayList<>();
        reservationList.add(reservation);
        when(reservationRepository.findAll()).thenReturn(reservationList);

        List<Reservation> result = reservationService.getAllReservations();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(LocalDateTime.now(), result.get(0).getReservationDate());
        verify(reservationRepository, times(1)).findAll();
    }

    @Test
    public void testGetReservationById_Success() {
        when(reservationRepository.findById(1L)).thenReturn(Optional.of(reservation));

        Optional<Reservation> result = reservationService.getReservationById(1L);

        assertTrue(result.isPresent());
        assertEquals(LocalDateTime.now(), result.get().getReservationDate());
        verify(reservationRepository, times(1)).findById(1L);
    }

    @Test
    public void testUpdateReservation_Success() {
        Reservation updatedReservation = new Reservation(LocalDateTime.now(), 5, "Done");
        updatedReservation.setId(1L);
        when(reservationRepository.existsById(1L)).thenReturn(true);
        when(reservationRepository.save(any(Reservation.class))).thenReturn(updatedReservation);

        Reservation result = reservationService.updateReservation(1L, updatedReservation);

        assertNotNull(result);
        assertEquals(LocalDateTime.now(), result.getReservationDate());
        verify(reservationRepository, times(1)).save(any(Reservation.class));
    }

    @Test
    public void testDeleteReservation_Success() {
        doNothing().when(reservationRepository).deleteById(1L);

        reservationService.deleteReservation(1L);

        verify(reservationRepository, times(1)).deleteById(1L);
    }
}
