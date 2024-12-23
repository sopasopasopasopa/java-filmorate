package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.Duration;
import java.time.Instant;

@Data
public class Film {
    private Long id;

    @NotBlank(message = "Название фильма не может быть пустым")
    private String name;

    private String description;

    @NotNull(message = "Дата релиза не может быть пустой")
    private Instant releaseDate;

    @NotNull(message = "Продолжительность фильма должна быть указана")
    private Duration duration;
}