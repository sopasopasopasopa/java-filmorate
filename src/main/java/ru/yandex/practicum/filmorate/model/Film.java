package ru.yandex.practicum.filmorate.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    String name;
    String description;
    LocalDate releaseDate;
    Long duration;

    @JsonIgnore
    @Builder.Default
    Set<Long> userLikes = new HashSet<>();

    int likes;
}
