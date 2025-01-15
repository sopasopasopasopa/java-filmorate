package ru.yandex.practicum.filmorate.service;

import jakarta.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class FriendService {

    private static Logger log = LoggerFactory.getLogger(FriendService.class);
    private final UserStorage userStorage;

    public FriendService(UserStorage userStorage) {
        this.userStorage = userStorage;
    }

    public Set<Long> addFriends(@NotNull Long userId, @NotNull Long friendId) {
        User user = userStorage.getUserById(userId);
        User friend = userStorage.getUserById(friendId);

        if (user == null || friend == null) {
            throw new NotFoundException("User with Id: " + userId + " or friend with Id " + friendId + " not found");
        }
        if (user.getFriends().contains(friendId)) {
            throw new ValidationException("User is already a friend");
        }

        user.getFriends().add(friendId);
        friend.getFriends().add(userId);
        userStorage.updateUser(user);
        userStorage.updateUser(friend);

        return user.getFriends();
    }

    public Set<Long> deleteFriend(@NotNull Long userId, @NotNull Long friendId) {
        User user = userStorage.getUserById(userId);
        User friend = userStorage.getUserById(userId);

        if (user == null || friend == null) {
            throw new NotFoundException("User or friend with Id " + userId + " " + friendId + " not found");
        }

        user.getFriends().remove(friendId);
        friend.getFriends().remove(userId);

        userStorage.updateUser(user);
        userStorage.updateUser(friend);

        return user.getFriends();
    }

    public List<User> getAllFriends(@NotNull Long userId) {
        User user = userStorage.getUserById(userId);
        if (user == null) {
            throw new NotFoundException("User with Id " + userId + " not found.");
        }

        Set<Long> friendsIds = user.getFriends();
        List<User> friendList = friendsIds.stream()
                .map(userStorage::getUserById)
                .toList();
        return friendList;
    }

    public List<User> listMutualFriends(@NotNull Long userId, @NotNull Long friendId) {
        User user = userStorage.getUserById(userId);
        User friend = userStorage.getUserById(friendId);

        if (user == null || friend == null) {
            throw new NotFoundException("One of the users with ID: " + userId + " or " + friendId + " not found");
        }

        Set<Long> mutualFriendIds = findMutualFriends(user, friend);
        List<User> mutualFriends = mutualFriendIds.stream()
                .map(userStorage::getUserById)
                .collect(Collectors.toList());

        return mutualFriends;
    }

    private Set<Long> findMutualFriends(User user, User friend) {
        Set<Long> friendsOfUser  = user.getFriends();
        friendsOfUser .retainAll(friend.getFriends());
        return friendsOfUser ;
    }



}
