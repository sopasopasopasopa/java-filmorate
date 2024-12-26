package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class FilmServiceTest {

    private FilmService filmService;

    @BeforeEach
    void setUp() {
        filmService = new FilmService();
    }

    @Test
    void shouldThrowExceptionWhenTitleIsEmpty() {
        Film film = Film.builder()
                .title("")
                .build();
        assertThrows(ValidationException.class, () -> filmService.filmCreate(film));
    }

    @Test
    void shouldThrowExceptionWhenDescriptionIsTooLong() {
        String longDescription = "a".repeat(201);
        Film film = Film.builder()
                .title("Valid Title")
                .description(longDescription)
                .build();
        assertThrows(ValidationException.class, () -> filmService.filmCreate(film));
    }
}