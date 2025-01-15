package ru.yandex.practicum.filmorate.storage.user;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class InMemoryUserStorage implements UserStorage {
    private Map<Long, User> users = new HashMap<>();

    @Override
    public User addUser(User user) {
        validateUser(user);
        User createUser  = User.builder()
                .id(nextId())
                .email(user.getEmail())
                .login(user.getLogin())
                .name(user.getName())
                .birthday(user.getBirthday())
                .build();

        users.put(createUser.getId(), createUser);
        return createUser;
    }

    @Override
    public User updateUser(User updateUser) {
        if (updateUser.getId() == null) {
            throw new ValidationException("Id should not be empty");
        }
        if (!users.containsKey(updateUser .getId())) {
            throw new NotFoundException("User with this Id not found");
        }
        validateUser(updateUser);

        users.put(updateUser.getId(), updateUser);
        return updateUser;
    }

    @Override
    public List<User> getAllUsers() {
        return new ArrayList<>(users.values());
    }

    @Override
    public void deleteUserById(Long id) {
        if (!users.containsKey(id)) {
            throw new NotFoundException("User not found");
        }
        users.remove(id);
    }

    private void validateUser(User user) {
        if (user.getEmail() == null || user.getEmail().isEmpty() || !user.getEmail().contains("@")) {
            throw new ValidationException("Email should not be empty and should contain @");
        }
        if (user.getLogin() == null || user.getLogin().isEmpty()) {
            throw new ValidationException("Login should not be empty");
        }
        if (user.getName() == null || user.getName().trim().isEmpty()) {
            user.setName(user.getLogin());
        }
        if (user.getBirthday().isAfter(LocalDate.now())) {
            throw new ValidationException("Birthday should not be in the future");
        }
    }

    @Override
    public User getUserById(Long id) {
        if (!users.containsKey(id)) {
            throw new NotFoundException("User  not found");
        }
        return users.get(id);
    }

    private long nextId() {
        return users.keySet()
                .stream()
                .mapToLong(Long::longValue)
                .max()
                .orElse(0L) + 1;
    }
}
