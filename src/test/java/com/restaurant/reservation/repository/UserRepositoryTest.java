package com.restaurant.reservation.repository;

import com.restaurant.reservation.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    private User testUser;

    @BeforeEach
    public void setUp() {
        testUser = new User();
        testUser.setEmail("test@example.com");
        testUser.setName("John Doe");
        testUser = userRepository.save(testUser);
    }

    @Test
    public void testFindByEmail() {
        Optional<User> foundUser = userRepository.findByEmail(testUser.getEmail());

        assertTrue(foundUser.isPresent(), "User should be found by email");
        assertEquals(testUser.getEmail(), foundUser.get().getEmail(), "Email should match");
        assertEquals(testUser.getName(), foundUser.get().getName(), "Name should match");
    }

    @Test
    public void testFindByEmailNotFound() {
        Optional<User> foundUser = userRepository.findByEmail("nonexistent@example.com");

        assertFalse(foundUser.isPresent(), "User should not be found with a non-existing email");
    }
}
