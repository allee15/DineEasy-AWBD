package com.restaurant.reservation.controller;

import com.restaurant.reservation.model.Reservation;
import com.restaurant.reservation.model.ReservationConfirmation;
import com.restaurant.reservation.service.ConfirmationReservationService;
import com.restaurant.reservation.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/reservationconfirmations")
public class ReservationConfirmationController {

    @Autowired
    private ConfirmationReservationService confirmationReservationService;

    @Autowired
    private ReservationService reservationService;

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


    @PostMapping //TODO
    public ResponseEntity<ReservationConfirmation> createReservationConfirmation(@RequestBody ReservationConfirmation reservationConfirmation) {
        ReservationConfirmation savedReservationConfirmation = confirmationReservationService.addReservationConfirmation(reservationConfirmation);
        return ResponseEntity.ok(savedReservationConfirmation);
    }

    @GetMapping
    public List<ReservationConfirmation> getAllReservationConfirmations() {
        return confirmationReservationService.getAllConfirmations();
    }

    @GetMapping("/{id}") //TODO
    public ResponseEntity<ReservationConfirmation> getReservationConfirmationById(@PathVariable Long id) {
        Optional<ReservationConfirmation> reservationConfirmation = confirmationReservationService.getConfirmationById(id);
        return reservationConfirmation.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}") //TODO
    public ResponseEntity<ReservationConfirmation> updateReservationConfirmation(@PathVariable Long id, @RequestBody ReservationConfirmation updatedReservationConfirmation) {
        ReservationConfirmation updated = confirmationReservationService.updateConfirmation(id, updatedReservationConfirmation);
        return updated != null ? ResponseEntity.ok(updated) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}") //TODO
    public ResponseEntity<Void> deleteReservationConfirmation(@PathVariable Long id) {
        confirmationReservationService.deleteConfirmation(id);
        return ResponseEntity.noContent().build();
    }
}
