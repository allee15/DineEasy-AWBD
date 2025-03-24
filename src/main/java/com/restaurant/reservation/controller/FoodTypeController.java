package com.restaurant.reservation.controller;

import com.restaurant.reservation.model.FoodType;
import com.restaurant.reservation.service.FoodTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/foodtypes")
public class FoodTypeController {

    @Autowired
    private FoodTypeService foodTypeService;

    @PostMapping
    public ResponseEntity<FoodType> createFoodType(@RequestBody FoodType foodType) {
        FoodType savedFoodType = foodTypeService.addFoodType(foodType);
        return ResponseEntity.ok(savedFoodType);
    }

    @GetMapping
    public ResponseEntity<Page<FoodType>> getAllFoodTypes(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Page<FoodType> foodTypesPage = foodTypeService.getAllFoodTypes(page, size);
        return ResponseEntity.ok(foodTypesPage);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FoodType> getFoodTypeById(@PathVariable Long id) {
        Optional<FoodType> foodType = foodTypeService.getFoodTypeById(id);
        return foodType.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<FoodType> updateFoodType(@PathVariable Long id, @RequestBody FoodType updatedFoodType) {
        FoodType updated = foodTypeService.updateFoodType(id, updatedFoodType);
        return updated != null ? ResponseEntity.ok(updated) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFoodType(@PathVariable Long id) {
        foodTypeService.deleteFoodType(id);
        return ResponseEntity.noContent().build();
    }
}
