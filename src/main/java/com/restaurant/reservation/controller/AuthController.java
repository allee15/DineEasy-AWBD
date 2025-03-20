package com.restaurant.reservation.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthController {

    @GetMapping("/login")
    public String login() {
        return "login";  // Pagina de login, care poate fi un fișier HTML personalizat
    }

    @GetMapping("/register")
    public String register() {
        return "register";  // Pagina de înregistrare
    }
}
