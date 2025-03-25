package com.restaurant.reservation.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.ui.Model;
import com.restaurant.reservation.model.Restaurant;
import com.restaurant.reservation.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/restaurants")
public class RestaurantController {

    @Autowired
    private RestaurantService restaurantService;

    @GetMapping
    public String getAllRestaurants(Model model) {
        List<Restaurant> restaurants = restaurantService.getAllRestaurants();
        model.addAttribute("restaurants", restaurants);
        System.out.println("Restaurants found: " + restaurants.size());
        for (Restaurant r : restaurants) {
            System.out.println("Restaurant: " + r.getName() + " - " + r.getLocation());
        }

        return "restaurants";
    }

    @GetMapping("/{id}")
    public String getRestaurantById(@PathVariable Long id, Model model) {
        Optional<Restaurant> restaurant = restaurantService.getRestaurantById(id);
        if (restaurant.isPresent()) {
            model.addAttribute("restaurant", restaurant.get());
            return "restaurant-details";
        }
        return "redirect:/restaurants";
    }

    @GetMapping("/new")
    public String showAddRestaurantForm(Model model) {
        model.addAttribute("restaurant", new Restaurant());
        return "add-restaurant";
    }

    @PostMapping
    public String addRestaurant(@ModelAttribute Restaurant restaurant, Model model) {
        restaurantService.addRestaurant(restaurant);
        return "redirect:/restaurants";
    }

    public String showEditRestaurantForm(@PathVariable Long id, Model model) {
        Optional<Restaurant> restaurant = restaurantService.getRestaurantById(id);
        if (restaurant.isPresent()) {
            model.addAttribute("restaurant", restaurant.get());
            return "edit-restaurant";
        }
        return "redirect:/restaurants";
    }

    @PostMapping("/update/{id}")
    public String updateRestaurant(@PathVariable Long id, @ModelAttribute Restaurant updatedRestaurant, Model model) {
        updatedRestaurant.setId(id);
        restaurantService.updateRestaurant(id, updatedRestaurant);
        return "redirect:/restaurants";
    }

    @PostMapping("/delete/{id}")
    public String deleteRestaurant(@PathVariable Long id) {
        restaurantService.deleteRestaurant(id);
        return "redirect:/restaurants";
    }
}
