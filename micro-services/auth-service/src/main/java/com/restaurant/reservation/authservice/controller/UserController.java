package com.restaurant.reservation.authservice.controller;

import com.restaurant.reservation.authservice.exception.CustomException;
import com.restaurant.reservation.authservice.model.User;
import com.restaurant.reservation.authservice.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Slf4j
@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public String getUserById(@PathVariable Long id, Model model) {
        User user = userService.getUserById(id)
                .orElseThrow(() -> new CustomException("User not found"));

        log.info(user.toString());
        model.addAttribute("user", user);
        return "restaurants";
    }

    @PostMapping("/update/{id}")
    public String updateUser(@PathVariable Long id,
                             @RequestParam String name,
                             @RequestParam String email,
                             @RequestParam String phone,
                             @RequestParam String password) {
        Optional<User> optionalUser = userService.getUserById(id);
        if (optionalUser.isEmpty()) {
            throw new CustomException("An error has occured. Please go back and try again.");
        }

        User user = optionalUser.get();
        user.setName(name);
        user.setEmail(email);
        user.setPhone(phone);
        user.setPassword(password);
        userService.updateUser(id, user);
        return "restaurants";
    }

    @PostMapping("/delete/{id}")
    public String deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return "redirect:/login";
    }
}
