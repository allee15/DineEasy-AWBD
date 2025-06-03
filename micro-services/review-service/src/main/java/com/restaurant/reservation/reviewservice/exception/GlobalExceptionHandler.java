package com.restaurant.reservation.reviewservice.exception;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleRestaurantNotFound(CustomException ex, Model model) {
        model.addAttribute("errorTitle", "Error");
        model.addAttribute("errorMessage", ex.getMessage());
        return "custom-error";
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleGeneralException(Exception ex, Model model) {
        model.addAttribute("errorTitle", "Internal error");
        model.addAttribute("errorMessage", "An error occurred. Please try again later.");
        return "custom-error";
    }
}