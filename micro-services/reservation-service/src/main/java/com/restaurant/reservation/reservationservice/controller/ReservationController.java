package com.restaurant.reservation.reservationservice.controller;

import com.restaurant.reservation.reservationservice.exception.CustomException;
import com.restaurant.reservation.reservationservice.validator.ReservationValidator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import com.restaurant.reservation.reservationservice.model.Reservation;
import com.restaurant.reservation.reservationservice.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/reservation")
public class ReservationController {

    @Autowired
    private ReservationService reservationService;

    @GetMapping("/new")
    public String showAddReservationForm(@RequestParam("restaurantId") Long restaurantId, Model model) {

        return "reservationForm";
    }

    @PostMapping
    public String createReservation(@RequestParam Long restaurantId,
                                    @RequestParam Long userId,
                                    @RequestParam String reservationDate,
                                    @RequestParam Integer nbOfPeople,
                                    @RequestParam String status) {
        LocalDateTime reservationDateTime = LocalDateTime.parse(reservationDate);

        Reservation reservation = new Reservation();
        reservation.setReservationDate(reservationDateTime);
        reservation.setNbOfPeople(nbOfPeople);
        reservation.setStatus(status);
        reservation.setRestaurantId(restaurantId);
        reservation.setUserId(userId);

        if (!ReservationValidator.isValidReservation(reservation)) {
            throw new CustomException("Invalid reservation");
        }

        reservationService.addReservation(reservation);

        return "redirect:/restaurants";
    }

    @GetMapping("/all")
    public String getAllReservations(Model model) {

        List<Reservation> reservations = reservationService.getAllReservations();
        model.addAttribute("reservations", reservations);

        return "reservations";
    }

    @GetMapping("/delete/{id}")
    public String deleteReservation(@PathVariable Long id) {
        Optional<Reservation> reservationOptional = reservationService.getReservationById(id);
        if (reservationOptional.isEmpty()) {
            throw new CustomException("Reservation not found.");
        }

        reservationService.deleteReservation(id);

        return "redirect:/reservation/all";
    }
}

