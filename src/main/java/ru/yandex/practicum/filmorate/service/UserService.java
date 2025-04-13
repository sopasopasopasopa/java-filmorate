package ru.yandex.practicum.filmorate.service;

import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.List;

@Service
public class UserService {
    private final UserStorage userStorage;

    public UserService(UserStorage userStorage) {
        this.userStorage = userStorage;
    }

    public User userCreate(User user) {
        return userStorage.addUser(user);
    }

    public User userUpdate(User updateUser) {
        return userStorage.updateUser(updateUser);
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