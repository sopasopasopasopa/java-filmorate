package ru.yandex.practicum.filmorate.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Builder
@Data
public class User {
    Long id;

    @Email(message = "Некорректный формат email")
    private String email;


    @NotBlank(message = "Логин не может быть пустым")
    @Pattern(regexp = "\\S+", message = "Логин не может содержать пробелы")
    private String login;

    String name;

    @PastOrPresent(message = "Дата рождения не может быть в будущем") // Добавить
    private LocalDate birthday;

    @JsonIgnore
    @Builder.Default
    Set<Long> friends = new HashSet<>();
}
