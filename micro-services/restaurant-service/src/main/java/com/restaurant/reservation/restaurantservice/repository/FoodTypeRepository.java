package com.restaurant.reservation.restaurantservice.repository;

import com.restaurant.reservation.restaurantservice.model.FoodType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FoodTypeRepository extends JpaRepository<FoodType, Long> {
    FoodType findByType(String type);
}

