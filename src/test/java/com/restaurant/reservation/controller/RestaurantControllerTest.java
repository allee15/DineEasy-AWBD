package com.restaurant.reservation.controller;

import com.restaurant.reservation.exception.CustomException;
import com.restaurant.reservation.model.*;
import com.restaurant.reservation.service.*;
import com.restaurant.reservation.repository.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.ui.Model;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class RestaurantControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    RestaurantService restaurantService;

    @MockitoBean
    MenuRepository menuRepository;

    @MockitoBean
    ReviewRepository reviewRepository;

    @MockitoBean
    FoodTypeRepository foodTypeRepository;

    @MockitoBean
    Model model;

    @Test
    public void shouldReturnRestaurantDetailsView_whenRestaurantExists() throws Exception {
        Long id = 1L;

        Restaurant restaurant = new Restaurant();
        restaurant.setId(id);
        restaurant.setName("Mock Restaurant");

        Menu menu = new Menu();
        menu.setName("Pizza");
        menu.setPrice(25);
        menu.setDescription("Delicioasă");

        Review review = new Review();
        review.setRating(5);
        review.setComment("Excelent!");

        when(restaurantService.getRestaurantById(id)).thenReturn(Optional.of(restaurant));
        when(menuRepository.findByRestaurantId(id)).thenReturn(List.of(menu));
        when(reviewRepository.findByRestaurantId(id)).thenReturn(List.of(review));

        mockMvc.perform(get("/restaurants/restaurantDetails/{id}", id))
                .andExpect(status().isOk())
                .andExpect(view().name("restaurantDetails"))
                .andExpect(model().attributeExists("restaurant"))
                .andExpect(model().attributeExists("menuItems"))
                .andExpect(model().attributeExists("reviews"));
    }

    @Test
    public void shouldReturnErrorView_whenRestaurantNotFound() throws Exception {
        Long id = 999L;

        when(restaurantService.getRestaurantById(id))
                .thenThrow(new CustomException("Restaurant: " + id + " not found."));

        mockMvc.perform(get("/restaurants/restaurantDetails/{id}", id))
                .andExpect(status().isNotFound())
                .andExpect(view().name("custom-error"))
                .andExpect(model().attribute("errorTitle", "Restaurant not found"))
                .andExpect(model().attribute("errorMessage", "Restaurant: " + id + " not found."));
    }

    @Test
    public void shouldShowAddRestaurantForm_withFoodTypes() throws Exception {
        FoodType ft = new FoodType();
        ft.setId(1L);
        ft.setType("Italian");
        when(foodTypeRepository.findAll()).thenReturn(List.of(ft));

        mockMvc.perform(get("/restaurants/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("add-restaurant"))
                .andExpect(model().attributeExists("foodTypes"))
                .andExpect(model().attributeExists("restaurant"));
    }

    @Test
    public void shouldAddRestaurantAndRedirect_whenDataValid() throws Exception {
        FoodType ft = new FoodType();
        ft.setId(1L);
        when(foodTypeRepository.findById(1L)).thenReturn(Optional.of(ft));

        mockMvc.perform(post("/restaurants/add")
                        .param("name", "New Restaurant")
                        .param("location", "Somewhere")
                        .param("foodTypeId", "1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/restaurants"));

        verify(restaurantService).addRestaurant(any(Restaurant.class));
    }

    @Test
    public void shouldThrowException_whenFoodTypeNotFoundOnAdd() throws Exception {
        when(foodTypeRepository.findById(999L)).thenReturn(Optional.empty());

        mockMvc.perform(post("/restaurants/add")
                        .param("name", "Name")
                        .param("location", "Loc")
                        .param("foodTypeId", "999"))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void shouldShowEditForm_whenRestaurantExists() throws Exception {
        Long id = 1L;
        Restaurant restaurant = new Restaurant();
        restaurant.setId(id);

        FoodType ft = new FoodType();
        ft.setId(1L);
        when(foodTypeRepository.findAll()).thenReturn(List.of(ft));
        when(restaurantService.getRestaurantById(id)).thenReturn(Optional.of(restaurant));

        mockMvc.perform(get("/restaurants/edit/{id}", id))
                .andExpect(status().isOk())
                .andExpect(view().name("edit-restaurant"))
                .andExpect(model().attributeExists("foodTypes"))
                .andExpect(model().attributeExists("restaurant"));
    }

    @Test
    public void shouldRedirectToList_whenRestaurantNotFoundForEdit() throws Exception {
        Long id = 999L;
        when(restaurantService.getRestaurantById(id)).thenReturn(Optional.empty());

        mockMvc.perform(get("/restaurants/edit/{id}", id))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/restaurants"));
    }

    @Test
    public void shouldThrowException_whenFoodTypeNotFoundOnUpdate() throws Exception {
        Long id = 1L;
        when(foodTypeRepository.findById(999L)).thenReturn(Optional.empty());

        mockMvc.perform(post("/restaurants/update/{id}", id)
                        .param("foodTypeId", "999")
                        .flashAttr("updatedRestaurant", new Restaurant()))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void shouldDeleteRestaurantAndRedirect() throws Exception {
        Long id = 1L;

        mockMvc.perform(post("/restaurants/delete/{id}", id))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/restaurants"));

        verify(restaurantService).deleteRestaurant(id);
    }

    @Test
    public void shouldReturnSearchResults_whenQueryProvided() throws Exception {
        Restaurant r = new Restaurant();
        r.setName("Test");
        when(restaurantService.searchRestaurants("Test", "name")).thenReturn(List.of(r));

        mockMvc.perform(get("/restaurants/search")
                        .param("searchQuery", "Test")
                        .param("sortBy", "name"))
                .andExpect(status().isOk())
                .andExpect(view().name("restaurantList"))
                .andExpect(model().attributeExists("restaurants"));
    }

    @Test
    public void shouldReturnSearchResults_whenNoQueryProvided() throws Exception {
        when(restaurantService.searchRestaurants(null, "name")).thenReturn(List.of());

        mockMvc.perform(get("/restaurants/search"))
                .andExpect(status().isOk())
                .andExpect(view().name("restaurantList"))
                .andExpect(model().attributeExists("restaurants"));
    }
}
