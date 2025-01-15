package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * Film.
 */
@Builder
@Data
public class Film {
    Long id;

    @NotBlank(message = "Film title should not be empty")
    String name;

    String description;
    LocalDate releaseDate;
    Long duration;

    @Builder.Default
    Set<Long> userLikes = new HashSet<>();

    int likes;
}
