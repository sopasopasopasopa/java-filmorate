package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.controller.UserController;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class UserControllerTest {

    private UserController userController;

    @BeforeEach
    public void setUp() {
        userController = new UserController();
    }

    @Test
    public void testValidateUserNullEmail() {
        User user = new User();
        user.setEmail(null);
        user.setLogin("validLogin");
        user.setBirthday(LocalDate.of(2000, 1, 1));

        ValidationException exception = assertThrows(ValidationException.class, () -> {
            userController.validateUser(user);
        });

        assertEquals("Email cannot be empty and must contain '@'.", exception.getMessage());
    }

    @Test
    public void testValidateUserBlankEmail() {
        User user = new User();
        user.setEmail(" ");
        user.setLogin("validLogin");
        user.setBirthday(LocalDate.of(2000, 1, 1));

        ValidationException exception = assertThrows(ValidationException.class, () -> {
            userController.validateUser(user);
        });

        assertEquals("Email cannot be empty and must contain '@'.", exception.getMessage());
    }

    @Test
    public void testValidateUserInvalidEmail() {
        User user = new User();
        user.setEmail("invalidEmail");
        user.setLogin("validLogin");
        user.setBirthday(LocalDate.of(2000, 1, 1));

        ValidationException exception = assertThrows(ValidationException.class, () -> {
            userController.validateUser(user);
        });

        assertEquals("Email cannot be empty and must contain '@'.", exception.getMessage());
    }

    @Test
    public void testValidateUserNullLogin() {
        User user = new User();
        user.setEmail("valid@example.com");
        user.setLogin(null);
        user.setBirthday(LocalDate.of(2000, 1, 1));

        ValidationException exception = assertThrows(ValidationException.class, () -> {
            userController.validateUser(user);
        });

        assertEquals("Login cannot be empty and must not contain spaces.", exception.getMessage());
    }

    @Test
    public void testValidateUserBlankLogin() {
        User user = new User();
        user.setEmail("valid@example.com");
        user.setLogin(" ");
        user.setBirthday(LocalDate.of(2000, 1, 1));

        ValidationException exception = assertThrows(ValidationException.class, () -> {
            userController.validateUser(user);
        });

        assertEquals("Login cannot be empty and must not contain spaces.", exception.getMessage());
    }

    @Test
    public void testValidateUserLoginWithSpaces() {
        User user = new User();
        user.setEmail("valid@example.com");
        user.setLogin("invalid login");
        user.setBirthday(LocalDate.of(2000, 1, 1));

        ValidationException exception = assertThrows(ValidationException.class, () -> {
            userController.validateUser(user);
        });

        assertEquals("Login cannot be empty and must not contain spaces.", exception.getMessage());
    }

    @Test
    public void testValidateUserNullBirthday() {
        User user = new User();
        user.setEmail("valid@example.com");
        user.setLogin("validLogin");
        user.setBirthday(null);

        ValidationException exception = assertThrows(ValidationException.class, () -> {
            userController.validateUser(user);
        });

        assertEquals("Birthday cannot be in the future.", exception.getMessage());
    }

    @Test
    public void testValidateUserBirthdayInFuture() {
        User user = new User();
        user.setEmail("valid@example.com");
        user.setLogin("validLogin");
        user.setBirthday(LocalDate.of(3000, 1, 1)); // Будущее

        ValidationException exception = assertThrows(ValidationException.class, () -> {
            userController.validateUser(user);
        });

        assertEquals("Birthday cannot be in the future.", exception.getMessage());
    }

    @Test
    public void testValidateUserValidUser() {
        User user = new User();
        user.setEmail("valid@example.com");
        user.setLogin("validLogin");
        user.setBirthday(LocalDate.of(2000, 1, 1));

        // Не должно выбрасывать исключение
        userController.validateUser (user);
    }
}
