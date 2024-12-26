package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class UserServiceTest {

    private UserService userService;

    @BeforeEach
    void setUp() {
        userService = new UserService();
    }

    @Test
    void shouldThrowExceptionWhenEmailIsNull() {
        User user = User.builder()
                .email(null)
                .login("validLogin")
                .name("validName")
                .birthday(LocalDate.of(2000,1,1))
                .build();
        assertThrows(ValidationException.class, () -> userService.userCreate(user));
    }

    @Test
    void shouldThrowExceptionWhenEmailIsEmpty() {
        User user = User.builder()
                .email("")
                .login("validLogin")
                .name("validName")
                .birthday(LocalDate.of(2000,1,1))
                .build();
        assertThrows(ValidationException.class, () -> userService.userCreate(user));
    }

    @Test
    void shouldThrowExceptionWhenEmailDoesNotContainAtSymbol() {
        User user = User.builder()
                .email("invalidEmail.com")
                .login("validLogin")
                .name("validName")
                .birthday(LocalDate.of(2000, 1, 1))
                .build();
        assertThrows(ValidationException.class, () -> userService.userCreate(user));
    }

    @Test
    void shouldThrowExceptionWhenLoginIsNull() {
        User user = User.builder()
                .email("validEmail@example.com")
                .login(null)
                .name("validName")
                .birthday(LocalDate.of(2000, 1, 1))
                .build();
        assertThrows(ValidationException.class, () -> userService.userCreate(user));
    }

    @Test
    void shouldThrowExceptionWhenLoginIsEmpty() {
        User user = User.builder()
                .email("validEmail@example.com")
                .login("")
                .name("validName")
                .birthday(LocalDate.of(2000, 1, 1))
                .build();
        assertThrows(ValidationException.class, () -> userService.userCreate(user));
    }

    @Test
    void shouldThrowExceptionWhenBirthdayIsInFuture() {
        User user = User.builder()
                .email("validEmail@example.com")
                .login("validLogin")
                .name("validName")
                .birthday(LocalDate.of(3000, 1, 1))
                .build();
        assertThrows(ValidationException.class, () -> userService.userCreate(user));
    }

    @Test
    void shouldSetNameToLoginWhenNameIsNull() {
        User user = User.builder()
                .email("validEmail@example.com")
                .login("validLogin")
                .name(null) // имя отсутствует
                .birthday(LocalDate.of(2000, 1, 1))
                .build();
        User createdUser  = userService.userCreate(user);
        assertEquals("validLogin", createdUser .getName());
    }

    @Test
    void shouldThrowExceptionWhenUserIdIsNullOnUpdate() {
        User user = User.builder()
                .id(null)
                .email("validEmail@example.com")
                .login("validLogin")
                .name("validName")
                .birthday(LocalDate.of(2000, 1, 1))
                .build();
        assertThrows(ValidationException.class, () -> userService.userUpdate(user));
    }

    @Test
    void shouldThrowExceptionWhenUserIdNotFoundOnUpdate() {
        User user = User.builder()
                .id(999L)
                .email("validEmail@example.com")
                .login("validLogin")
                .name("validName")
                .birthday(LocalDate.of(2000, 1, 1))
                .build();
        assertThrows(NotFoundException.class, () -> userService.userUpdate(user));
    }
}

