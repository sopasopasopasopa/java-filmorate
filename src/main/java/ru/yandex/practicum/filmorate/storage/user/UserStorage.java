package ru.yandex.practicum.filmorate.storage.user;

import ru.yandex.practicum.filmorate.model.User;

import java.util.List;
import java.util.Optional;

public interface UserStorage {

    User addUser(User user);

    User updateUser(User user);

    List<User> getAllUsers();

    void deleteUserById(Long id);

    Optional<User> getUserById(Long id);

    void clear();

    boolean emailExists(String email);

    boolean loginExists(String login);
}
