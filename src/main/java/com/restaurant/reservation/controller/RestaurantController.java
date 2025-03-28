package com.restaurant.reservation.controller;

import com.restaurant.reservation.exception.CustomException;
import com.restaurant.reservation.model.FoodType;
import com.restaurant.reservation.model.Menu;
import com.restaurant.reservation.model.Review;
import com.restaurant.reservation.repository.FoodTypeRepository;
import com.restaurant.reservation.repository.MenuRepository;
import com.restaurant.reservation.repository.ReviewRepository;
import com.restaurant.reservation.validator.RestaurantValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import com.restaurant.reservation.model.Restaurant;
import com.restaurant.reservation.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Slf4j
@Controller
@RequestMapping("/restaurants")
public class RestaurantController {

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private MenuRepository menuRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private FoodTypeRepository foodTypeRepository;

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

    @GetMapping("/restaurantDetails/{id}")
    public String getRestaurantDetails(@PathVariable Long id, Model model) {
        Restaurant restaurant = restaurantService.getRestaurantById(id)
                .orElseThrow(() -> new RuntimeException("Restaurant not found"));

        List<Menu> menuItems = menuRepository.findByRestaurantId(id);
        List<Review> reviews = reviewRepository.findByRestaurantId(id);
        log.info("Menu Item: " + menuItems.getFirst().getName() + ", Price: " + menuItems.getFirst().getPrice() + ", Description: " + menuItems.getFirst().getDescription());
        log.info("Review: Rating: " + reviews.getFirst().getRating() + ", Comment: " + reviews.getFirst().getComment());
        model.addAttribute("restaurant", restaurant);
        model.addAttribute("menuItems", menuItems);
        model.addAttribute("reviews", reviews);

        return "restaurantDetails";
    }

    @GetMapping("/new")
    public String showAddRestaurantForm(Model model) {
        List<FoodType> foodTypes = foodTypeRepository.findAll();
        model.addAttribute("foodTypes", foodTypes);
        model.addAttribute("restaurant", new Restaurant());
        return "add-restaurant";
    }

    @PostMapping("/add")
    public String addRestaurant(@RequestParam String name, @RequestParam String location, @RequestParam Long foodTypeId) {
        FoodType foodType = foodTypeRepository.findById(foodTypeId).orElseThrow(() -> new RuntimeException("FoodType not found"));

        Restaurant restaurant = new Restaurant();
        restaurant.setName(name);
        restaurant.setLocation(location);
        restaurant.setFoodType(foodType);

        restaurantService.addRestaurant(restaurant);

        return "redirect:/restaurants";
    }

    @GetMapping("/edit/{id}")
    public String showEditRestaurantForm(@PathVariable Long id, Model model) {
        Optional<Restaurant> restaurant = restaurantService.getRestaurantById(id);
        if (restaurant.isPresent()) {
            List<FoodType> foodTypes = foodTypeRepository.findAll();
            model.addAttribute("foodTypes", foodTypes);
            model.addAttribute("restaurant", restaurant.get());
            return "edit-restaurant";
        }
        return "redirect:/restaurants";
    }

    @PostMapping("/update/{id}")
    public String updateRestaurant(@PathVariable Long id, @RequestParam Long foodTypeId, @ModelAttribute Restaurant updatedRestaurant) {
        log.info("Updating restaurant with ID: " + id);

        if (!RestaurantValidator.isValidRestaurant(updatedRestaurant)) {
            throw new CustomException("Invalid restaurant");
        }

        Optional<FoodType> foodTypeOpt = foodTypeRepository.findById(foodTypeId);
        if (foodTypeOpt.isEmpty()) {
            throw new CustomException("FoodType not found");
        }

        updatedRestaurant.setFoodType(foodTypeOpt.get());
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
