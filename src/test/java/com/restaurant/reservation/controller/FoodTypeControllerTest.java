package com.restaurant.reservation.controller;

import com.restaurant.reservation.model.FoodType;
import com.restaurant.reservation.service.FoodTypeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
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
        // Crează o listă de FoodType pentru simularea paginării
        FoodType foodType1 = new FoodType("Italian");
        FoodType foodType2 = new FoodType("Vegetarian");
        List<FoodType> foodTypes = Arrays.asList(foodType1, foodType2);

        Page<FoodType> page = new PageImpl<>(foodTypes, PageRequest.of(0, 10), foodTypes.size());

        when(foodTypeService.getAllFoodTypes(0, 10)).thenReturn(page);

        mockMvc.perform(get("/api/foodtypes?page=0&size=10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].type").value("Italian"))
                .andExpect(jsonPath("$.content[1].type").value("Vegetarian"))
                .andExpect(jsonPath("$.totalElements").value(2))
                .andExpect(jsonPath("$.totalPages").value(1))
                .andExpect(jsonPath("$.size").value(10));
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
