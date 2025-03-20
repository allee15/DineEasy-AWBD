package com.restaurant.reservation.service;

import com.restaurant.reservation.model.Menu;
import com.restaurant.reservation.repository.MenuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MenuService {
    @Autowired
    private MenuRepository menuRepository;

    public Menu addMenu(Menu menu) {
        return menuRepository.save(menu);
    }

    public List<Menu> getAllMenus() {
        return menuRepository.findAll();
    }

    public Optional<Menu> getMenuById(Long id) {
        return menuRepository.findById(id);
    }

    public void deleteMenu(Long id) {
        menuRepository.deleteById(id);
    }

    public Menu updateMenu(Long id, Menu updateMenu) {
        if (menuRepository.existsById(id)) {
            updateMenu.setId(id);
            return menuRepository.save(updateMenu);
        }
        return null;
    }
}
