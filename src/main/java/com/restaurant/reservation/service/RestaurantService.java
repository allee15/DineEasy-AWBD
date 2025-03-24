package com.restaurant.reservation.service;

import com.restaurant.reservation.exception.CustomException;
import com.restaurant.reservation.model.Restaurant;
import com.restaurant.reservation.repository.RestaurantRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class RestaurantService {
    @Autowired
    private RestaurantRepository restaurantRepository;

    public Restaurant addRestaurant(Restaurant restaurant) {
        log.info("Adding new Restaurant: {}", restaurant);
        return restaurantRepository.save(restaurant);
    }

    public List<Restaurant> getAllRestaurants() {
        log.info("Fetching all Restaurants");
        return restaurantRepository.findAll();
    }

    public Optional<Restaurant> getRestaurantById(Long id) {
        log.info("Fetching Restaurant with ID: {}", id);
        return Optional.ofNullable(restaurantRepository.findById(id)
                .orElseThrow(() -> new CustomException("Restaurant with ID " + id + " not found")));
    }

    public void deleteRestaurant(Long id) {
        log.info("Deleting Restaurant with ID: {}", id);
        restaurantRepository.deleteById(id);
    }

    public Restaurant updateRestaurant(Long id, Restaurant updatedRestaurant) {
        log.info("Updating Restaurant with ID: {}", id);
        if (restaurantRepository.existsById(id)) {
            updatedRestaurant.setId(id);
            return restaurantRepository.save(updatedRestaurant);
        }
        return null;
    }
}
