package com.restaurant.reservation.controller;

import com.restaurant.reservation.model.Reservation;
import com.restaurant.reservation.model.Restaurant;
import com.restaurant.reservation.model.User;
import com.restaurant.reservation.service.EmailService;
import com.restaurant.reservation.service.ReservationService;
import com.restaurant.reservation.service.RestaurantService;
import com.restaurant.reservation.service.UserService;
import com.restaurant.reservation.validator.ReservationValidator;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.ui.Model;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class ReservationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ReservationService reservationService;

    @MockitoBean
    private RestaurantService restaurantService;

    @MockitoBean
    private UserService userService;

    @MockitoBean
    private EmailService emailService;

    @MockitoBean
    private Model model;

    @Test
    public void showAddReservationForm_shouldReturnForm_whenRestaurantExists() throws Exception {
        Long restaurantId = 1L;
        Restaurant restaurant = new Restaurant();
        restaurant.setId(restaurantId);
        restaurant.setName("Test Restaurant");

        when(restaurantService.getRestaurantById(restaurantId)).thenReturn(Optional.of(restaurant));

        mockMvc.perform(get("/reservation/new")
                        .param("restaurantId", restaurantId.toString()))
                .andExpect(status().isOk())
                .andExpect(view().name("reservationForm"));

        verify(restaurantService).getRestaurantById(restaurantId);
    }

    @Test
    public void showAddReservationForm_shouldThrowException_whenRestaurantNotFound() throws Exception {
        Long restaurantId = 99L;

        when(restaurantService.getRestaurantById(restaurantId)).thenReturn(Optional.empty());

        mockMvc.perform(get("/reservation/new")
                        .param("restaurantId", restaurantId.toString()))
                .andExpect(status().isInternalServerError());
    }

    @Test
    public void createReservation_shouldCreateAndRedirect_whenValidData() throws Exception {
        Long restaurantId = 1L;
        Long userId = 2L;
        String reservationDate = "2025-06-03T14:00:00";
        int nbOfPeople = 4;
        String status = "PENDING";

        Restaurant restaurant = new Restaurant();
        restaurant.setId(restaurantId);
        restaurant.setName("Test Restaurant");

        User user = new User();
        user.setId(userId);
        user.setEmail("test@example.com");

        when(restaurantService.getRestaurantById(restaurantId)).thenReturn(Optional.of(restaurant));
        when(userService.getUserById(userId)).thenReturn(Optional.of(user));

        try (var mockedValidator = mockStatic(ReservationValidator.class)) {
            mockedValidator.when(() -> ReservationValidator.isValidReservation(any())).thenReturn(true);

            mockMvc.perform(post("/reservation")
                            .param("restaurantId", restaurantId.toString())
                            .param("userId", userId.toString())
                            .param("reservationDate", reservationDate)
                            .param("nbOfPeople", String.valueOf(nbOfPeople))
                            .param("status", status))
                    .andExpect(status().is3xxRedirection())
                    .andExpect(redirectedUrl("/restaurants"));

            ArgumentCaptor<Reservation> captor = ArgumentCaptor.forClass(Reservation.class);
            verify(reservationService).addReservation(captor.capture());
            Reservation savedReservation = captor.getValue();

            assertEquals(nbOfPeople, savedReservation.getNbOfPeople());
            assertEquals(status, savedReservation.getStatus());
            assertEquals(restaurant, savedReservation.getRestaurant());
            assertEquals(user, savedReservation.getUser());
            assertEquals(LocalDateTime.parse(reservationDate), savedReservation.getReservationDate());

            verify(emailService).sendReservationConfirmation(eq(user.getEmail()), contains("Restaurant: " + restaurant.getName()));
        }
    }

    @Test
    public void createReservation_shouldThrowException_whenRestaurantNotFound() throws Exception {
        Long restaurantId = 99L;
        Long userId = 1L;

        when(restaurantService.getRestaurantById(restaurantId)).thenReturn(Optional.empty());

        mockMvc.perform(post("/reservation")
                        .param("restaurantId", restaurantId.toString())
                        .param("userId", userId.toString())
                        .param("reservationDate", "2025-06-03T14:00:00")
                        .param("nbOfPeople", "2")
                        .param("status", "PENDING"))
                .andExpect(status().isInternalServerError());

        verify(reservationService, never()).addReservation(any());
        verify(emailService, never()).sendReservationConfirmation(any(), any());
    }

    @Test
    public void createReservation_shouldThrowException_whenUserNotFound() throws Exception {
        Long restaurantId = 1L;
        Long userId = 99L;

        Restaurant restaurant = new Restaurant();
        restaurant.setId(restaurantId);

        when(restaurantService.getRestaurantById(restaurantId)).thenReturn(Optional.of(restaurant));
        when(userService.getUserById(userId)).thenReturn(Optional.empty());

        mockMvc.perform(post("/reservation")
                        .param("restaurantId", restaurantId.toString())
                        .param("userId", userId.toString())
                        .param("reservationDate", "2025-06-03T14:00:00")
                        .param("nbOfPeople", "2")
                        .param("status", "PENDING"))
                .andExpect(status().isInternalServerError());

        verify(reservationService, never()).addReservation(any());
        verify(emailService, never()).sendReservationConfirmation(any(), any());
    }

    @Test
    public void createReservation_shouldThrowException_whenInvalidReservation() throws Exception {
        Long restaurantId = 1L;
        Long userId = 2L;

        Restaurant restaurant = new Restaurant();
        restaurant.setId(restaurantId);

        User user = new User();
        user.setId(userId);

        when(restaurantService.getRestaurantById(restaurantId)).thenReturn(Optional.of(restaurant));
        when(userService.getUserById(userId)).thenReturn(Optional.of(user));

        try (var mockedValidator = mockStatic(ReservationValidator.class)) {
            mockedValidator.when(() -> ReservationValidator.isValidReservation(any())).thenReturn(false);

            mockMvc.perform(post("/reservation")
                            .param("restaurantId", restaurantId.toString())
                            .param("userId", userId.toString())
                            .param("reservationDate", "2025-06-03T14:00:00")
                            .param("nbOfPeople", "2")
                            .param("status", "PENDING"))
                    .andExpect(status().isInternalServerError());

            verify(reservationService, never()).addReservation(any());
            verify(emailService, never()).sendReservationConfirmation(any(), any());
        }
    }

    @Test
    public void getAllReservations_shouldAddListToModelAndReturnView() throws Exception {
        List<Reservation> reservations = List.of(new Reservation(), new Reservation());
        when(reservationService.getAllReservations()).thenReturn(reservations);

        mockMvc.perform(get("/reservation/all"))
                .andExpect(status().isOk())
                .andExpect(view().name("reservations"));

        verify(reservationService).getAllReservations();
    }

    @Test
    public void deleteReservation_shouldDeleteAndRedirect_whenReservationExists() throws Exception {
        Long reservationId = 1L;
        Reservation reservation = new Reservation();
        reservation.setId(reservationId);

        when(reservationService.getReservationById(reservationId)).thenReturn(Optional.of(reservation));

        mockMvc.perform(get("/reservation/delete/{id}", reservationId))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/reservation/all"));

        verify(reservationService).deleteReservation(reservationId);
    }

    @Test
    public void deleteReservation_shouldThrowException_whenReservationNotFound() throws Exception {
        Long reservationId = 99L;

        when(reservationService.getReservationById(reservationId)).thenReturn(Optional.empty());

        mockMvc.perform(get("/reservation/delete/{id}", reservationId))
                .andExpect(status().isInternalServerError());

        verify(reservationService, never()).deleteReservation(anyLong());
    }
}
