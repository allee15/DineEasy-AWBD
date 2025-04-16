package com.restaurant.reservation.menuservice.service;

import com.restaurant.reservation.menuservice.exception.CustomException;
import com.restaurant.reservation.menuservice.model.Menu;
import com.restaurant.reservation.menuservice.repository.MenuRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

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

    public Page<Menu> getAllMenus(int page, int size) {
        log.info("Fetching all Menus - Page: {}, Size: {}", page, size);
        org.springframework.data.domain.Pageable pageable = PageRequest.of(page, size);
        return menuRepository.findAll(pageable);
    }

    public Optional<Menu> getMenuById(Long id) {
        log.info("Fetching Menu with ID: {}", id);
        return Optional.ofNullable(menuRepository.findById(id)
                .orElseThrow(() -> new CustomException("Menu with ID " + id + " not found")));
    }

    public void deleteMenu(Long id) {
        log.info("Deleting Menu with ID: {}", id);
        if (menuRepository.existsById(id)) {
            menuRepository.deleteById(id);
        } else {
            throw new CustomException("Menu with ID " + id + " not found");
        }
    }

    public Menu updateMenu(Menu menu) {
        log.info("Updating Menu: {}", menu);

        if (menuRepository.existsById(menu.getId())) {
            return menuRepository.save(menu);
        } else {
            throw new CustomException("Menu not found");
        }
    }
}
