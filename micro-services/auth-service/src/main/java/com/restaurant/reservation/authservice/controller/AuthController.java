package com.restaurant.reservation.authservice.controller;

import com.restaurant.reservation.authservice.model.User;
import com.restaurant.reservation.authservice.service.CustomUserDetailsService;
import com.restaurant.reservation.authservice.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.UriUtils;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping("/auth")
public class AuthController {

    private static final Logger log = LoggerFactory.getLogger(AuthController.class);
    @Autowired
    private UserService userService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @PostMapping("/login")
    public Map<String, String> loginUser(
            @RequestParam String email,
            @RequestParam String password) {

        Optional<User> user = userService.getUserByEmail(email);
        Map<String, String> response = new HashMap<>();

        if (user.isEmpty()) {
            response.put("error", "Email not found");
            return response;
        }

        if (!passwordEncoder.matches(password, user.get().getPassword())) {
            response.put("error", "Invalid password");
            return response;
        }

        log.info("Fetching User By Email: {}", user);

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, password)
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        response.put("message", "Login successful");
        response.put("user", user.get().getEmail());
        return response;
    }

    @PostMapping("/register")
    public Map<String, String> registerUser(
            @RequestParam String email,
            @RequestParam String name,
            @RequestParam String phone,
            @RequestParam String password) {

        Map<String, String> response = new HashMap<>();

        if (userService.getUserByEmail(email).isPresent()) {
            response.put("error", "Email already in use");
            return response;
        }

        User user = userService.addUser(new User(name, email, phone, password));
        response.put("message", "User registered successfully");
        response.put("user", user.getEmail());

        return response;
    }

    @PostMapping("/logout")
    public Map<String, String> logout(HttpServletRequest request) {
        SecurityContextHolder.clearContext();
        Map<String, String> response = new HashMap<>();
        response.put("message", "Logged out successfully");
        return response;
    }
}
