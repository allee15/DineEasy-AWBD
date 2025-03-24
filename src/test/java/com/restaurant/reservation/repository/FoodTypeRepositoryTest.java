package com.restaurant.reservation.repository;

import com.restaurant.reservation.model.FoodType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

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
        List<FoodType> foundFoodTypes = foodTypeRepository.findByType("Vegetarian");

        assertNotNull(foundFoodTypes, "Found food types should not be null");
        assertFalse(foundFoodTypes.isEmpty(), "Food type list should not be empty");
        assertEquals("Vegetarian", foundFoodTypes.get(0).getType(), "The type should be 'Vegetarian'");
    }

    @Test
    public void testFindByNonExistingType() {
        List<FoodType> foundFoodTypes = foodTypeRepository.findByType("NonExistingType");

        assertTrue(foundFoodTypes.isEmpty(), "Food type list should be empty for non-existing type");
    }
}
