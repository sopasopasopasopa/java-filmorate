package ru.yandex.practicum.filmorate.storage.user;

import org.apache.logging.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

@Repository
public class UserDbStorage implements UserStorage {
    private final JdbcTemplate jdbcTemplate;
    private Logger log;

    public UserDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<User> getUserById(Long id) {
        String sql = "SELECT * FROM users WHERE user_id = ?";
        try {
            User user = jdbcTemplate.queryForObject(sql, this::mapRowToUser, id);
            if (user != null) {
                loadFriends(user);
            }
            return Optional.ofNullable(user);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public User addUser(User user) {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("users")
                .usingGeneratedKeyColumns("user_id");

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("email", user.getEmail());
        parameters.put("login", user.getLogin());
        parameters.put("name", user.getName());
        parameters.put("birthday", user.getBirthday());

        Number key = simpleJdbcInsert.executeAndReturnKey(parameters);
        user.setId(key.longValue());
        return user;
    }

    @Override
    @Transactional
    public User updateUser(User user) {
        // Обновление основных данных
        String sql = "UPDATE users SET email=?, login=?, name=?, birthday=? WHERE user_id=?";
        int updated = jdbcTemplate.update(
                sql,
                user.getEmail(),
                user.getLogin(),
                user.getName(),
                user.getBirthday(),
                user.getId()
        );

        if (updated == 0) {
            throw new NotFoundException("User not found");
        }

        // Отдельно обновляем друзей
        updateFriends(user);

        return user;
    }

    @Override
    public List<User> getAllUsers() {
        String sql = "SELECT * FROM users";
        return jdbcTemplate.query(sql, this::mapRowToUser);
    }

    @Override
    public void deleteUserById(Long id) {
        String sql = "DELETE FROM users WHERE user_id = ?";
        jdbcTemplate.update(sql, id);
    }

    @Transactional
    @Override
    public void clear() {
        jdbcTemplate.update("DELETE FROM friendship");
        jdbcTemplate.update("DELETE FROM likes");
        jdbcTemplate.update("DELETE FROM users");
        jdbcTemplate.update("ALTER TABLE users ALTER COLUMN user_id RESTART WITH 1");
        log.info("Cleared all users and related data");
    }

    private User mapRowToUser(ResultSet rs, int rowNum) throws SQLException {
        return User.builder()
                .id(rs.getLong("user_id"))
                .email(rs.getString("email"))
                .login(rs.getString("login"))
                .name(rs.getString("name"))
                .birthday(rs.getDate("birthday").toLocalDate())
                .build();
    }

    private void loadFriends(User user) {
        String sql = "SELECT friend_id FROM friendship WHERE user_id = ?";
        Set<Long> friends = new HashSet<>(jdbcTemplate.query(
                sql,
                (rs, rowNum) -> rs.getLong("friend_id"),
                user.getId()));
        user.setFriends(friends);
    }

    private void updateFriends(User user) {
        // Удаляем все текущие дружеские связи пользователя
        jdbcTemplate.update("DELETE FROM friendship WHERE user_id = ?", user.getId());

        // Добавляем новые связи только если есть друзья
        if (!user.getFriends().isEmpty()) {
            List<Object[]> batchArgs = user.getFriends().stream()
                    .map(friendId -> new Object[]{user.getId(), friendId})
                    .collect(Collectors.toList());

            jdbcTemplate.batchUpdate(
                    "INSERT INTO friendship (user_id, friend_id) VALUES (?, ?)",
                    batchArgs
            );
        }
    }
}