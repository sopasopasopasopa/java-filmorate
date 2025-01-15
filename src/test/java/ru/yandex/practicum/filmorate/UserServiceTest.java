package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;
import ru.yandex.practicum.filmorate.storage.user.InMemoryUserStorage;

import java.time.LocalDate;
import java.util.List;

class UserServiceTest {

    private UserService userService;
    private InMemoryUserStorage userStorage;

    private User user;

    @BeforeEach
    public void setUp() {
        userStorage = new InMemoryUserStorage();
        userService = new UserService(userStorage);
        user = User.builder()
                .email("test@example.com")
                .login("testLogin")
                .name("Test User")
                .birthday(LocalDate.of(1990, 1, 1))
                .build();
    }

    @Test
    public void shouldCreateUser() {
        User createdUser = userService.userCreate(user);

        Assertions.assertNotNull(createdUser);
        Assertions.assertEquals("testLogin", createdUser.getLogin());
        Assertions.assertEquals(1L, createdUser.getId());
    }

    @Test
    public void shouldUpdateUser() {
        User createdUser = userService.userCreate(user);
        createdUser.setName("Updated User");

        User updatedUser = userService.userUpdate(createdUser);

        Assertions.assertNotNull(updatedUser);
        Assertions.assertEquals("Updated User", updatedUser.getName());
    }

    @Test
    public void shouldFindUserById() {
        User createdUser = userService.userCreate(user);

        User foundUser = userService.findById(createdUser.getId());

        Assertions.assertNotNull(foundUser);
        Assertions.assertEquals("testLogin", foundUser.getLogin());
    }

    @Test
    public void shouldGetAllUsers() {
        userService.userCreate(user);
        User anotherUser = User.builder()
                .email("another@example.com")
                .login("anotherLogin")
                .name("Another User")
                .birthday(LocalDate.of(1995, 5, 5))
                .build();
        userService.userCreate(anotherUser);

        List<User> users = userService.getAllUsers();

        Assertions.assertNotNull(users);
        Assertions.assertEquals(2, users.size());
    }

    @Test
    public void shouldDeleteUserById() {
        User createdUser = userService.userCreate(user);
        userService.deleteUserById(createdUser.getId());

        Assertions.assertThrows(NotFoundException.class, () -> userService.findById(createdUser.getId()));
    }

    @Test
    public void shouldThrowNotFoundExceptionWhenUserNotFound() {
        Assertions.assertThrows(NotFoundException.class, () -> userService.findById(999L));
    }

    @Test
    public void shouldThrowValidationExceptionWhenUserIsInvalid() {
        User invalidUser = User.builder()
                .email("")
                .login("")
                .name("")
                .birthday(LocalDate.of(2025, 1, 1))
                .build();

        Assertions.assertThrows(ValidationException.class, () -> userService.userCreate(invalidUser));
    }
}
