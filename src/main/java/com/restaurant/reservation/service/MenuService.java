package com.restaurant.reservation.service;

import com.restaurant.reservation.exception.CustomException;
import com.restaurant.reservation.model.Menu;
import com.restaurant.reservation.repository.MenuRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class MenuService {
    @Autowired
    private MenuRepository menuRepository;

    public Menu addMenu(Menu menu) {
        log.info("Adding new Menu: {}", menu);
        return menuRepository.save(menu);
    }

    public List<Menu> getAllMenus() {
        log.info("Fetching all Menus");
        return menuRepository.findAll();
    }

    public Optional<Menu> getMenuById(Long id) {
        log.info("Fetching Menu with ID: {}", id);
        return Optional.ofNullable(menuRepository.findById(id)
                .orElseThrow(() -> new CustomException("Menu with ID " + id + " not found")));
    }

    public void deleteMenu(Long id) {
        log.info("Deleting Menu with ID: {}", id);
        menuRepository.deleteById(id);
    }

    public Menu updateMenu(Long id, Menu updateMenu) {
        log.info("Updating Menu with ID: {}", id);
        if (menuRepository.existsById(id)) {
            updateMenu.setId(id);
            return menuRepository.save(updateMenu);
        }
        return null;
    }
}
