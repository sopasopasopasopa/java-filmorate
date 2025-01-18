package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.FriendService;
import ru.yandex.practicum.filmorate.service.UserService;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/users")
@Validated
public class UserController {

    private final UserService userService;
    private final FriendService friendService;

    public UserController(UserService userService, FriendService friendService) {
        this.userService = userService;
        this.friendService = friendService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public User userCreate(@Valid @RequestBody User user) {
        return userService.userCreate(user);
    }

    @PutMapping
    public User userUpdate(@Valid @RequestBody User updateUser) {
        return userService.userUpdate(updateUser);
    }

    @PutMapping("/{userId}/friends/{friendId}")
    public Set<Long> addFriend(@PathVariable Long userId, @PathVariable Long friendId) {
        return friendService.addFriends(userId, friendId);
    }

    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{userId}/friends")
    public List<User> getFriends(@PathVariable Long userId) {
        return friendService.getAllFriends(userId);
    }

    @GetMapping("/{userId}")
    public User findById(@PathVariable Long userId) {
        return userService.findById(userId);
    }

    @GetMapping("/{userId}/friends/common/{mutualFriendId}")
    public List<User> getListCommonFriend(@PathVariable Long userId, @PathVariable Long mutualFriendId) {
        return friendService.listMutualFriends(userId, mutualFriendId);
    }

    @DeleteMapping("/{userId}")
    public void deleteUserById(@PathVariable Long userId) {
        userService.deleteUserById(userId);
    }

    @DeleteMapping("/{userId}/friends/{friendId}")
    public Set<Long> deleteFriend(@PathVariable long userId, @PathVariable Long friendId) {
        return friendService.deleteFriend(userId, friendId);
    }

}
