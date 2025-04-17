package com.restaurant.reservation.validator;

import com.restaurant.reservation.model.Menu;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MenuValidatorTest {

    @Test
    void testValidMenu() {
        Menu menu = new Menu();
        menu.setName("Pizza");
        menu.setDescription("Delicious pizza");
        menu.setPrice(30);
        menu.setPhoto("pizza.jpg");

        assertTrue(MenuValidator.isValidMenu(menu));
    }

    @Test
    void testInvalidName() {
        Menu menu = new Menu();
        menu.setName(" ");
        menu.setDescription("Good");
        menu.setPrice(10);
        menu.setPhoto("photo.jpg");

        assertFalse(MenuValidator.isValidMenu(menu));
    }

    @Test
    void testInvalidDescription() {
        Menu menu = new Menu();
        menu.setName("Burger");
        menu.setDescription("   ");
        menu.setPrice(10);
        menu.setPhoto("photo.jpg");

        assertFalse(MenuValidator.isValidMenu(menu));
    }

    @Test
    void testInvalidPrice_Zero() {
        Menu menu = new Menu();
        menu.setName("Burger");
        menu.setDescription("Tasty");
        menu.setPrice(0);
        menu.setPhoto("photo.jpg");

        assertFalse(MenuValidator.isValidMenu(menu));
    }

    @Test
    void testInvalidPrice_Negative() {
        Menu menu = new Menu();
        menu.setName("Burger");
        menu.setDescription("Tasty");
        menu.setPrice(-5);
        menu.setPhoto("photo.jpg");

        assertFalse(MenuValidator.isValidMenu(menu));
    }

    @Test
    void testInvalidPhoto() {
        Menu menu = new Menu();
        menu.setName("Pasta");
        menu.setDescription("Fresh");
        menu.setPrice(20);
        menu.setPhoto("  ");

        assertFalse(MenuValidator.isValidMenu(menu));
    }

    @Test
    void testAllFieldsInvalid() {
        Menu menu = new Menu();
        menu.setName(null);
        menu.setDescription(null);
        menu.setPrice(-1);
        menu.setPhoto(null);

        assertFalse(MenuValidator.isValidMenu(menu));
    }
}
