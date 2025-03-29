package com.restaurant.reservation.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthController {

    @GetMapping("/login")
    public String login() {
        return "restaurants";
    }

    @GetMapping("/register")
    public String register() {
        return "restaurants";
    }
}
