package com.restaurant.reservation.controller;

import com.restaurant.reservation.model.Menu;
import com.restaurant.reservation.service.MenuService;
import com.restaurant.reservation.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import com.restaurant.reservation.model.Restaurant;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/menus")
public class MenuController {

    @Autowired
    private MenuService menuService;

    @Autowired
    private RestaurantService restaurantService;

    @PostMapping("/add")
    public String addMenu(@RequestParam Long restaurantId,
                          @RequestParam String name,
                          @RequestParam String description,
                          @RequestParam int price,
                          @RequestParam String photo) {

        Optional<Restaurant> restaurantOptional = restaurantService.getRestaurantById(restaurantId);

        if (restaurantOptional.isEmpty()) {
            return "An error has occured. Please go back and try again.";
        }

        Restaurant restaurant = restaurantOptional.get();
        Menu menu = new Menu();
        menu.setRestaurant(restaurant);
        menu.setName(name);
        menu.setDescription(description);
        menu.setPrice(price);
        menu.setPhoto(photo);

        menuService.addMenu(menu);

        return "Your request was handled successfully. Go back to previous page.";
    }

    @GetMapping
    public Page<Menu> getAllMenus(@RequestParam(defaultValue = "0") int page,
                                  @RequestParam(defaultValue = "10") int size) {
        return menuService.getAllMenus(page, size);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Menu> getMenuById(@PathVariable Long id) {
        Optional<Menu> menu = menuService.getMenuById(id);
        return menu.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/update/{id}")
    public String updateMenu(@PathVariable Long id,
                             @RequestParam String name,
                             @RequestParam String description,
                             @RequestParam int price,
                             @RequestParam String photo) {

        Optional<Menu> menuOptional = menuService.getMenuById(id);

        if (menuOptional.isEmpty()) {
            return "Meniu not found!";
        }

        Menu menu = menuOptional.get();
        menu.setName(name);
        menu.setDescription(description);
        menu.setPrice(price);
        menu.setPhoto(photo);

        menuService.updateMenu(menu);

        return "Your request was handled successfully. Go back to previous page.";
    }

    @PostMapping("/delete")
    public String deleteMenu(@RequestParam Long menuId) {
        menuService.deleteMenu(menuId);
        return "Your request was handled successfully. Go back to previous page.";
    }
}
