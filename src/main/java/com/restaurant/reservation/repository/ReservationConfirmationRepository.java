package com.restaurant.reservation.repository;

import com.restaurant.reservation.model.ReservationConfirmation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationConfirmationRepository extends JpaRepository<ReservationConfirmation, Long> {
}
