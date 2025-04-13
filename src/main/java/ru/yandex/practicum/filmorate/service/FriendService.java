package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
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

    public void addFriend(Long userId, Long friendId) {
        User user = getUserById(userId);
        User friend = getUserById(friendId);

        // Проверяем существование дружбы в обе стороны
        if (user.getFriends().contains(friendId) || friend.getFriends().contains(userId)) {
            log.warn("Friendship between {} and {} already exists", userId, friendId);
            return;
        }

        // Добавляем взаимную дружбу
        user.getFriends().add(friendId);
        friend.getFriends().add(userId);

        // Сохраняем обоих пользователей
        userStorage.updateUser(user);
        userStorage.updateUser(friend);

        log.info("Mutual friendship created between {} and {}", userId, friendId);
    }

    public Set<Long> removeFriend(Long userId, Long friendId) {
        User user = getUserById(userId);
        User friend = getUserById(friendId);

        // Удаляем дружбу в обе стороны
        boolean removedFromUser = user.getFriends().remove(friendId);
        boolean removedFromFriend = friend.getFriends().remove(userId);

        if (!removedFromUser && !removedFromFriend) {
            log.warn("No friendship found between {} and {}", userId, friendId);
            return user.getFriends();
        }

        // Сохраняем изменения
        userStorage.updateUser(user);
        userStorage.updateUser(friend);

        log.info("Mutual friendship removed between {} and {}", userId, friendId);
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