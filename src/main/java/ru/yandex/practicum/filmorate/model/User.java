package ru.yandex.practicum.filmorate.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Builder
@Data
public class User {
    Long id;
    String email;
    String login;
    String name;
    LocalDate birthday;

    @Builder.Default
    Set<Long> friends = new HashSet<>();
}
