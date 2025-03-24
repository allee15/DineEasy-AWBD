package com.restaurant.reservation.service;

import com.restaurant.reservation.model.Menu;
import com.restaurant.reservation.repository.MenuRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class MenuServiceTest {

    @InjectMocks
    private MenuService menuService;

    @Mock
    private MenuRepository menuRepository;

    private Menu menu;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        menu = new Menu("Dinner Menu", "food", 5, "");
    }

    @Test
    public void testAddMenu() {
        when(menuRepository.save(any(Menu.class))).thenReturn(menu);

        Menu savedMenu = menuService.addMenu(menu);

        assertNotNull(savedMenu);
        assertEquals("Dinner Menu", savedMenu.getName());
        verify(menuRepository, times(1)).save(any(Menu.class));
    }

    @Test
    public void testGetAllMenus() {
        List<Menu> menuList = new ArrayList<>();
        menuList.add(menu);
        when(menuRepository.findAll()).thenReturn(menuList);

        List<Menu> result = menuService.getAllMenus();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Dinner Menu", result.get(0).getName());
        verify(menuRepository, times(1)).findAll();
    }

    @Test
    public void testGetMenuById_Success() {
        when(menuRepository.findById(1L)).thenReturn(Optional.of(menu));

        Optional<Menu> result = menuService.getMenuById(1L);

        assertTrue(result.isPresent());
        assertEquals("Dinner Menu", result.get().getName());
        verify(menuRepository, times(1)).findById(1L);
    }

    @Test
    public void testUpdateMenu_Success() {
        Menu updatedMenu = new Menu("Updated Menu", "food", 5, "");
        updatedMenu.setId(1L);
        when(menuRepository.existsById(1L)).thenReturn(true);
        when(menuRepository.save(any(Menu.class))).thenReturn(updatedMenu);

        Menu result = menuService.updateMenu(1L, updatedMenu);

        assertNotNull(result);
        assertEquals("Updated Menu", result.getName());
        verify(menuRepository, times(1)).save(any(Menu.class));
    }

    @Test
    public void testDeleteMenu_Success() {
        doNothing().when(menuRepository).deleteById(1L);

        menuService.deleteMenu(1L);

        verify(menuRepository, times(1)).deleteById(1L);
    }
}
