package com.restaurant.reservation.repository;

import com.restaurant.reservation.model.Menu;
import com.restaurant.reservation.model.Restaurant;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

@DataJpaTest
public class MenuRepositoryTest {

    @Autowired
    private MenuRepository menuRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    private Menu menu;
    private Restaurant restaurant;
    private Pageable pageable;

    @BeforeEach
    public void setUp() {
        pageable = PageRequest.of(0, 10);

        restaurant = new Restaurant();
        restaurant.setName("Green Eatery");
        restaurant.setLocation("New York");
        restaurant = restaurantRepository.save(restaurant);

        menu = new Menu();
        menu.setName("Vegetarian Menu");
        menu.setRestaurant(restaurant);
        menu = menuRepository.save(menu);
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
        Page<Menu> foundMenus = menuRepository.findByRestaurantId(restaurant.getId(), pageable);

        assertFalse(foundMenus.isEmpty(), "Menu list should not be empty");
        assertEquals(restaurant.getId(), foundMenus.getContent().get(0).getRestaurant().getId(),
                "Menu should belong to the correct restaurant");
    }

    @Test
    public void testFindByNonExistingRestaurantId() {
        Page<Menu> foundMenus = menuRepository.findByRestaurantId(999L, pageable);

        assertTrue(foundMenus.isEmpty(), "Menu list should be empty for non-existing restaurant ID");
    }
}
