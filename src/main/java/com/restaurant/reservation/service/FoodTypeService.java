package com.restaurant.reservation.service;

import com.restaurant.reservation.exception.CustomException;
import com.restaurant.reservation.model.FoodType;
import com.restaurant.reservation.repository.FoodTypeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class FoodTypeService {
    @Autowired
    private FoodTypeRepository foodTypeRepository;

    public FoodType addFoodType(FoodType foodType) {
        log.info("Adding new FoodType: {}", foodType);
        return foodTypeRepository.save(foodType);
    }

    public List<FoodType> getAllFoodTypes() {
        log.info("Fetching all FoodTypes");
        return foodTypeRepository.findAll();
    }

    public Optional<FoodType> getFoodTypeById(Long id) {
        log.info("Fetching FoodType with ID: {}", id);
        return Optional.ofNullable(foodTypeRepository.findById(id)
                .orElseThrow(() -> new CustomException("FoodType with ID " + id + " not found")));
    }

    public void deleteFoodType(Long id) {
        log.info("Deleting FoodType with ID: {}", id);
        foodTypeRepository.deleteById(id);
    }

    public FoodType updateFoodType(Long id, FoodType updatedFoodType) {
        log.info("Updating FoodType with ID: {}", id);
        if (foodTypeRepository.existsById(id)) {
            updatedFoodType.setId(id);
            return foodTypeRepository.save(updatedFoodType);
        }
        return null;
    }
}
