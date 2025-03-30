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

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
        return restaurantRepository.findAll();
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

    public List<Restaurant> searchRestaurants(String searchQuery, String sortBy) {
        List<Restaurant> allRestaurants = restaurantRepository.findAll();

        if (searchQuery != null && !searchQuery.isEmpty()) {
            allRestaurants = allRestaurants.stream()
                    .filter(r -> r.getName().contains(searchQuery) || r.getLocation().contains(searchQuery))
                    .collect(Collectors.toList());
        }

        switch (sortBy) {
            case "location":
                allRestaurants.sort(Comparator.comparing(Restaurant::getLocation));
                break;
            case "cuisine":
                allRestaurants.sort(Comparator.comparing(r -> r.getFoodType().getType()));
                break;
            default:
                allRestaurants.sort(Comparator.comparing(Restaurant::getName));
        }

        return allRestaurants;
    }
}
