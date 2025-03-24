package com.restaurant.reservation.controller;

import com.restaurant.reservation.model.FoodType;
import com.restaurant.reservation.service.FoodTypeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(FoodTypeController.class)
public class FoodTypeControllerTest {

    @Mock
    private FoodTypeService foodTypeService;

    @InjectMocks
    private FoodTypeController foodTypeController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(foodTypeController).build();
    }

    @Test
    public void testCreateFoodType() throws Exception {
        FoodType foodType = new FoodType("Italian");
        when(foodTypeService.addFoodType(any(FoodType.class))).thenReturn(foodType);

        mockMvc.perform(post("/api/foodtypes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"type\": \"Italian\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.type").value("Italian"));
    }

    @Test
    public void testGetAllFoodTypes() throws Exception {
        when(foodTypeService.getAllFoodTypes()).thenReturn(Collections.singletonList(new FoodType("Italian")));

        mockMvc.perform(get("/api/foodtypes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].type").value("Italian"));
    }

    @Test
    public void testGetFoodTypeById() throws Exception {
        FoodType foodType = new FoodType("Italian");
        foodType.setId(1L);
        when(foodTypeService.getFoodTypeById(1L)).thenReturn(Optional.of(foodType));

        mockMvc.perform(get("/api/foodtypes/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.type").value("Italian"));
    }

    @Test
    public void testUpdateFoodType() throws Exception {
        FoodType foodType = new FoodType("Italian");
        when(foodTypeService.updateFoodType(1L, any(FoodType.class))).thenReturn(foodType);

        mockMvc.perform(put("/api/foodtypes/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"type\": \"Italian\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.type").value("Italian"));
    }

    @Test
    public void testDeleteFoodType() throws Exception {
        doNothing().when(foodTypeService).deleteFoodType(1L);

        mockMvc.perform(delete("/api/foodtypes/1"))
                .andExpect(status().isNoContent());
    }
}
