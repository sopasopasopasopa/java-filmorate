package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.controller.UserController;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class UserValidationTest {

    @Test
    public void testUserEmailValidation() {
        User user = new User();
        user.setEmail("");
        user.setLogin("validlogin");
        user.setBirthday(Instant.now());

        assertThrows(ValidationException.class, () -> {
            new UserController().validateUser(user);
        });
    }

    @Test
    public void testUserLoginValidation() {
        User user = new User();
        user.setEmail("user@example.com");
        user.setLogin("invalid login");
        user.setBirthday(Instant.now());

        assertThrows(ValidationException.class, () -> {
            new UserController().validateUser(user);
        });
    }

    @Test
    public void testUserBirthdayValidation() {
        User user = new User();
        user.setEmail("user@example.com");
        user.setLogin("validlogin");
        user.setBirthday(Instant.now().plusSeconds(10000)); // будущее

        assertThrows(ValidationException.class, () -> {
            new UserController().validateUser(user);
        });
    }
}
