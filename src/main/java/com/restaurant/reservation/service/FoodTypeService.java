package com.restaurant.reservation.service;

import com.restaurant.reservation.model.FoodType;
import com.restaurant.reservation.repository.FoodTypeRepository;
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
        return foodTypeRepository.findById(id);
    }

    public void deleteFoodType(Long id) {
        foodTypeRepository.deleteById(id);
    }

    public FoodType updateFoodType(Long id, FoodType updatedFoodType) {
        if (foodTypeRepository.existsById(id)) {
            updatedFoodType.setId(id);
            return foodTypeRepository.save(updatedFoodType);
        }
        return null;
    }
}
