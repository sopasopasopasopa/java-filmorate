package ru.yandex.practicum.filmorate.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

/**
 * Film.
 */
@Builder
@Data
public class Film {
    Long id;
    String title;
    String description;
    LocalDate releaseDate;
    Long duration;
}
