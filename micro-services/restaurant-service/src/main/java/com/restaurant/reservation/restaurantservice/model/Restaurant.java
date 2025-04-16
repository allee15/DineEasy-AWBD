package com.restaurant.reservation.restaurantservice.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class Restaurant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String location;

    @ManyToOne
    @JoinColumn(name = "food_type_id", nullable = false)
    private FoodType foodType;

    @Transient
    private List<Long> reservationIds;

    @Transient
    private List<Long> reviewIds;

    @Transient
    private List<Long> menuIds;

    public Restaurant() {}

    public Restaurant(String name, String location, FoodType foodType) {
        this.name = name;
        this.location = location;
        this.foodType = foodType;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Long getId() {
        return id;
    }

    public FoodType getFoodType() {
        return foodType;
    }

    public void setFoodType(FoodType foodType) {
        this.foodType = foodType;
    }

    public List<Long> getReservationIds() {
        return reservationIds;
    }

    public void setReservationIds(List<Long> reservationIds) {
        this.reservationIds = reservationIds;
    }

    public List<Long> getReviewIds() {
        return reviewIds;
    }

    public void setReviewIds(List<Long> reviewIds) {
        this.reviewIds = reviewIds;
    }

    public List<Long> getMenuIds() {
        return menuIds;
    }

    public void setMenuIds(List<Long> menuIds) {
        this.menuIds = menuIds;
    }
}

