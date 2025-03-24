package com.restaurant.reservation.controller;

import com.restaurant.reservation.service.ReservationService;
import org.junit.jupiter.api.Test;
import com.restaurant.reservation.model.Reservation;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(ReservationController.class)
public class ReservationControllerTest {

    @Mock
    private ReservationService reservationService;

    @InjectMocks
    private ReservationController reservationController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(reservationController).build();
    }

    @Test
    public void testCreateReservation() throws Exception {
        Reservation reservation = new Reservation();
        when(reservationService.addReservation(any(Reservation.class)))
                .thenReturn(reservation);

        mockMvc.perform(post("/api/reservations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetAllReservations() throws Exception {
        when(reservationService.getAllReservations()).thenReturn(Collections.singletonList(new Reservation()));

        mockMvc.perform(get("/api/reservations"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetReservationById() throws Exception {
        Reservation reservation = new Reservation();
        reservation.setId(1L);
        when(reservationService.getReservationById(1L)).thenReturn(Optional.of(reservation));

        mockMvc.perform(get("/api/reservations/1"))
                .andExpect(status().isOk());
    }

    @Test
    public void testUpdateReservation() throws Exception {
        Reservation reservation = new Reservation();
        when(reservationService.updateReservation(1L, any(Reservation.class)))
                .thenReturn(reservation);

        mockMvc.perform(put("/api/reservations/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isOk());
    }

    @Test
    public void testDeleteReservation() throws Exception {
        doNothing().when(reservationService).deleteReservation(1L);

        mockMvc.perform(delete("/api/reservations/1"))
                .andExpect(status().isNoContent());
    }
}
