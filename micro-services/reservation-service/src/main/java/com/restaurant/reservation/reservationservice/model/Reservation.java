package com.restaurant.reservation.reservationservice.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime reservationDate;
    private Integer nbOfPeople;
    private String status;

    @Transient
    private Long userId;

    @Transient
    private Long restaurantId;

    @OneToOne(mappedBy = "reservation")
    private ReservationConfirmation reservationConfirmation;

    public Reservation() {}

    public Reservation(LocalDateTime reservationDate, Integer nbOfPeople, String status) {
        this.reservationDate = reservationDate;
        this.nbOfPeople = nbOfPeople;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getReservationDate() {
        return reservationDate;
    }

    public void setReservationDate(LocalDateTime reservationDate) {
        this.reservationDate = reservationDate;
    }

    public Integer getNbOfPeople() {
        return nbOfPeople;
    }

    public void setNbOfPeople(Integer nbOfPeople) {
        this.nbOfPeople = nbOfPeople;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(Long restaurantId) {
        this.restaurantId = restaurantId;
    }

    public ReservationConfirmation getReservationConfirmation() {
        return reservationConfirmation;
    }

    public void setReservationConfirmation(ReservationConfirmation reservationConfirmation) {
        this.reservationConfirmation = reservationConfirmation;
    }
}

