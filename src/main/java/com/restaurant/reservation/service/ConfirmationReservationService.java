package com.restaurant.reservation.service;

import com.restaurant.reservation.model.ReservationConfirmation;
import com.restaurant.reservation.repository.ReservationConfirmationRepository;
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
        return reservationConfirmationRepository.findById(id);
    }

        public void deleteConfirmation(Long id) {
        reservationConfirmationRepository.deleteById(id);
    }

        public ReservationConfirmation updateConfirmation(Long id, ReservationConfirmation updatedConfirmation) {
        if (reservationConfirmationRepository.existsById(id)) {
            updatedConfirmation.setId(id);
            return reservationConfirmationRepository.save(updatedConfirmation);
        }
        return null;
    }
}
