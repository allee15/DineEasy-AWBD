package com.restaurant.reservation.controller;

import com.restaurant.reservation.exception.CustomException;
import com.restaurant.reservation.model.*;
import com.restaurant.reservation.repository.ReservationRepository;
import com.restaurant.reservation.service.ConfirmationReservationService;
import com.restaurant.reservation.service.ReservationService;
import com.restaurant.reservation.validator.RestaurantValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Slf4j
@Controller
@RequestMapping("/reservationconfirmations")
public class ReservationConfirmationController {

    @Autowired
    private ConfirmationReservationService confirmationReservationService;

    @Autowired
    private ReservationService reservationService;
    @Autowired
    private ReservationRepository reservationRepository;

    @PostMapping("/confirm/{id}")
    public String confirmReservation(@PathVariable Long id) {
        Optional<Reservation> reservationOpt = reservationService.getReservationById(id);
        if (reservationOpt.isPresent()) {
            Reservation reservation = reservationOpt.get();
            if (reservation.getReservationConfirmation() == null) {
                ReservationConfirmation confirmation = new ReservationConfirmation();
                confirmation.setEmailSent(true);
                confirmation.setSentDate(LocalDateTime.now());
                confirmation.setReservation(reservation);
                confirmationReservationService.addReservationConfirmation(confirmation);
            }
            reservation.setStatus("CONFIRMED");
            reservationService.updateReservation(reservation);
            return "redirect:/reservation/all";
        }
        return "redirect:/reservation/all";
    }


    @PostMapping("/add/confirmation")
    public String createReservationConfirmation(@RequestBody ReservationConfirmation reservationConfirmation, @RequestParam Long reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId).orElseThrow(() -> new CustomException("Reservation not found"));

        ReservationConfirmation confirmation = new ReservationConfirmation();
        confirmation.setEmailSent(true);
        confirmation.setSentDate(LocalDateTime.now());
        confirmation.setReservation(reservation);
        confirmationReservationService.addReservationConfirmation(confirmation);

        return "redirect:/reservation/all";
    }

    @GetMapping
    public List<ReservationConfirmation> getAllReservationConfirmations() {
        return confirmationReservationService.getAllConfirmations();
    }

    @GetMapping("/confirmation-details/{id}")
    public String getReservationConfirmationById(@PathVariable Long id, Model model) {
        ReservationConfirmation reservationConfirmation = confirmationReservationService.getConfirmationById(id)
                .orElseThrow(() -> new CustomException("Not found"));

        Reservation reservation = reservationRepository.findById(id).orElseThrow(() -> new CustomException("Not found"));

        model.addAttribute("reservation", reservation);
        model.addAttribute("reservationConfirmation", reservationConfirmation);
        return "redirect:/reservation/all";
    }

    @PostMapping("/confirmation/{id}")
    public String updateReservationConfirmation(@PathVariable Long id, @RequestParam Long reservationId, @RequestBody ReservationConfirmation updatedReservationConfirmation) {
        log.info("Updating reservation confirmation with ID: " + id);

        Optional<Reservation> reservationOpt = reservationRepository.findById(reservationId);
        if (reservationOpt.isEmpty()) {
            throw new CustomException("Reservation not found");
        }

        updatedReservationConfirmation.setReservation(reservationOpt.get());
        updatedReservationConfirmation.setId(id);

        return "redirect:/reservation/all";
    }

    @PostMapping("/delete-confirmation/{id}")
    public String deleteReservationConfirmation(@PathVariable Long id) {
        confirmationReservationService.deleteConfirmation(id);
        return "redirect:/reservation/all";
    }
}