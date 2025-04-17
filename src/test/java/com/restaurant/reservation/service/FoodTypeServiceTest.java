package com.restaurant.reservation.service;

import com.restaurant.reservation.exception.CustomException;
import com.restaurant.reservation.model.FoodType;
import com.restaurant.reservation.repository.FoodTypeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class FoodTypeServiceTest {
    @Mock
    private FoodTypeRepository foodTypeRepository;

    @InjectMocks
    private FoodTypeService foodTypeService;

    private FoodType foodType;

    @BeforeEach
    void setup() {
        foodType = new FoodType();
        foodType.setId(1L);
        foodType.setType("Italian");
    }

    @Test
    void testAddFoodType() {
        when(foodTypeRepository.save(foodType)).thenReturn(foodType);

        FoodType savedFoodType = foodTypeService.addFoodType(foodType);

        assertNotNull(savedFoodType);
        assertEquals(foodType.getId(), savedFoodType.getId());
        assertEquals(foodType.getType(), savedFoodType.getType());

        verify(foodTypeRepository, times(1)).save(foodType);
    }

    @Test
    void testGetAllFoodTypes() {
        when(foodTypeRepository.findAll()).thenReturn(List.of(foodType));

        var foodTypes = foodTypeService.getAllFoodTypes();

        assertNotNull(foodTypes);
        assertFalse(foodTypes.isEmpty());
        assertEquals(1, foodTypes.size());
        assertEquals(foodType.getType(), foodTypes.get(0).getType());

        verify(foodTypeRepository, times(1)).findAll();
    }

    @Test
    void testGetFoodTypeById_FoodTypeFound() {
        when(foodTypeRepository.findById(foodType.getId())).thenReturn(Optional.of(foodType));

        var result = foodTypeService.getFoodTypeById(foodType.getId());

        assertNotNull(result);
        assertEquals(foodType.getId(), result.get().getId());
        assertEquals(foodType.getType(), result.get().getType());

        verify(foodTypeRepository, times(1)).findById(foodType.getId());
    }

    @Test
    void testGetFoodTypeById_FoodTypeNotFound() {
        when(foodTypeRepository.findById(foodType.getId())).thenReturn(Optional.empty());

        CustomException thrown = assertThrows(CustomException.class, () -> {
            foodTypeService.getFoodTypeById(foodType.getId());
        });

        assertEquals("FoodType with ID " + foodType.getId() + " not found", thrown.getMessage());

        verify(foodTypeRepository, times(1)).findById(foodType.getId());
    }

    @Test
    void testDeleteFoodType_FoodTypeExists() {
        when(foodTypeRepository.existsById(foodType.getId())).thenReturn(true);

        foodTypeService.deleteFoodType(foodType.getId());

        verify(foodTypeRepository, times(1)).deleteById(foodType.getId());
    }

    @Test
    void testDeleteFoodType_FoodTypeNotFound() {
        when(foodTypeRepository.existsById(foodType.getId())).thenReturn(false);

        CustomException thrown = assertThrows(CustomException.class, () -> {
            foodTypeService.deleteFoodType(foodType.getId());
        });

        assertEquals("FoodType with ID " + foodType.getId() + " not found", thrown.getMessage());

        verify(foodTypeRepository, times(1)).existsById(foodType.getId());
    }

    @Test
    void testUpdateFoodType_FoodTypeExists() {
        when(foodTypeRepository.existsById(foodType.getId())).thenReturn(true);
        when(foodTypeRepository.save(foodType)).thenReturn(foodType);

        FoodType updatedFoodType = foodTypeService.updateFoodType(foodType.getId(), foodType);

        assertNotNull(updatedFoodType);
        assertEquals(foodType.getId(), updatedFoodType.getId());
        assertEquals(foodType.getType(), updatedFoodType.getType());

        verify(foodTypeRepository, times(1)).save(foodType);
        verify(foodTypeRepository, times(1)).existsById(foodType.getId());
    }

    @Test
    void testUpdateFoodType_FoodTypeNotFound() {
        when(foodTypeRepository.existsById(foodType.getId())).thenReturn(false);

        FoodType updatedFoodType = foodTypeService.updateFoodType(foodType.getId(), foodType);

        assertNull(updatedFoodType);

        verify(foodTypeRepository, times(1)).existsById(foodType.getId());
    }
}