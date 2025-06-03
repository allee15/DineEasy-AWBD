package com.restaurant.reservation.controller;

import com.restaurant.reservation.model.Menu;
import com.restaurant.reservation.model.Restaurant;
import com.restaurant.reservation.service.MenuService;
import com.restaurant.reservation.service.RestaurantService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.ui.Model;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class MenuControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private MenuService menuService;

    @MockitoBean
    private RestaurantService restaurantService;

    @MockitoBean
    private Model model;

    @Test
    public void shouldAddMenuAndRedirect_whenRestaurantExistsAndMenuIsValid() throws Exception {
        Long restaurantId = 1L;
        Restaurant restaurant = new Restaurant();
        restaurant.setId(restaurantId);

        when(restaurantService.getRestaurantById(restaurantId)).thenReturn(Optional.of(restaurant));

        mockMvc.perform(post("/menus/add")
                        .param("restaurantId", restaurantId.toString())
                        .param("name", "Pizza")
                        .param("description", "Delicious")
                        .param("price", "20")
                        .param("photo", "photo.jpg"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/restaurants/restaurantDetails/" + restaurantId + "?"));

        verify(menuService).addMenu(argThat(menu ->
                menu.getName().equals("Pizza") &&
                        menu.getDescription().equals("Delicious") &&
                        menu.getPrice() == 20 &&
                        menu.getPhoto().equals("photo.jpg") &&
                        menu.getRestaurant().equals(restaurant)
        ));
    }

    @Test
    public void shouldThrowException_whenRestaurantDoesNotExistOnAdd() throws Exception {
        Long restaurantId = 99L;
        when(restaurantService.getRestaurantById(restaurantId)).thenReturn(Optional.empty());

        mockMvc.perform(post("/menus/add")
                        .param("restaurantId", restaurantId.toString())
                        .param("name", "Pizza")
                        .param("description", "Desc")
                        .param("price", "10")
                        .param("photo", "photo.jpg"))
                .andExpect(status().isInternalServerError());
    }

    @Test
    public void shouldReturnMenuDetailsView_whenMenuExists() throws Exception {
        Long menuId = 1L;
        Menu menu = new Menu();
        menu.setId(menuId);
        menu.setName("Pizza");

        when(menuService.getMenuById(menuId)).thenReturn(Optional.of(menu));

        mockMvc.perform(get("/menus/menu-details/s{0}", menuId))
                .andExpect(status().isOk())
                .andExpect(view().name("restaurants"))
                .andExpect(model().attributeExists("menu"));
    }

    @Test
    public void shouldThrowException_whenMenuNotFoundInDetails() throws Exception {
        Long menuId = 999L;
        when(menuService.getMenuById(menuId)).thenReturn(Optional.empty());

        mockMvc.perform(get("/menus/menu-details/s{0}", menuId))
                .andExpect(status().isInternalServerError());
    }

    @Test
    public void shouldUpdateMenuAndRedirect_whenMenuExists() throws Exception {
        Long menuId = 1L;
        Long restaurantId = 2L;

        Menu existingMenu = new Menu();
        existingMenu.setId(menuId);

        when(menuService.getMenuById(menuId)).thenReturn(Optional.of(existingMenu));

        mockMvc.perform(post("/menus/update/{id}", menuId)
                        .param("name", "Updated Name")
                        .param("description", "Updated Desc")
                        .param("price", "30")
                        .param("photo", "newphoto.jpg")
                        .param("restaurantId", restaurantId.toString()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/restaurants/restaurantDetails/" + restaurantId + "?"));

        verify(menuService).updateMenu(argThat(menu ->
                menu.getName().equals("Updated Name") &&
                        menu.getDescription().equals("Updated Desc") &&
                        menu.getPrice() == 30 &&
                        menu.getPhoto().equals("newphoto.jpg")
        ));
    }

    @Test
    public void shouldThrowException_whenMenuNotFoundOnUpdate() throws Exception {
        Long menuId = 123L;
        Long restaurantId = 1L;

        when(menuService.getMenuById(menuId)).thenReturn(Optional.empty());

        mockMvc.perform(post("/menus/update/{id}", menuId)
                        .param("name", "Name")
                        .param("description", "Desc")
                        .param("price", "10")
                        .param("photo", "photo.jpg")
                        .param("restaurantId", restaurantId.toString()))
                .andExpect(status().isInternalServerError());
    }

    @Test
    public void shouldDeleteMenuAndRedirect() throws Exception {
        Long menuId = 5L;

        mockMvc.perform(post("/menus/delete")
                        .param("menuId", menuId.toString()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/restaurants"));

        verify(menuService).deleteMenu(menuId);
    }
}
