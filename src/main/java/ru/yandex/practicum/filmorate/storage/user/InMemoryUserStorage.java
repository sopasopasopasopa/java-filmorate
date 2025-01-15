package ru.yandex.practicum.filmorate.storage.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static Logger log = LoggerFactory.getLogger(InMemoryUserStorage.class);

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
        log.info("User created {} and added to storage {}", user, user.getId());
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
        log.info("User updated with Id: {}", updateUser .getId());
        return updateUser;
    }

    @Override
    public List<User> getAllUsers() {
        return new ArrayList<>(users.values());
    }

    @Override
    public void deleteUserById(Long id) {
        if (!users.containsKey(id)) {
            log.error("User with ID {} not found for deletion", id);
            throw new NotFoundException("User not found");
        }
        users.remove(id);
        log.info("User with ID {} successfully deleted", id);
    }

    private void validateUser(User user) {
        if (user.getEmail() == null || user.getEmail().isEmpty() || !user.getEmail().contains("@")) {
            log.warn("User email {}", user.getEmail());
            throw new ValidationException("Email should not be empty and should contain @");
        }
        if (user.getLogin() == null || user.getLogin().isEmpty()) {
            log.warn("User login is empty");
            throw new ValidationException("Login should not be empty");
        }
        if (user.getName() == null || user.getName().trim().isEmpty()) {
            log.debug("User name is empty, using login: {}", user.getLogin());
            user.setName(user.getLogin());
        }
        if (user.getBirthday().isAfter(LocalDate.now())) {
            log.warn("Birthday {}", user.getBirthday());
            throw new ValidationException("Birthday should not be in the future");
        }
    }

    @Override
    public User getUserById(Long id) {
        if (!users.containsKey(id)) {
            throw new NotFoundException("User not found");
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
