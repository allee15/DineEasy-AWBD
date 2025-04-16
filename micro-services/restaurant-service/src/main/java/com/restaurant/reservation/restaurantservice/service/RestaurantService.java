package com.restaurant.reservation.restaurantservice.service;

import com.restaurant.reservation.restaurantservice.exception.CustomException;
import com.restaurant.reservation.restaurantservice.model.FoodType;
import com.restaurant.reservation.restaurantservice.model.Restaurant;
import com.restaurant.reservation.restaurantservice.repository.RestaurantRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import com.restaurant.reservation.restaurantservice.utils.Pagination;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RestaurantService {
    @Autowired
    private RestaurantRepository restaurantRepository;

    public Restaurant addRestaurant(Restaurant restaurant) {
        return restaurantRepository.save(restaurant);
    }

    public Pagination<Restaurant> getAllRestaurants(int page) {
        List<Restaurant> restaurants = restaurantRepository.findAll();
        return new Pagination<>(restaurants, page, 20);
    }

    public Page<Restaurant> getRestaurantsByLocation(String location, int page, int size) {
        org.springframework.data.domain.Pageable pageable = PageRequest.of(page, size);
        return restaurantRepository.findByLocation(location, pageable);
    }

    public Page<Restaurant> getRestaurantsByFoodType(FoodType foodType, int page, int size) {
        org.springframework.data.domain.Pageable pageable = PageRequest.of(page, size);
        return restaurantRepository.findByFoodType(foodType, pageable);
    }

    public Optional<Restaurant> getRestaurantById(Long id) {
        return Optional.ofNullable(restaurantRepository.findById(id)
                .orElseThrow(() -> new CustomException("Restaurant with ID " + id + " not found")));
    }

    public void deleteRestaurant(Long id) {
        if (restaurantRepository.existsById(id)) {
            restaurantRepository.deleteById(id);
        } else {
            throw new CustomException("Restaurant with ID " + id + " not found");
        }
    }

    public Restaurant updateRestaurant(Long id, Restaurant updatedRestaurant) {
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

