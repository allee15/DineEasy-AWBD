package com.restaurant.reservation.controller;

import com.restaurant.reservation.model.Reservation;
import com.restaurant.reservation.model.ReservationConfirmation;
import com.restaurant.reservation.repository.ReservationRepository;
import com.restaurant.reservation.service.ConfirmationReservationService;
import com.restaurant.reservation.service.ReservationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.ui.Model;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class ReservationConfirmationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ConfirmationReservationService confirmationReservationService;

    @MockitoBean
    private ReservationService reservationService;

    @MockitoBean
    private ReservationRepository reservationRepository;

    @MockitoBean
    private Model model;

    @Test
    public void confirmReservation_shouldConfirmAndRedirect_whenReservationExistsAndNotConfirmed() throws Exception {
        Long reservationId = 1L;
        Reservation reservation = new Reservation();
        reservation.setId(reservationId);
        reservation.setStatus("PENDING");
        reservation.setReservationConfirmation(null);

        when(reservationService.getReservationById(reservationId)).thenReturn(Optional.of(reservation));

        mockMvc.perform(post("/reservationconfirmations/confirm/{id}", reservationId))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/reservation/all"));

        verify(confirmationReservationService).addReservationConfirmation(any(ReservationConfirmation.class));
        verify(reservationService).updateReservation(argThat(r -> "CONFIRMED".equals(r.getStatus())));
    }

    @Test
    public void confirmReservation_shouldRedirect_whenReservationExistsAndAlreadyConfirmed() throws Exception {
        Long reservationId = 2L;
        ReservationConfirmation confirmation = new ReservationConfirmation();
        confirmation.setId(1L);
        Reservation reservation = new Reservation();
        reservation.setId(reservationId);
        reservation.setStatus("CONFIRMED");
        reservation.setReservationConfirmation(confirmation);

        when(reservationService.getReservationById(reservationId)).thenReturn(Optional.of(reservation));

        mockMvc.perform(post("/reservationconfirmations/confirm/{id}", reservationId))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/reservation/all"));

        verify(confirmationReservationService, never()).addReservationConfirmation(any());
        verify(reservationService).updateReservation(any());
    }

    @Test
    public void confirmReservation_shouldRedirect_whenReservationNotFound() throws Exception {
        Long reservationId = 99L;

        when(reservationService.getReservationById(reservationId)).thenReturn(Optional.empty());

        mockMvc.perform(post("/reservationconfirmations/confirm/{id}", reservationId))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/reservation/all"));

        verifyNoInteractions(confirmationReservationService);
        verify(reservationService, never()).updateReservation(any());
    }

    @Test
    public void createReservationConfirmation_shouldCreateAndRedirect_whenReservationExists() throws Exception {
        Long reservationId = 1L;
        Reservation reservation = new Reservation();
        reservation.setId(reservationId);

        when(reservationRepository.findById(reservationId)).thenReturn(Optional.of(reservation));

        String jsonBody = """
                {
                    "emailSent": true,
                    "sentDate": "2025-06-03T12:00:00"
                }
                """;

        mockMvc.perform(post("/reservationconfirmations/add/confirmation")
                        .param("reservationId", reservationId.toString())
                        .contentType("application/json")
                        .content(jsonBody))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/reservation/all"));

        verify(confirmationReservationService).addReservationConfirmation(any(ReservationConfirmation.class));
    }

    @Test
    public void createReservationConfirmation_shouldThrowException_whenReservationNotFound() throws Exception {
        Long reservationId = 99L;
        when(reservationRepository.findById(reservationId)).thenReturn(Optional.empty());

        String jsonBody = """
                {
                    "emailSent": true,
                    "sentDate": "2025-06-03T12:00:00"
                }
                """;

        mockMvc.perform(post("/reservationconfirmations/add/confirmation")
                        .param("reservationId", reservationId.toString())
                        .contentType("application/json")
                        .content(jsonBody))
                .andExpect(status().isInternalServerError());
    }

    @Test
    public void getAllReservationConfirmations_shouldReturnList() throws Exception {
        List<ReservationConfirmation> confirmations = List.of(new ReservationConfirmation(), new ReservationConfirmation());
        when(confirmationReservationService.getAllConfirmations()).thenReturn(confirmations);

        mockMvc.perform(get("/reservationconfirmations"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(confirmations.size()));
    }

    @Test
    public void getReservationConfirmationById_shouldRedirectWithModel_whenExists() throws Exception {
        Long id = 1L;
        ReservationConfirmation confirmation = new ReservationConfirmation();
        confirmation.setId(id);
        Reservation reservation = new Reservation();
        reservation.setId(id);

        when(confirmationReservationService.getConfirmationById(id)).thenReturn(Optional.of(confirmation));
        when(reservationRepository.findById(id)).thenReturn(Optional.of(reservation));

        mockMvc.perform(get("/reservationconfirmations/confirmation-details/{id}", id))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/reservation/all"));
    }

    @Test
    public void getReservationConfirmationById_shouldThrowException_whenNotFound() throws Exception {
        Long id = 999L;
        when(confirmationReservationService.getConfirmationById(id)).thenReturn(Optional.empty());

        mockMvc.perform(get("/reservationconfirmations/confirmation-details/{id}", id))
                .andExpect(status().isInternalServerError());
    }

    @Test
    public void updateReservationConfirmation_shouldRedirect_whenReservationExists() throws Exception {
        Long id = 1L;
        Long reservationId = 2L;
        Reservation reservation = new Reservation();
        reservation.setId(reservationId);

        when(reservationRepository.findById(reservationId)).thenReturn(Optional.of(reservation));

        String jsonBody = """
                {
                    "emailSent": true,
                    "sentDate": "2025-06-03T12:00:00"
                }
                """;

        mockMvc.perform(post("/reservationconfirmations/confirmation/{id}", id)
                        .param("reservationId", reservationId.toString())
                        .contentType("application/json")
                        .content(jsonBody))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/reservation/all"));
    }

    @Test
    public void updateReservationConfirmation_shouldThrowException_whenReservationNotFound() throws Exception {
        Long id = 1L;
        Long reservationId = 99L;

        when(reservationRepository.findById(reservationId)).thenReturn(Optional.empty());

        String jsonBody = """
                {
                    "emailSent": true,
                    "sentDate": "2025-06-03T12:00:00"
                }
                """;

        mockMvc.perform(post("/reservationconfirmations/confirmation/{id}", id)
                        .param("reservationId", reservationId.toString())
                        .contentType("application/json")
                        .content(jsonBody))
                .andExpect(status().isInternalServerError());
    }

    @Test
    public void deleteReservationConfirmation_shouldDeleteAndRedirect() throws Exception {
        Long id = 1L;

        mockMvc.perform(post("/reservationconfirmations/delete-confirmation/{id}", id))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/reservation/all"));

        verify(confirmationReservationService).deleteConfirmation(id);
    }
}
