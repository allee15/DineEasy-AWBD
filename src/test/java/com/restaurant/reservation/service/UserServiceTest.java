package com.restaurant.reservation.service;

import com.restaurant.reservation.exception.CustomException;
import com.restaurant.reservation.model.User;
import com.restaurant.reservation.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1L);
        user.setName("Alice");
        user.setEmail("alice@example.com");
        user.setPhone("1234567890");
        user.setPassword("plainPass");
    }

    @Test
    void testAddUser() {
        when(passwordEncoder.encode("plainPass")).thenReturn("encodedPass");
        when(userRepository.save(any(User.class))).thenAnswer(inv -> inv.getArgument(0));

        User saved = userService.addUser(user);

        assertNotNull(saved);
        assertEquals("encodedPass", saved.getPassword());
        verify(passwordEncoder).encode("plainPass");
        verify(userRepository).save(saved);
    }

    @Test
    void testGetAllUsers() {
        when(userRepository.findAll()).thenReturn(List.of(user));

        List<User> all = userService.getAllUsers();

        assertEquals(1, all.size());
        assertEquals("Alice", all.get(0).getName());
        verify(userRepository).findAll();
    }

    @Test
    void testGetUserByEmail_Found() {
        when(userRepository.findByEmail("alice@example.com")).thenReturn(Optional.of(user));

        Optional<User> result = userService.getUserByEmail("alice@example.com");

        assertTrue(result.isPresent());
        assertEquals("alice@example.com", result.get().getEmail());
        verify(userRepository).findByEmail("alice@example.com");
    }

    @Test
    void testGetUserById_Found() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        Optional<User> result = userService.getUserById(1L);

        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getId());
        verify(userRepository).findById(1L);
    }

    @Test
    void testGetUserById_NotFound() {
        when(userRepository.findById(2L)).thenReturn(Optional.empty());

        CustomException ex = assertThrows(CustomException.class, () -> {
            userService.getUserById(2L);
        });
        assertEquals("User with ID 2 not found", ex.getMessage());
        verify(userRepository).findById(2L);
    }

    @Test
    void testDeleteUser_Exists() {
        when(userRepository.existsById(1L)).thenReturn(true);

        userService.deleteUser(1L);

        verify(userRepository).deleteById(1L);
    }

    @Test
    void testDeleteUser_NotExists() {
        when(userRepository.existsById(3L)).thenReturn(false);

        CustomException ex = assertThrows(CustomException.class, () -> {
            userService.deleteUser(3L);
        });
        assertEquals("User with ID 3 not found", ex.getMessage());
        verify(userRepository).existsById(3L);
        verify(userRepository, never()).deleteById(anyLong());
    }

    @Test
    void testUpdateUser_Exists() {
        User updated = new User();
        updated.setName("Alice Updated");
        when(userRepository.existsById(1L)).thenReturn(true);
        when(userRepository.save(any(User.class))).thenReturn(updated);

        userService.updateUser(1L, updated);

        assertEquals(1L, updated.getId());
        verify(userRepository).existsById(1L);
        verify(userRepository).save(updated);
    }

    @Test
    void testUpdateUser_NotExists() {
        when(userRepository.existsById(4L)).thenReturn(false);

        userService.updateUser(4L, user);

        verify(userRepository).existsById(4L);
        verify(userRepository, never()).save(any());
    }
}