package com.restaurant.reservation.repository;

import com.restaurant.reservation.model.FoodType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class FoodTypeRepositoryTest {

    @Autowired
    private FoodTypeRepository foodTypeRepository;

    private FoodType foodType;

    @BeforeEach
    public void setUp() {
        foodType = new FoodType();
        foodType.setType("Vegetarian");
    }

    @Test
    public void testSaveFoodType() {
        FoodType savedFoodType = foodTypeRepository.save(foodType);

        assertNotNull(savedFoodType, "Saved food type should not be null");
        assertEquals("Vegetarian", savedFoodType.getType(), "Food type should be 'Vegetarian'");
    }

    @Test
    public void testFindByType() {
        foodTypeRepository.save(foodType);
        Optional<FoodType> foundFoodTypes = foodTypeRepository.findById(1L);

        assertNotNull(foundFoodTypes, "Found food types should not be null");
        assertFalse(foundFoodTypes.isEmpty(), "Food type list should not be empty");
        assertEquals("Vegetarian", foundFoodTypes.get().getType(), "The type should be 'Vegetarian'");
    }

    @Test
    public void testFindByNonExistingType() {
        Optional<FoodType> foundFoodTypes = foodTypeRepository.findById(3L);

        assertTrue(foundFoodTypes.isEmpty(), "Food type list should be empty for non-existing type");
    }
}
