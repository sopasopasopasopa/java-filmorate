package ru.yandex.practicum.filmorate.model;

import lombok.Data;

import java.time.LocalDate;

/**
 * Film.
 */
@Data
public class Film {
    String name;
    String description;
    LocalDate releaseDate;
    Long duration;
}
