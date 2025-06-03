package com.restaurant.reservation.controller;

import com.restaurant.reservation.model.User;
import com.restaurant.reservation.service.CustomUserDetailsService;
import com.restaurant.reservation.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.ui.Model;

import java.nio.charset.StandardCharsets;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

@SpringBootTest
@AutoConfigureMockMvc
public class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    UserService userService;

    @MockitoBean
    AuthenticationManager authenticationManager;

    @MockitoBean
    PasswordEncoder passwordEncoder;

    @MockitoBean
    CustomUserDetailsService customUserDetailsService;

    @MockitoBean
    Model model;

    @Test
    public void shouldReturnLoginView() throws Exception {
        mockMvc.perform(get("/auth/login"))
                .andExpect(status().isOk())
                .andExpect(view().name("login"));
    }

    @Test
    public void shouldReturnRegisterView_withoutError() throws Exception {
        mockMvc.perform(get("/auth/register"))
                .andExpect(status().isOk())
                .andExpect(view().name("register"))
                .andExpect(model().attributeDoesNotExist("email"));
    }

    @Test
    public void shouldReturnRegisterView_withEmailError() throws Exception {
        String email = "test@example.com";

        mockMvc.perform(get("/auth/register")
                        .param("error", "email")
                        .param("email", email))
                .andExpect(status().isOk())
                .andExpect(view().name("register"))
                .andExpect(model().attributeExists("email"))
                .andExpect(model().attribute("email", email));
    }

    @Test
    public void shouldRegisterUserAndRedirect_whenUserDoesNotExist() throws Exception {
        String email = "newuser@example.com";
        String name = "New User";
        String phone = "1234567890";
        String password = "password";

        when(userService.getUserByEmail(email)).thenReturn(Optional.empty());
        when(userService.addUser(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        mockMvc.perform(post("/auth/register")
                        .param("email", email)
                        .param("name", name)
                        .param("phone", phone)
                        .param("password", password))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/auth/login"));

        verify(userService).getUserByEmail(email);
        verify(userService).addUser(any(User.class));
    }

    @Test
    public void shouldRedirectToRegisterWithError_whenUserExists() throws Exception {
        String email = "existing@example.com";
        String name = "Existing User";
        String phone = "0987654321";
        String password = "password";

        when(userService.getUserByEmail(email)).thenReturn(Optional.of(new User()));

        mockMvc.perform(post("/auth/register")
                        .param("email", email)
                        .param("name", name)
                        .param("phone", phone)
                        .param("password", password))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/auth/register?error=email&email=" +
                        java.net.URLEncoder.encode(email, StandardCharsets.UTF_8)));

        verify(userService).getUserByEmail(email);
        verify(userService, never()).addUser(any(User.class));
    }

    @Test
    public void shouldReturnLogoutView() throws Exception {
        mockMvc.perform(get("/auth/logout"))
                .andExpect(status().isOk())
                .andExpect(view().name("logout"));
    }
}
