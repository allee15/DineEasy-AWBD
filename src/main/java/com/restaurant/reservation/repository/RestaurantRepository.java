package com.restaurant.reservation.repository;

import com.restaurant.reservation.model.FoodType;
import com.restaurant.reservation.model.Restaurant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
    @EntityGraph(attributePaths = {"foodType"})
    Page<Restaurant> findByLocation(String location, Pageable pageable);
    @EntityGraph(attributePaths = {"foodType"})
    Page<Restaurant> findByFoodType(FoodType foodType, Pageable pageable);
    @EntityGraph(attributePaths = {"foodType"})
    List<Restaurant> findAll();
}
