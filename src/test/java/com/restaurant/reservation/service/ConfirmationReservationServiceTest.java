package com.restaurant.reservation.service;

import com.restaurant.reservation.exception.CustomException;
import com.restaurant.reservation.model.ReservationConfirmation;
import com.restaurant.reservation.repository.ReservationConfirmationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class ConfirmationReservationServiceTest {

    @Mock
    private ReservationConfirmationRepository reservationConfirmationRepository;

    @InjectMocks
    private ConfirmationReservationService confirmationReservationService;

    private ReservationConfirmation reservationConfirmation;

    @BeforeEach
    void setup() {
        reservationConfirmation = new ReservationConfirmation();
        reservationConfirmation.setId(1L);
        reservationConfirmation.setId(101L);
        reservationConfirmation.setEmailSent(true);
    }

    @Test
    void testAddReservationConfirmation() {
        when(reservationConfirmationRepository.save(any(ReservationConfirmation.class))).thenReturn(reservationConfirmation);

        ReservationConfirmation savedReservationConfirmation = confirmationReservationService.addReservationConfirmation(reservationConfirmation);

        assertNotNull(savedReservationConfirmation);
        assertEquals(true, savedReservationConfirmation.getEmailSent());
        verify(reservationConfirmationRepository, times(1)).save(any(ReservationConfirmation.class));
    }

    @Test
    void testGetAllConfirmations() {
        when(reservationConfirmationRepository.findAll()).thenReturn(List.of(reservationConfirmation));

        var confirmations = confirmationReservationService.getAllConfirmations();

        assertNotNull(confirmations);
        assertFalse(confirmations.isEmpty());
        verify(reservationConfirmationRepository, times(1)).findAll();
    }

    @Test
    void testGetConfirmationById_Found() {
        when(reservationConfirmationRepository.findById(anyLong())).thenReturn(Optional.of(reservationConfirmation));

        Optional<ReservationConfirmation> foundConfirmation = confirmationReservationService.getConfirmationById(1L);

        assertTrue(foundConfirmation.isPresent());
        assertEquals(1L, foundConfirmation.get().getId());
        verify(reservationConfirmationRepository, times(1)).findById(anyLong());
    }

    @Test
    void testGetConfirmationById_NotFound() {
        when(reservationConfirmationRepository.findById(anyLong())).thenReturn(Optional.empty());

        CustomException thrown = assertThrows(CustomException.class, () -> {
            confirmationReservationService.getConfirmationById(1L);
        });

        assertEquals("ConfirmationReservation with ID 1 not found", thrown.getMessage());
        verify(reservationConfirmationRepository, times(1)).findById(anyLong());
    }

    @Test
    void testDeleteConfirmation_Success() {
        when(reservationConfirmationRepository.existsById(anyLong())).thenReturn(true);

        confirmationReservationService.deleteConfirmation(1L);

        verify(reservationConfirmationRepository, times(1)).deleteById(anyLong());
    }

    @Test
    void testDeleteConfirmation_NotFound() {
        when(reservationConfirmationRepository.existsById(anyLong())).thenReturn(false);

        CustomException thrown = assertThrows(CustomException.class, () -> {
            confirmationReservationService.deleteConfirmation(1L);
        });

        assertEquals("FoodType with ID 1 not found", thrown.getMessage());
        verify(reservationConfirmationRepository, times(1)).existsById(anyLong());
    }

    @Test
    void testUpdateConfirmation_Found() {
        when(reservationConfirmationRepository.existsById(anyLong())).thenReturn(true);
        when(reservationConfirmationRepository.save(any(ReservationConfirmation.class))).thenReturn(reservationConfirmation);

        ReservationConfirmation updatedConfirmation = confirmationReservationService.updateConfirmation(1L, reservationConfirmation);

        assertNotNull(updatedConfirmation);
        assertEquals(1L, updatedConfirmation.getId());
        verify(reservationConfirmationRepository, times(1)).save(any(ReservationConfirmation.class));
    }

    @Test
    void testUpdateConfirmation_NotFound() {
        when(reservationConfirmationRepository.existsById(anyLong())).thenReturn(false);

        ReservationConfirmation updatedConfirmation = confirmationReservationService.updateConfirmation(1L, reservationConfirmation);

        assertNull(updatedConfirmation);
        verify(reservationConfirmationRepository, times(1)).existsById(anyLong());
    }
}