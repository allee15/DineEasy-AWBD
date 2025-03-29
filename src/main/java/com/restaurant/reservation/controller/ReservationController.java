package com.restaurant.reservation.controller;

import com.restaurant.reservation.exception.CustomException;
import com.restaurant.reservation.validator.ReservationValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import com.restaurant.reservation.model.Reservation;
import com.restaurant.reservation.model.Restaurant;
import com.restaurant.reservation.model.User;
import com.restaurant.reservation.service.ReservationService;
import com.restaurant.reservation.service.RestaurantService;
import com.restaurant.reservation.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Slf4j
@Controller
@RequestMapping("/reservation")
public class ReservationController {

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private UserService userService;

    @GetMapping("/new")
    public String showAddReservationForm(@RequestParam("restaurantId") Long restaurantId, Model model) {
        log.info("Loading reservation form for restaurant ID: {}", restaurantId);

        Optional<Restaurant> restaurantOptional = restaurantService.getRestaurantById(restaurantId);
        if (restaurantOptional.isEmpty()) {
            log.error("Restaurant with ID {} not found!", restaurantId);
            throw new CustomException("An error has occured. Please go back and try again.");
        }

        model.addAttribute("restaurant", restaurantOptional.get());
       return "reservationForm";
    }

    @PostMapping
    public String createReservation(@RequestParam Long restaurantId,
                                    @RequestParam Long userId,
                                    @RequestParam String reservationDate,
                                    @RequestParam Integer nbOfPeople,
                                    @RequestParam String status) {
        log.info("Creating reservation for restaurant ID: {}", restaurantId);
        LocalDateTime reservationDateTime = LocalDateTime.parse(reservationDate);

        Optional<Restaurant> restaurantOptional = restaurantService.getRestaurantById(restaurantId);

        if (restaurantOptional.isEmpty()) {
            throw new CustomException("An error has occured. Please go back and try again.");
        }

        Restaurant restaurant = restaurantOptional.get();

        Optional<User> userOptional = userService.getUserById(userId);

        if (userOptional.isEmpty()) {
            throw new CustomException("An error has occured. Please go back and try again.");
        }

        User user = userOptional.get();

        Reservation reservation = new Reservation();
        reservation.setReservationDate(reservationDateTime);
        reservation.setNbOfPeople(nbOfPeople);
        reservation.setStatus(status);
        reservation.setRestaurant(restaurant);
        reservation.setUser(user);

        if (!ReservationValidator.isValidReservation(reservation)) {
            throw new CustomException("Invalid reservation");
        }

        reservationService.addReservation(reservation);

        return "redirect:/restaurants";
    }

    @GetMapping("/all")
    public String getAllReservations(Model model) {
        log.info("Fetching all reservations.");

        List<Reservation> reservations = reservationService.getAllReservations();
        model.addAttribute("reservations", reservations);

        return "reservations";
    }

    @GetMapping("/delete/{id}")
    public String deleteReservation(@PathVariable Long id) {
        Optional<Reservation> reservationOptional = reservationService.getReservationById(id);
        if (reservationOptional.isEmpty()) {
            log.error("Reservation with ID {} not found!", id);
            throw new CustomException("Reservation not found.");
        }

        reservationService.deleteReservation(id);

        return "redirect:/reservation/all";
    }
}
