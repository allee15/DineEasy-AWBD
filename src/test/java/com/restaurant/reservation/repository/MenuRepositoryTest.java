package com.restaurant.reservation.repository;

import com.restaurant.reservation.model.Menu;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

@DataJpaTest
public class MenuRepositoryTest {

    @Autowired
    private MenuRepository menuRepository;

    private Menu menu;

    @BeforeEach
    public void setUp() {
        menu = new Menu();
        menu.setId(1L);
        menu.setName("Vegetarian Menu");
    }

    @Test
    public void testSaveMenu() {
        Menu savedMenu = menuRepository.save(menu);

        assertNotNull(savedMenu, "Saved menu should not be null");
        assertEquals("Vegetarian Menu", savedMenu.getName(), "Menu name should be 'Vegetarian Menu'");
        assertEquals(1L, savedMenu.getId(), "Restaurant ID should be 1");
    }

    @Test
    public void testFindByRestaurantId() {
        menuRepository.save(menu);
        List<Menu> foundMenus = menuRepository.findByRestaurantId(1L);

        assertNotNull(foundMenus, "Found menus should not be null");
        assertFalse(foundMenus.isEmpty(), "Menu list should not be empty");
        assertEquals(1L, foundMenus.get(0).getId(), "Restaurant ID should be 1");
    }

    @Test
    public void testFindByNonExistingRestaurantId() {
        List<Menu> foundMenus = menuRepository.findByRestaurantId(999L);

        assertTrue(foundMenus.isEmpty(), "Menu list should be empty for non-existing restaurant ID");
    }
}
