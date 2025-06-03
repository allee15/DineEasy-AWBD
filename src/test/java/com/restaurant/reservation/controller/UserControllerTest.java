package com.restaurant.reservation.controller;

import com.restaurant.reservation.model.User;
import com.restaurant.reservation.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserService userService;

    @Test
    public void getAllUsers_shouldReturnList() throws Exception {
        User u1 = new User();
        User u2 = new User();
        List<User> users = List.of(u1, u2);

        when(userService.getAllUsers()).thenReturn(users);

        mockMvc.perform(get("/user"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));

        verify(userService).getAllUsers();
    }

    @Test
    public void getUserById_shouldReturnViewWithUser() throws Exception {
        Long userId = 1L;
        User user = new User();
        user.setId(userId);
        user.setName("John Doe");

        when(userService.getUserById(userId)).thenReturn(Optional.of(user));

        mockMvc.perform(get("/user/{id}", userId))
                .andExpect(status().isOk())
                .andExpect(view().name("restaurants"))
                .andExpect(model().attributeExists("user"))
                .andExpect(model().attribute("user", user));

        verify(userService).getUserById(userId);
    }

    @Test
    public void getUserById_shouldThrowException_whenUserNotFound() throws Exception {
        Long userId = 1L;

        when(userService.getUserById(userId)).thenReturn(Optional.empty());

        mockMvc.perform(get("/user/{id}", userId))
                .andExpect(status().isInternalServerError());

        verify(userService).getUserById(userId);
    }

    @Test
    public void updateUser_shouldReturnView_whenUserExists() throws Exception {
        Long userId = 1L;
        User existingUser = new User();
        existingUser.setId(userId);

        when(userService.getUserById(userId)).thenReturn(Optional.of(existingUser));

        mockMvc.perform(post("/user/update/{id}", userId)
                        .param("name", "New Name")
                        .param("email", "newemail@example.com")
                        .param("phone", "1234567890")
                        .param("password", "newpassword"))
                .andExpect(status().isOk())
                .andExpect(view().name("restaurants"));

        ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);
        verify(userService).updateUser(eq(userId), captor.capture());

        User updatedUser = captor.getValue();
        assertEquals("New Name", updatedUser.getName());
        assertEquals("newemail@example.com", updatedUser.getEmail());
        assertEquals("1234567890", updatedUser.getPhone());
        assertEquals("newpassword", updatedUser.getPassword());
    }

    @Test
    public void updateUser_shouldThrowException_whenUserNotFound() throws Exception {
        Long userId = 1L;

        when(userService.getUserById(userId)).thenReturn(Optional.empty());

        mockMvc.perform(post("/user/update/{id}", userId)
                        .param("name", "Name")
                        .param("email", "email@example.com")
                        .param("phone", "1234567890")
                        .param("password", "password"))
                .andExpect(status().isInternalServerError());

        verify(userService, never()).updateUser(anyLong(), any());
    }

    @Test
    public void deleteUser_shouldRedirect() throws Exception {
        Long userId = 1L;

        mockMvc.perform(post("/user/delete/{id}", userId))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login"));

        verify(userService).deleteUser(userId);
    }
}
