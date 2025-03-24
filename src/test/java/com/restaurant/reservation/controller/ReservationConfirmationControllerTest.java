package com.restaurant.reservation.controller;

import com.restaurant.reservation.model.ReservationConfirmation;
import com.restaurant.reservation.service.ConfirmationReservationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ReservationConfirmationController.class)
public class ReservationConfirmationControllerTest {

    @Mock
    private ConfirmationReservationService confirmationReservationService;

    @InjectMocks
    private ReservationConfirmationController reservationConfirmationController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(reservationConfirmationController).build();
    }

    @Test
    public void testCreateReservationConfirmation() throws Exception {
        ReservationConfirmation reservationConfirmation = new ReservationConfirmation(true, LocalDateTime.now());
        when(confirmationReservationService.addReservationConfirmation(any(ReservationConfirmation.class)))
                .thenReturn(reservationConfirmation);

        mockMvc.perform(post("/api/reservationconfirmations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"status\": \"Confirmed\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("Confirmed"));
    }

    @Test
    public void testGetAllReservationConfirmations() throws Exception {
        when(confirmationReservationService.getAllConfirmations()).thenReturn(Collections.singletonList(new ReservationConfirmation(true, LocalDateTime.now())));

        mockMvc.perform(get("/api/reservationconfirmations"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].status").value("Confirmed"));
    }

    @Test
    public void testGetReservationConfirmationById() throws Exception {
        ReservationConfirmation reservationConfirmation = new ReservationConfirmation(true, LocalDateTime.now());
        reservationConfirmation.setId(1L);
        when(confirmationReservationService.getConfirmationById(1L)).thenReturn(Optional.of(reservationConfirmation));

        mockMvc.perform(get("/api/reservationconfirmations/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("Confirmed"));
    }

    @Test
    public void testUpdateReservationConfirmation() throws Exception {
        ReservationConfirmation reservationConfirmation = new ReservationConfirmation(true, LocalDateTime.now());
        when(confirmationReservationService.updateConfirmation(1L, any(ReservationConfirmation.class)))
                .thenReturn(reservationConfirmation);

        mockMvc.perform(put("/api/reservationconfirmations/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"status\": \"Confirmed\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("Confirmed"));
    }

    @Test
    public void testDeleteReservationConfirmation() throws Exception {
        doNothing().when(confirmationReservationService).deleteConfirmation(1L);

        mockMvc.perform(delete("/api/reservationconfirmations/1"))
                .andExpect(status().isNoContent());
    }
}
