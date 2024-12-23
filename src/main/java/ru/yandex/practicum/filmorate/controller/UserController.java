package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.*;
import ru.yandex.practicum.filmorate.model.User;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {

    private final List<User> users = new ArrayList<>();
    private Long nextId = 1L;

    @PostMapping
    public User createUser(@Valid @RequestBody User user) {
        user.setId(getNextId());
        users.add(user);
        log.info("Пользователь {} успешно добавлен", user);
        return user;
    }

    @PutMapping
    public User updateUser(@RequestBody User user) {
        if (user.getId() == null) {
            log.error("Ошибка обновления пользователя: {}", "Id должен быть указан");
            throw new ConditionsNotMetException("Id должен быть указан");
        }

        User existingUser  = users.stream()
                .filter(u -> u.getId().equals(user.getId()))
                .findFirst()
                .orElseThrow(() -> {
                    log.error("Ошибка обновления пользователя: {}", "Пользователь не найден");
                    return new ConditionsNotMetException("Пользователь не найден");
                });

        validateUser (user);

        existingUser.setEmail(user.getEmail());
        existingUser.setLogin(user.getLogin());
        existingUser.setName(user.getName());
        existingUser.setBirthday(user.getBirthday());

        log.info("Пользователь {} успешно обновлен", existingUser );
        return existingUser ;
    }

    public void validateUser(User user) {
        if (user.getEmail() == null || user.getEmail().isEmpty() || !user.getEmail().contains("@")) {
            log.error("Ошибка валидации пользователя: {}", "Электронная почта не может быть пустой и должна содержать символ @");
            throw new ValidationException("Электронная почта не может быть пустой и должна содержать символ @");
        }
        if (user.getLogin() == null || user.getLogin().isEmpty() || user.getLogin().contains(" ")) {
            log.error("Ошибка валидации пользователя: {}", "Логин не может быть пустым и содержать пробелы");
            throw new ValidationException("Логин не может быть пустым и содержать пробелы");
        }
        if (user.getBirthday() != null && user.getBirthday().isAfter(Instant.now())) {
            log.error("Ошибка валидации пользователя: {}", "Дата рождения не может быть в будущем");
            throw new ValidationException("Дата рождения не может быть в будущем");
        }
    }

    @GetMapping
    public List<User> getAllUsers() {
        return users;
    }

    private Long getNextId() {
        return nextId++;
    }
}