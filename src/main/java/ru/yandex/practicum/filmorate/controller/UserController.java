package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {

    private final List<User> users = new ArrayList<>();
    private int currentId = 1;

    @PostMapping
    public User createUser(@RequestBody User user) {
        try {
            validateUser(user);
            user.setId(currentId++);
            users.add(user);
            log.info("User  created: {}", user);
            return user;
        } catch (ValidationException e) {
            log.error("Validation failed for user: {}. Reason: {}", user, e.getMessage()); // Логируем ошибку валидации
            throw e;
        }
    }

    @PutMapping("/{id}")
    public User updateUser(@PathVariable int id, @RequestBody User updatedUser ) {
        try {
            validateUser(updatedUser );
            for (int i = 0; i < users.size(); i++) {
                if (users.get(i).getId() == id) {
                    updatedUser.setId(id);
                    users.set(i, updatedUser );
                    log.info("User  updated: {}", updatedUser); // Логируем обновление пользователя
                    return updatedUser ;
                }
            }
            log.error("User  not found with id: {}", id); // Логируем, если пользователь не найден
            throw new RuntimeException("User not found");
        } catch (ValidationException e) {
            log.error("Validation failed for user: {}. Reason: {}", updatedUser , e.getMessage()); // Логируем ошибку валидации
            throw e; // Пробрасываем исключение дальше
        }
    }

    @GetMapping
    public List<User> getAllUsers() {
        log.info("Fetching all users. Total users: {}", users.size()); // Логируем получение всех пользователей
        return users;
    }

    public void validateUser(User user) {
        if (user.getEmail() == null || user.getEmail().isBlank() || !user.getEmail().contains("@")) {
            throw new ValidationException("Email cannot be empty and must contain '@'.");
        }
        if (user.getLogin() == null || user.getLogin().isBlank() || user.getLogin().contains(" ")) {
            throw new ValidationException("Login cannot be empty and must not contain spaces.");
        }
        if (user.getBirthday() == null || user.getBirthday().isAfter(LocalDate.now())) {
            throw new ValidationException("Birthday cannot be in the future.");
        }
    }
}
