package com.restaurant.reservation.service;

import com.restaurant.reservation.exception.CustomException;
import com.restaurant.reservation.model.ReservationConfirmation;
import com.restaurant.reservation.repository.ReservationConfirmationRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class ConfirmationReservationService {
        @Autowired
        private ReservationConfirmationRepository reservationConfirmationRepository;

        public ReservationConfirmation addReservationConfirmation(ReservationConfirmation reservationConfirmation) {
            log.info("Adding new ReservationConfirmation: {}", reservationConfirmation);
            return reservationConfirmationRepository.save(reservationConfirmation);
    }

        public List<ReservationConfirmation> getAllConfirmations() {
            log.info("Fetching all ReservationConfirmations");
            return reservationConfirmationRepository.findAll();
    }

        public Optional<ReservationConfirmation> getConfirmationById(Long id) {
            log.info("Fetching ReservationConfirmation with ID: {}", id);
            return Optional.ofNullable(reservationConfirmationRepository.findById(id)
                .orElseThrow(() -> new CustomException("ConfirmationReservation with ID " + id + " not found")));
    }

        public void deleteConfirmation(Long id) {
            log.info("Deleting ReservationConfirmation with ID: {}", id);
            if (reservationConfirmationRepository.existsById(id)) {
                reservationConfirmationRepository.deleteById(id);
            } else {
                throw new CustomException("FoodType with ID " + id + " not found");
            }
    }
        public ReservationConfirmation updateConfirmation(Long id, ReservationConfirmation updatedConfirmation) {
            log.info("Updating ReservationConfirmation with ID: {}", id);
            if (reservationConfirmationRepository.existsById(id)) {
                updatedConfirmation.setId(id);
                return reservationConfirmationRepository.save(updatedConfirmation);
            }
            return null;
        }
}
