package com.restaurant.reservation.controller;

import com.restaurant.reservation.model.ReservationConfirmation;
import com.restaurant.reservation.service.ConfirmationReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/reservationconfirmations")
public class ReservationConfirmationController {

    @Autowired
    private ConfirmationReservationService confirmationReservationService;

    @PostMapping
    public ResponseEntity<ReservationConfirmation> createReservationConfirmation(@RequestBody ReservationConfirmation reservationConfirmation) {
        ReservationConfirmation savedReservationConfirmation = confirmationReservationService.addReservationConfirmation(reservationConfirmation);
        return ResponseEntity.ok(savedReservationConfirmation);
    }

    @GetMapping
    public List<ReservationConfirmation> getAllReservationConfirmations() {
        return confirmationReservationService.getAllConfirmations();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReservationConfirmation> getReservationConfirmationById(@PathVariable Long id) {
        Optional<ReservationConfirmation> reservationConfirmation = confirmationReservationService.getConfirmationById(id);
        return reservationConfirmation.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReservationConfirmation> updateReservationConfirmation(@PathVariable Long id, @RequestBody ReservationConfirmation updatedReservationConfirmation) {
        ReservationConfirmation updated = confirmationReservationService.updateConfirmation(id, updatedReservationConfirmation);
        return updated != null ? ResponseEntity.ok(updated) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservationConfirmation(@PathVariable Long id) {
        confirmationReservationService.deleteConfirmation(id);
        return ResponseEntity.noContent().build();
    }
}
