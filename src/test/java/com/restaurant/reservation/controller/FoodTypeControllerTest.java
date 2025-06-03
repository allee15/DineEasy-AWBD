package com.restaurant.reservation.controller;

import com.restaurant.reservation.model.FoodType;
import com.restaurant.reservation.service.FoodTypeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.ui.Model;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class FoodTypeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private FoodTypeService foodTypeService;

    @MockitoBean
    private Model model;

    @Test
    public void shouldReturnFoodTypesListViewWithFoodTypes() throws Exception {
        FoodType foodType1 = new FoodType();
        foodType1.setId(1L);
        foodType1.setType("Italian");

        FoodType foodType2 = new FoodType();
        foodType2.setId(2L);
        foodType2.setType("Mexican");

        when(foodTypeService.getAllFoodTypes()).thenReturn(List.of(foodType1, foodType2));

        mockMvc.perform(get("/foodtypes/all"))
                .andExpect(status().isOk())
                .andExpect(view().name("foodTypesList"))
                .andExpect(model().attributeExists("foodTypes"));
    }

    @Test
    public void shouldAddFoodTypeAndRedirect() throws Exception {
        String newType = "Japanese";

        mockMvc.perform(post("/foodtypes/add")
                        .param("name", newType))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/foodtypes/all"));

        verify(foodTypeService).addFoodType(argThat(foodType -> foodType.getType().equals(newType)));
    }

    @Test
    public void shouldDeleteFoodTypeAndRedirect() throws Exception {
        Long idToDelete = 5L;

        mockMvc.perform(post("/foodtypes/delete/{id}", idToDelete))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/foodtypes/all"));

        verify(foodTypeService).deleteFoodType(idToDelete);
    }
}
