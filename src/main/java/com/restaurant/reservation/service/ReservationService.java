package com.restaurant.reservation.service;

import com.restaurant.reservation.exception.CustomException;
import com.restaurant.reservation.model.Reservation;
import com.restaurant.reservation.repository.ReservationRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class ReservationService {
    @Autowired
    private ReservationRepository reservationRepository;

    public void addReservation(Reservation reservation) {
        log.info("Adding new Reservation: {}", reservation);
        reservationRepository.save(reservation);
    }

    public List<Reservation> getAllReservations() {
        log.info("Fetching all Reservations");
        return reservationRepository.findAll();
    }

    public Optional<Reservation> getReservationById(Long id) {
        log.info("Fetching Reservation with ID: {}", id);
        return Optional.ofNullable(reservationRepository.findById(id)
                .orElseThrow(() -> new CustomException("Reservation with ID " + id + " not found")));
    }

    public void deleteReservation(Long id) {
        log.info("Deleting Reservation with ID: {}", id);
        if (reservationRepository.existsById(id)) {
            reservationRepository.deleteById(id);
        } else {
            throw new CustomException("Reservation with ID " + id + " not found");
        }
        reservationRepository.deleteById(id);
    }

    public Reservation updateReservation(Reservation updatedReservation) {
        log.info("Updating Reservation with ID: {}", updatedReservation.getId());
        if (reservationRepository.existsById(updatedReservation.getId())) {
            updatedReservation.setId(updatedReservation.getId());
            return reservationRepository.save(updatedReservation);
        }
        return null;
    }
}
