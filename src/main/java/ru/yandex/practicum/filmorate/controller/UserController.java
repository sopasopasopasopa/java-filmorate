package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public User userCreate(@Valid @RequestBody User user) {
        return userService.userCreate(user);
    }

    @PutMapping
    public User userUpdate(@Valid @RequestBody User updateUser) {
        return userService.userUpdate(updateUser);
    }



}
