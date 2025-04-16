package com.restaurant.reservation.restaurantservice.service;

import com.restaurant.reservation.restaurantservice.exception.CustomException;
import com.restaurant.reservation.restaurantservice.model.FoodType;
import com.restaurant.reservation.restaurantservice.repository.FoodTypeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FoodTypeService {
    @Autowired
    private FoodTypeRepository foodTypeRepository;

    public FoodType addFoodType(FoodType foodType) {
        return foodTypeRepository.save(foodType);
    }

    public List<FoodType> getAllFoodTypes() {
        return foodTypeRepository.findAll();
    }

    public Optional<FoodType> getFoodTypeById(Long id) {
        return Optional.ofNullable(foodTypeRepository.findById(id)
                .orElseThrow(() -> new CustomException("FoodType with ID " + id + " not found")));
    }

    public void deleteFoodType(Long id) {
        if (foodTypeRepository.existsById(id)) {
            foodTypeRepository.deleteById(id);
        } else {
            throw new CustomException("FoodType with ID " + id + " not found");
        }
    }

    public FoodType updateFoodType(Long id, FoodType updatedFoodType) {
        if (foodTypeRepository.existsById(id)) {
            updatedFoodType.setId(id);
            return foodTypeRepository.save(updatedFoodType);
        }
        return null;
    }
}

