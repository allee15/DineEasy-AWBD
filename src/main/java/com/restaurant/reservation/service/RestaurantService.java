package com.restaurant.reservation.service;

import com.restaurant.reservation.exception.CustomException;
import com.restaurant.reservation.model.FoodType;
import com.restaurant.reservation.model.Restaurant;
import com.restaurant.reservation.repository.RestaurantRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
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

    public Page<Restaurant> getAllRestaurants(int page, int size) {
        log.info("Fetching all Restaurants - Page: {}, Size: {}", page, size);
        org.springframework.data.domain.Pageable pageable = PageRequest.of(page, size);
        return restaurantRepository.findAll(pageable);
    }

    public Page<Restaurant> getRestaurantsByLocation(String location, int page, int size) {
        log.info("Fetching Restaurants by Location: {} - Page: {}, Size: {}", location, page, size);
        org.springframework.data.domain.Pageable pageable = PageRequest.of(page, size);
        return restaurantRepository.findByLocation(location, pageable);
    }

    public Page<Restaurant> getRestaurantsByFoodType(FoodType foodType, int page, int size) {
        log.info("Fetching Restaurants by FoodType: {} - Page: {}, Size: {}", foodType, page, size);
        org.springframework.data.domain.Pageable pageable = PageRequest.of(page, size);
        return restaurantRepository.findByFoodType(foodType, pageable);
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
        if (restaurantRepository.existsById(id)) {
            restaurantRepository.deleteById(id);
        } else {
            throw new CustomException("Restaurant with ID " + id + " not found");
        }
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
