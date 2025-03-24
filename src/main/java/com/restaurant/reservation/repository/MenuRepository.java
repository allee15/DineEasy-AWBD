package com.restaurant.reservation.repository;

import com.restaurant.reservation.model.Menu;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MenuRepository extends JpaRepository<Menu, Long> {
    Page<Menu> findByRestaurantId(Long restaurantId, Pageable pageable);
}
