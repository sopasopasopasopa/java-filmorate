package ru.yandex.practicum.filmorate.service;

import io.micrometer.common.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.List;

@Service
public class UserService {
    private final UserStorage userStorage;
    private final Logger log = LoggerFactory.getLogger(UserService.class);

    public UserService(UserStorage userStorage) {
        this.userStorage = userStorage;
    }

    public User userCreate(User user) {
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
        return userStorage.addUser(user);
    }

    public User userUpdate(User user) {
        try {
            User existingUser = userStorage.getUserById(user.getId())
                    .orElseThrow(() -> new NotFoundException("User not found"));

            // Проверка уникальности с исключением текущего пользователя
            if (!user.getEmail().equals(existingUser.getEmail())) {
                if (userStorage.emailExists(user.getEmail())) {
                    throw new ValidationException("Email already registered");
                }
            }

            if (!user.getLogin().equals(existingUser.getLogin())) {
                if (userStorage.loginExists(user.getLogin())) {
                    throw new ValidationException("Login already taken");
                }
            }

            if (StringUtils.isBlank(user.getName())) {
                user.setName(user.getLogin());
            }

            return userStorage.updateUser(user);
        } catch (DataAccessException ex) {
            log.error("Database error during user update", ex);
            throw new RuntimeException("Database operation failed");
        }
    }

    public User findById(Long userId) {
        return userStorage.getUserById(userId)
                .orElseThrow(() -> new NotFoundException("User with ID " + userId + " not found"));
    }

    public List<User> getAllUsers() {
        return userStorage.getAllUsers();
    }

    public void deleteUserById(Long userId) {
        // Сначала проверяем существование пользователя
        if (userStorage.getUserById(userId).isEmpty()) {
            throw new NotFoundException("User with ID " + userId + " not found");
        }
        userStorage.deleteUserById(userId);
    }

    public void clearUsers() {
        userStorage.clear();
    }
}