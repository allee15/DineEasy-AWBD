package com.restaurant.reservation.repository;

import com.restaurant.reservation.model.FoodType;
import com.restaurant.reservation.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
    List<Restaurant> findByLocation(String location);
    List<Restaurant> findByFoodType(FoodType foodType);
}
