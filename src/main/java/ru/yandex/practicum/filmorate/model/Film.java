package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.NotBlank;
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
    @NotBlank
    String name;
    String description;
    LocalDate releaseDate;
    Long duration;
}
