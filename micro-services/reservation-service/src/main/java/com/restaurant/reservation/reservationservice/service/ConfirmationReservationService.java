package com.restaurant.reservation.reservationservice.service;

import com.restaurant.reservation.reservationservice.exception.CustomException;
import com.restaurant.reservation.reservationservice.model.ReservationConfirmation;
import com.restaurant.reservation.reservationservice.repository.ReservationConfirmationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ConfirmationReservationService {
    @Autowired
    private ReservationConfirmationRepository reservationConfirmationRepository;

    public ReservationConfirmation addReservationConfirmation(ReservationConfirmation reservationConfirmation) {
        return reservationConfirmationRepository.save(reservationConfirmation);
    }

    public List<ReservationConfirmation> getAllConfirmations() {
        return reservationConfirmationRepository.findAll();
    }

    public Optional<ReservationConfirmation> getConfirmationById(Long id) {
        return Optional.ofNullable(reservationConfirmationRepository.findById(id)
                .orElseThrow(() -> new CustomException("ConfirmationReservation with ID " + id + " not found")));
    }

    public void deleteConfirmation(Long id) {
        if (reservationConfirmationRepository.existsById(id)) {
            reservationConfirmationRepository.deleteById(id);
        } else {
            throw new CustomException("FoodType with ID " + id + " not found");
        }
    }
    public ReservationConfirmation updateConfirmation(Long id, ReservationConfirmation updatedConfirmation) {
        if (reservationConfirmationRepository.existsById(id)) {
            updatedConfirmation.setId(id);
            return reservationConfirmationRepository.save(updatedConfirmation);
        }
        return null;
    }
}

