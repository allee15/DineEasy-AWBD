package com.restaurant.reservation.restaurantservice.controller;

import com.restaurant.reservation.restaurantservice.model.FoodType;
import com.restaurant.reservation.restaurantservice.service.FoodTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
@RequestMapping("/foodtypes")
public class FoodTypeController {

    @Autowired
    private FoodTypeService foodTypeService;

    @GetMapping("/all")
    public String getAllFoodTypes(Model model) {
        List<FoodType> foodTypes = foodTypeService.getAllFoodTypes();
        model.addAttribute("foodTypes", foodTypes);
        return "foodTypesList";
    }

    @PostMapping("/add")
    public String addFoodType(@RequestParam String name) {
        FoodType foodType = new FoodType();
        foodType.setType(name);
        foodTypeService.addFoodType(foodType);
        return "redirect:/foodtypes/all";
    }

    @PostMapping("/delete/{id}")
    public String deleteFoodType(@PathVariable Long id) {
        foodTypeService.deleteFoodType(id);
        return "redirect:/foodtypes/all";
    }
}

