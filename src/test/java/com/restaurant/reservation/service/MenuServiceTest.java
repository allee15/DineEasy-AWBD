package com.restaurant.reservation.service;

import com.restaurant.reservation.exception.CustomException;
import com.restaurant.reservation.model.Menu;
import com.restaurant.reservation.repository.MenuRepository;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class MenuServiceTest {

    @Mock
    private MenuRepository menuRepository;

    @InjectMocks
    private MenuService menuService;

    private Menu menu;

    @BeforeEach
    void setUp() {
        menu = new Menu();
        menu.setId(1L);
        menu.setName("Test Dish");
        menu.setDescription("Delicious");
        menu.setPrice(10);
        menu.setPhoto("photo.jpg");
    }

    @Test
    void testAddMenu() {
        when(menuRepository.save(menu)).thenReturn(menu);

        Menu saved = menuService.addMenu(menu);

        assertNotNull(saved);
        assertEquals(menu.getName(), saved.getName());
        verify(menuRepository).save(menu);
    }

    @Test
    void testGetAllMenus() {
        var page = new PageImpl<>(List.of(menu));
        when(menuRepository.findAll());

        Page<Menu> result = menuService.getAllMenus(0, 5);

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        assertEquals(menu, result.getContent().get(0));
        verify(menuRepository).findAll();
    }

    @Test
    void testGetMenuById_Found() {
        when(menuRepository.findById(1L)).thenReturn(Optional.of(menu));

        Optional<Menu> result = menuService.getMenuById(1L);

        assertTrue(result.isPresent());
        assertEquals(menu, result.get());
        verify(menuRepository).findById(1L);
    }

    @Test
    void testGetMenuById_NotFound() {
        when(menuRepository.findById(1L)).thenReturn(Optional.empty());

        CustomException ex = assertThrows(CustomException.class, () -> {
            menuService.getMenuById(1L);
        });
        assertEquals("Menu with ID 1 not found", ex.getMessage());
        verify(menuRepository).findById(1L);
    }

    @Test
    void testDeleteMenu_Found() {
        when(menuRepository.existsById(1L)).thenReturn(true);

        menuService.deleteMenu(1L);

        verify(menuRepository).deleteById(1L);
    }

    @Test
    void testDeleteMenu_NotFound() {
        when(menuRepository.existsById(1L)).thenReturn(false);

        CustomException ex = assertThrows(CustomException.class, () -> {
            menuService.deleteMenu(1L);
        });
        assertEquals("Menu with ID 1 not found", ex.getMessage());
        verify(menuRepository).existsById(1L);
    }

    @Test
    void testUpdateMenu_Found() {
        when(menuRepository.existsById(menu.getId())).thenReturn(true);
        when(menuRepository.save(menu)).thenReturn(menu);

        Menu updated = menuService.updateMenu(menu);

        assertNotNull(updated);
        assertEquals(menu.getId(), updated.getId());
        verify(menuRepository).existsById(menu.getId());
        verify(menuRepository).save(menu);
    }

    @Test
    void testUpdateMenu_NotFound() {
        when(menuRepository.existsById(menu.getId())).thenReturn(false);

        CustomException ex = assertThrows(CustomException.class, () -> {
            menuService.updateMenu(menu);
        });
        assertEquals("Menu not found", ex.getMessage());
        verify(menuRepository).existsById(menu.getId());
    }
}