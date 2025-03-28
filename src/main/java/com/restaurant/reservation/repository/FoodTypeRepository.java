package com.restaurant.reservation.repository;

import com.restaurant.reservation.model.FoodType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Repository
public interface FoodTypeRepository extends JpaRepository<FoodType, Long> {
    FoodType findByType(String type);
}

