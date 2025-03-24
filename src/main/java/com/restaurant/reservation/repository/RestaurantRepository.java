package com.restaurant.reservation.repository;

import com.restaurant.reservation.model.FoodType;
import com.restaurant.reservation.model.Restaurant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
    Page<Restaurant> findByLocation(String location, Pageable pageable);
    Page<Restaurant> findByFoodType(FoodType foodType, Pageable pageable);
}
