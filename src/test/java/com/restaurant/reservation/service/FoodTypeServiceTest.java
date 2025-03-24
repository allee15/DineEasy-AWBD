package com.restaurant.reservation.service;

import com.restaurant.reservation.model.FoodType;
import com.restaurant.reservation.repository.FoodTypeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
class FoodTypeServiceTest {

    @InjectMocks
    private FoodTypeService foodTypeService;

    @Mock
    private FoodTypeRepository foodTypeRepository;

    private FoodType foodType;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        foodType = new FoodType("Pizza");
    }

    @Test
    public void testAddFoodType() {
        when(foodTypeRepository.save(any(FoodType.class))).thenReturn(foodType);

        FoodType savedFoodType = foodTypeService.addFoodType(foodType);

        assertNotNull(savedFoodType);
        assertEquals("Pizza", savedFoodType.getType());
        verify(foodTypeRepository, times(1)).save(any(FoodType.class));
    }

    @Test
    public void testGetAllFoodTypes() {
        FoodType foodType = new FoodType("Pizza");

        List<FoodType> foodTypes = new ArrayList<>();
        foodTypes.add(foodType);

        Page<FoodType> page = new PageImpl<>(foodTypes, PageRequest.of(1, 10), foodTypes.size());

        when(foodTypeRepository.findAll(PageRequest.of(1, 10))).thenReturn(page);

        Page<FoodType> result = foodTypeService.getAllFoodTypes(1, 10);

        verify(foodTypeRepository, times(1)).findAll(PageRequest.of(1, 10));
    }

    @Test
    public void testGetFoodTypeById_Success() {
        when(foodTypeRepository.findById(1L)).thenReturn(Optional.of(foodType));

        Optional<FoodType> result = foodTypeService.getFoodTypeById(1L);

        assertTrue(result.isPresent());
        assertEquals("Pizza", result.get().getType());
        verify(foodTypeRepository, times(1)).findById(1L);
    }

    @Test
    public void testDeleteFoodType_Success() {
        doNothing().when(foodTypeRepository).deleteById(1L);

        foodTypeService.deleteFoodType(1L);

        verify(foodTypeRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testUpdateFoodType_Success() {
        FoodType updatedFoodType = new FoodType("Pasta");
        updatedFoodType.setId(1L);
        when(foodTypeRepository.existsById(1L)).thenReturn(true);
        when(foodTypeRepository.save(any(FoodType.class))).thenReturn(updatedFoodType);

        FoodType result = foodTypeService.updateFoodType(1L, updatedFoodType);

        assertNotNull(result);
        assertEquals("Pasta", result.getType());
        verify(foodTypeRepository, times(1)).save(any(FoodType.class));
    }
}