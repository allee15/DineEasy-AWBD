package com.restaurant.reservation.controller;

import com.restaurant.reservation.model.User;
import com.restaurant.reservation.service.CustomUserDetailsService;
import com.restaurant.reservation.service.UserService;
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

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @PostMapping("/login")
    public String loginUser(
            @RequestParam String email,
            @RequestParam String password,
            HttpServletRequest request) {

        Optional<User> user = userService.getUserByEmail(email);

        log.info("Fetching User By Email: {}", user);
        if(user.isEmpty())
            return "redirect:/auth/register?error=email&email=" + UriUtils.encode(email, StandardCharsets.UTF_8);
        else {
            log.info("Password {}, hashed passowrd {}", passwordEncoder.encode(password), user.get().getPassword());
            if (!passwordEncoder.matches(password, user.get().getPassword())) {
                return "redirect:/auth/login?error=" + UriUtils.encode("This email/password combination is not correct", StandardCharsets.UTF_8);
            } else {
                log.info("User logged: {}", user);

                Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                        email, password
                ));

                SecurityContextHolder.getContext().setAuthentication(authentication);
                    return "redirect:/restaurants";

            }
        }

    }

    @GetMapping("/register")
    public String register(
            @RequestParam(value = "error", required = false) String error,
            @RequestParam(value = "email", required = false) String email,
            Model model) {

        log.info("Registering new user: {}", email);
        if(email != null && error.equals("email")) {
            model.addAttribute("email", email);
        }

        log.info("Registering new user: {}", model);

        return "register";
    }

    @PostMapping("/register")
    public String registerUser(
            @RequestParam String email,
            @RequestParam String name,
            @RequestParam String phone,
            @RequestParam String password) {

        User user = userService.addUser(new User(name, email, phone, password));

        log.info("User registered: {}", user);
        if (user != null) {
            return "redirect:/restaurants";
        } else {
            return "redirect:/register?error=email&email=" + UriUtils.encode(email, StandardCharsets.UTF_8);
        }
    }

    @GetMapping("/logout")
    public String logout() { return "logout"; }
}
