package ru.yandex.practicum.filmorate.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Builder
@Data
public class User {
    Long id;
    String email;
    String login;
    String name;
    LocalDate birthday;
}
