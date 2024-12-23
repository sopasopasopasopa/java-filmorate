package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.Instant;

@Data
public class User {
    private Long id;

    @NotBlank(message = "Электронная почта не может быть пустой и должна содержать символ @")
    @Email(message = "Некорректный формат электронной почты")
    private String email;

    @NotBlank(message = "Логин не может быть пустым и содержать пробелы")
    private String login;

    private String name;

    @NotNull(message = "Дата рождения не может быть пустой")
    private Instant birthday;
}