package com.restaurant.reservation.service;

import com.restaurant.reservation.exception.CustomException;
import com.restaurant.reservation.model.User;
import com.restaurant.reservation.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public User addUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        log.info("Adding new User: {}", user);
        return userRepository.save(user);
    }

    public List<User> getAllUsers() {
        log.info("Fetching all Users");
        return userRepository.findAll();
    }

    public Optional<User> getUserByEmail(String email) {
        log.info("Fetching User By Email: {}", email);
        return userRepository.findByEmail(email);
    }

    public Optional<User> getUserById(Long id) {
        log.info("Fetching User with ID: {}", id);
        return Optional.ofNullable(userRepository.findById(id)
                .orElseThrow(() -> new CustomException("User with ID " + id + " not found")));
    }

    public void deleteUser(Long id) {
        log.info("Deleting User with ID: {}", id);
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
        } else {
            throw new CustomException("User with ID " + id + " not found");
        }
    }

    public void updateUser(Long id, User updatedUser) {
        log.info("Updating User with ID: {}", id);
        if (userRepository.existsById(id)) {
            updatedUser.setId(id);
            userRepository.save(updatedUser);
        }
    }
}
