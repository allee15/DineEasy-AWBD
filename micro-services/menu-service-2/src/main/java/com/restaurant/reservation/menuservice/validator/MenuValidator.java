package com.restaurant.reservation.menuservice.validator;

import com.restaurant.reservation.menuservice.model.Menu;

public class MenuValidator {
    public static boolean isValidMenu(Menu menu) {
        if (menu.getName() == null || menu.getName().trim().isEmpty()) {
            return false;
        }

        if (menu.getDescription() == null || menu.getDescription().trim().isEmpty()) {
            return false;
        }

        if (menu.getPrice() <= 0) {
            return false;
        }

        if (menu.getPhoto() == null || menu.getPhoto().trim().isEmpty()) {
            return false;
        }

        return true;
    }
}

