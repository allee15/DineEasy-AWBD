package com.restaurant.reservation.service;

import com.restaurant.reservation.model.ReservationConfirmation;
import com.restaurant.reservation.repository.ReservationConfirmationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ConfirmationReservationServiceTest {

    @InjectMocks
    private ConfirmationReservationService confirmationReservationService;

    @Mock
    private ReservationConfirmationRepository reservationConfirmationRepository;

    private ReservationConfirmation reservationConfirmation;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        reservationConfirmation = new ReservationConfirmation();
        reservationConfirmation.setEmailSent(true);
    }

    @Test
    public void testAddReservationConfirmation() {
        when(reservationConfirmationRepository.save(any(ReservationConfirmation.class))).thenReturn(reservationConfirmation);

        ReservationConfirmation savedReservationConfirmation = confirmationReservationService.addReservationConfirmation(reservationConfirmation);

        assertNotNull(savedReservationConfirmation);
        assertEquals(true, savedReservationConfirmation.getEmailSent());
        verify(reservationConfirmationRepository, times(1)).save(any(ReservationConfirmation.class));
    }

    @Test
    public void testGetAllConfirmations() {
        List<ReservationConfirmation> confirmationList = new ArrayList<>();
        confirmationList.add(reservationConfirmation);
        when(reservationConfirmationRepository.findAll()).thenReturn(confirmationList);

        List<ReservationConfirmation> result = confirmationReservationService.getAllConfirmations();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(true, result.get(0).getEmailSent());
        verify(reservationConfirmationRepository, times(1)).findAll();
    }

    @Test
    public void testGetConfirmationById_Success() {
        when(reservationConfirmationRepository.findById(1L)).thenReturn(Optional.of(reservationConfirmation));

        Optional<ReservationConfirmation> result = confirmationReservationService.getConfirmationById(1L);

        assertTrue(result.isPresent());
        assertEquals(true, result.get().getEmailSent());
        verify(reservationConfirmationRepository, times(1)).findById(1L);
    }

    @Test
    public void testDeleteConfirmation_Success() {
        doNothing().when(reservationConfirmationRepository).deleteById(1L);

        confirmationReservationService.deleteConfirmation(1L);

        verify(reservationConfirmationRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testUpdateConfirmation_Success() {
        ReservationConfirmation updatedConfirmation = new ReservationConfirmation();
        updatedConfirmation.setEmailSent(true);
        updatedConfirmation.setId(1L);
        when(reservationConfirmationRepository.existsById(1L)).thenReturn(true);
        when(reservationConfirmationRepository.save(any(ReservationConfirmation.class))).thenReturn(updatedConfirmation);

        ReservationConfirmation result = confirmationReservationService.updateConfirmation(1L, updatedConfirmation);

        assertNotNull(result);
        assertEquals(true, result.getEmailSent());
        verify(reservationConfirmationRepository, times(1)).save(any(ReservationConfirmation.class));
    }
}