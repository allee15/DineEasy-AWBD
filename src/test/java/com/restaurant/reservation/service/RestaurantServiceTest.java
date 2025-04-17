package com.restaurant.reservation.service;

import com.restaurant.reservation.exception.CustomException;
import com.restaurant.reservation.model.FoodType;
import com.restaurant.reservation.model.Restaurant;
import com.restaurant.reservation.repository.RestaurantRepository;
import com.restaurant.reservation.utils.Pagination;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class RestaurantServiceTest {

    @Mock
    private RestaurantRepository restaurantRepository;

    @InjectMocks
    private RestaurantService restaurantService;

    private Restaurant r1, r2;
    private FoodType ft1, ft2;

    @BeforeEach
    void setUp() {
        ft1 = new FoodType();
        ft1.setId(1L);
        ft1.setType("Italian");

        ft2 = new FoodType();
        ft2.setId(2L);
        ft2.setType("Vegan");

        r1 = new Restaurant();
        r1.setId(1L);
        r1.setName("Alpha");
        r1.setLocation("LocA");
        r1.setFoodType(ft1);

        r2 = new Restaurant();
        r2.setId(2L);
        r2.setName("Beta");
        r2.setLocation("LocB");
        r2.setFoodType(ft2);
    }

    @Test
    void testAddRestaurant() {
        when(restaurantRepository.save(r1)).thenReturn(r1);

        Restaurant saved = restaurantService.addRestaurant(r1);

        assertNotNull(saved);
        assertEquals("Alpha", saved.getName());
        verify(restaurantRepository).save(r1);
    }

    @Test
    void testGetAllRestaurants_Pagination() {
        List<Restaurant> all = Arrays.asList(r1, r2);
        when(restaurantRepository.findAll()).thenReturn(all);

        Pagination<Restaurant> page = restaurantService.getAllRestaurants(1);

        assertEquals(1, page.getTotalPages());
        assertEquals(all, page.getCurrentPageData());
        verify(restaurantRepository).findAll();
    }

    @Test
    void testGetRestaurantsByLocation() {
        Page<Restaurant> pageImpl = new PageImpl<>(List.of(r1));
        when(restaurantRepository.findByLocation("LocA", PageRequest.of(0, 10)))
                .thenReturn(pageImpl);

        Page<Restaurant> result = restaurantService.getRestaurantsByLocation("LocA", 0, 10);

        assertEquals(1, result.getTotalElements());
        assertEquals(r1, result.getContent().get(0));
        verify(restaurantRepository).findByLocation("LocA", PageRequest.of(0, 10));
    }

    @Test
    void testGetRestaurantsByFoodType() {
        Page<Restaurant> pageImpl = new PageImpl<>(List.of(r2));
        when(restaurantRepository.findByFoodType(ft2, PageRequest.of(1, 5)))
                .thenReturn(pageImpl);

        Page<Restaurant> result = restaurantService.getRestaurantsByFoodType(ft2, 1, 5);

        assertEquals(1, result.getTotalElements());
        assertEquals(r2, result.getContent().get(0));
        verify(restaurantRepository).findByFoodType(ft2, PageRequest.of(1, 5));
    }

    @Test
    void testGetRestaurantById_Found() {
        when(restaurantRepository.findById(1L)).thenReturn(Optional.of(r1));

        Optional<Restaurant> result = restaurantService.getRestaurantById(1L);

        assertTrue(result.isPresent());
        assertEquals("Alpha", result.get().getName());
        verify(restaurantRepository).findById(1L);
    }

    @Test
    void testGetRestaurantById_NotFound() {
        when(restaurantRepository.findById(3L)).thenReturn(Optional.empty());

        CustomException ex = assertThrows(CustomException.class, () -> {
            restaurantService.getRestaurantById(3L);
        });
        assertEquals("Restaurant with ID 3 not found", ex.getMessage());
        verify(restaurantRepository).findById(3L);
    }

    @Test
    void testDeleteRestaurant_Found() {
        when(restaurantRepository.existsById(2L)).thenReturn(true);

        restaurantService.deleteRestaurant(2L);

        verify(restaurantRepository).deleteById(2L);
    }

    @Test
    void testDeleteRestaurant_NotFound() {
        when(restaurantRepository.existsById(4L)).thenReturn(false);

        CustomException ex = assertThrows(CustomException.class, () -> {
            restaurantService.deleteRestaurant(4L);
        });
        assertEquals("Restaurant with ID 4 not found", ex.getMessage());
        verify(restaurantRepository).existsById(4L);
    }

    @Test
    void testUpdateRestaurant_Found() {
        Restaurant updated = new Restaurant();
        updated.setName("Gamma");
        when(restaurantRepository.existsById(1L)).thenReturn(true);
        when(restaurantRepository.save(updated)).thenReturn(updated);

        Restaurant result = restaurantService.updateRestaurant(1L, updated);

        assertNotNull(result);
        assertEquals("Gamma", result.getName());
        verify(restaurantRepository).existsById(1L);
        verify(restaurantRepository).save(updated);
    }

    @Test
    void testUpdateRestaurant_NotFound() {
        when(restaurantRepository.existsById(5L)).thenReturn(false);

        Restaurant result = restaurantService.updateRestaurant(5L, r1);

        assertNull(result);
        verify(restaurantRepository).existsById(5L);
    }

    @Test
    void testSearchRestaurants_NoQuery_DefaultSort() {
        List<Restaurant> list = Arrays.asList(r2, r1);
        when(restaurantRepository.findAll()).thenReturn(list);

        List<Restaurant> result = restaurantService.searchRestaurants(null, "name");

        assertEquals("Alpha", result.get(0).getName());
        assertEquals("Beta", result.get(1).getName());
        verify(restaurantRepository).findAll();
    }

    @Test
    void testSearchRestaurants_WithQuery() {
        List<Restaurant> list = Arrays.asList(r1, r2);
        when(restaurantRepository.findAll()).thenReturn(list);

        List<Restaurant> result = restaurantService.searchRestaurants("Alpha", "name");

        assertEquals(1, result.size());
        assertEquals("Alpha", result.get(0).getName());
    }

    @Test
    void testSearchRestaurants_SortByLocation() {
        r1.setLocation("B-Loc");
        r2.setLocation("A-Loc");
        List<Restaurant> list = Arrays.asList(r1, r2);
        when(restaurantRepository.findAll()).thenReturn(list);

        List<Restaurant> result = restaurantService.searchRestaurants(null, "location");

        assertEquals("A-Loc", result.get(0).getLocation());
        assertEquals("B-Loc", result.get(1).getLocation());
    }

    @Test
    void testSearchRestaurants_SortByCuisine() {
        ft1.setType("Z-Type");
        ft2.setType("A-Type");
        r1.setFoodType(ft1);
        r2.setFoodType(ft2);
        when(restaurantRepository.findAll()).thenReturn(List.of(r1, r2));

        List<Restaurant> result = restaurantService.searchRestaurants(null, "cuisine");

        assertEquals("A-Type", result.get(0).getFoodType().getType());
        assertEquals("Z-Type", result.get(1).getFoodType().getType());
    }
}