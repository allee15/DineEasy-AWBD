package com.restaurant.reservation.reservationservice.service;

import com.restaurant.reservation.reservationservice.exception.CustomException;
import com.restaurant.reservation.reservationservice.model.Reservation;
import com.restaurant.reservation.reservationservice.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReservationService {
    @Autowired
    private ReservationRepository reservationRepository;

    public void addReservation(Reservation reservation) {
        reservationRepository.save(reservation);
    }

    public List<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }

    public Optional<Reservation> getReservationById(Long id) {
        return Optional.ofNullable(reservationRepository.findById(id)
                .orElseThrow(() -> new CustomException("Reservation with ID " + id + " not found")));
    }

    public void deleteReservation(Long id) {
        if (reservationRepository.existsById(id)) {
            reservationRepository.deleteById(id);
        } else {
            throw new CustomException("Reservation with ID " + id + " not found");
        }
        reservationRepository.deleteById(id);
    }

    public Reservation updateReservation(Reservation updatedReservation) {
        if (reservationRepository.existsById(updatedReservation.getId())) {
            updatedReservation.setId(updatedReservation.getId());
            return reservationRepository.save(updatedReservation);
        }
        return null;
    }
}

