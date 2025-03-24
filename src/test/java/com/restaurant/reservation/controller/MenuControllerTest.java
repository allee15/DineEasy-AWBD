package com.restaurant.reservation.controller;

import com.restaurant.reservation.model.Menu;
import com.restaurant.reservation.service.MenuService;
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

@WebMvcTest(MenuController.class)
public class MenuControllerTest {

    @Mock
    private MenuService menuService;

    @InjectMocks
    private MenuController menuController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(menuController).build();
    }

    @Test
    public void testCreateMenu() throws Exception {
        Menu menu = new Menu("Dinner Menu", "food", 5, "");
        when(menuService.addMenu(any(Menu.class))).thenReturn(menu);

        mockMvc.perform(post("/api/menus")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"Italian Menu\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Italian Menu"));
    }

    @Test
    public void testGetAllMenus() throws Exception {
        when(menuService.getAllMenus()).thenReturn(Collections.singletonList(new Menu("Dinner Menu", "food", 5, "")));

        mockMvc.perform(get("/api/menus"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Italian Menu"));
    }

    @Test
    public void testGetMenuById() throws Exception {
        Menu menu = new Menu("Dinner Menu", "food", 5, "");
        menu.setId(1L);
        when(menuService.getMenuById(1L)).thenReturn(Optional.of(menu));

        mockMvc.perform(get("/api/menus/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Italian Menu"));
    }

    @Test
    public void testUpdateMenu() throws Exception {
        Menu menu = new Menu("Dinner Menu", "food", 5, "");
        when(menuService.updateMenu(1L, any(Menu.class))).thenReturn(menu);

        mockMvc.perform(put("/api/menus/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"Italian Menu\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Italian Menu"));
    }

    @Test
    public void testDeleteMenu() throws Exception {
        doNothing().when(menuService).deleteMenu(1L);

        mockMvc.perform(delete("/api/menus/1"))
                .andExpect(status().isNoContent());
    }
}
