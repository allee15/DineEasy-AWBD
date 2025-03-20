package com.restaurant.reservation.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class ReservationConfirmation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Boolean emailSent;
    private LocalDateTime sentDate;

    @OneToOne
    @JoinColumn(name = "reservation_id")
    private Reservation reservation;

    public ReservationConfirmation() {}

    public ReservationConfirmation(Boolean emailSent, LocalDateTime sentDate) {
        this.emailSent = emailSent;
        this.sentDate = sentDate;
    }

    public Boolean getEmailSent() {
        return emailSent;
    }

    public void setEmailSent(Boolean emailSent) {
        this.emailSent = emailSent;
    }

    public LocalDateTime getSentDate() {
        return sentDate;
    }

    public void setSentDate(LocalDateTime sentDate) {
        this.sentDate = sentDate;
    }

    public void setId(Long id) {

    }

    public Long getId() {
        return id;
    }
}
