package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
public class FriendService {
    private final UserStorage userStorage;

    @Autowired
    public FriendService(UserStorage userStorage) {
        this.userStorage = userStorage;
    }

    @Transactional
    public void addFriend(Long userId, Long friendId) {

        if (userId.equals(friendId)) {
            throw new ValidationException("User cannot add himself as friend");
        }

        User user = getUserById(userId);
        User friend = getUserById(friendId);

        // Проверяем существование пользователей
        if (!userStorage.getUserById(userId).isPresent() ||
                !userStorage.getUserById(friendId).isPresent()) {
            throw new NotFoundException("User not found");
        }

        if (user.getFriends().contains(friendId)) {
            return;
        }

        // Добавляем дружбу в обоих направлениях
        user.getFriends().add(friendId);
        friend.getFriends().add(userId);

        userStorage.updateUser(user);
        userStorage.updateUser(friend);
    }

    public Set<Long> removeFriend(long userId, Long friendId) {
        User user = getUserById(userId);
        if (!user.getFriends().contains(friendId)) {
            throw new NotFoundException("Friend not found");
        }
        user.getFriends().remove(friendId);
        userStorage.updateUser(user);
        return user.getFriends();
    }

    public List<User> getFriends(Long userId) {
        User user = getUserById(userId);
        return user.getFriends().stream()
                .map(this::getUserById)
                .collect(Collectors.toList());
    }


    public List<User> getCommonFriends(Long userId, Long otherUserId) {
        User user = getUserById(userId);
        User otherUser = getUserById(otherUserId);

        return user.getFriends().stream()
                .filter(friendId -> otherUser.getFriends().contains(friendId))
                .map(this::getUserById)
                .collect(Collectors.toList());
    }

    private User getUserById(Long userId) {
        return userStorage.getUserById(userId)
                .orElseThrow(() -> new NotFoundException("User with ID " + userId + " not found"));
    }
}