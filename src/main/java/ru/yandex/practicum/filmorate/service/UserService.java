package ru.yandex.practicum.filmorate.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Service
public class UserService {

    private Map<Long, User> users = new HashMap<>();
    private static Logger log = LoggerFactory.getLogger(UserService.class);

    public User userCreate(User user) {
        if (user.getEmail() == null || user.getEmail().isEmpty() || user.getEmail().isBlank() || !user.getEmail().contains("@")) {
            log.warn("User's email {}", user.getEmail());
            throw new ValidationException("Email should not be empty and should contains @");
        }
        if (user.getLogin() == null || user.getLogin().isEmpty() || user.getLogin().isBlank()) {
            log.warn("User's login is empty");
            throw new ValidationException("Login should not be empty and should not contains blanks");
        }
        if (user.getName() == null || user.getName().trim().isEmpty() || user.getName().isBlank()) {
            log.debug("Name is empty, login is used: {}", user.getLogin());
            user.setName(user.getLogin());
        }
        if (user.getBirthday().isAfter(LocalDate.now())) {
            log.warn("User's birthday {}", user.getBirthday());
            throw new ValidationException("Birthday should not be in the future");
        }

        User createUser = User.builder()
                .id(nextId())
                .email(user.getEmail())
                .login(user.getLogin())
                .name(user.getName())
                .birthday(user.getBirthday())
                .build();

        users.put(createUser.getId(), createUser);

        return createUser;

    }

    private long nextId() {
        return users.keySet()
                .stream()
                .mapToLong(Long::longValue)
                .max()
                .orElse(0L) + 1;
    }
}
