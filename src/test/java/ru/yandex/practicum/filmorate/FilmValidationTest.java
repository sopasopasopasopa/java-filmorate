package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.controller.FilmController;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class FilmValidationTest {

    @Test
    public void testFilmNameValidation() {
        Film film = new Film();
        film.setName("");
        film.setDescription("A valid description");
        film.setReleaseDate(Instant.now());
        film.setDuration(Duration.ofMinutes(90));

        assertThrows(ValidationException.class, () -> {
            new FilmController().validateFilm(film);
        });
    }

    @Test
    public void testFilmDescriptionValidation() {
        Film film = new Film();
        film.setName("Valid Name");
        film.setDescription("A".repeat(201)); // превышает 200 символов
        film.setReleaseDate(Instant.now());
        film.setDuration(Duration.ofMinutes(90));

        assertThrows(ValidationException.class, () -> {
            new FilmController().validateFilm(film);
        });
    }

    @Test
    public void testFilmReleaseDateValidation() {
        Film film = new Film();
        film.setName("Valid Name");
        film.setDescription("A valid description");
        film.setReleaseDate(LocalDate.of(1895, 12, 27).atStartOfDay(ZoneOffset.UTC).toInstant());
        film.setDuration(Duration.ofMinutes(90));

        assertThrows(ValidationException.class, () -> {
            new FilmController().validateFilm(film);
        });
    }


    @Test
    public void testFilmDurationValidation() {
        Film film = new Film();
        film.setName("Valid Name");
        film.setDescription("A valid description");
        film.setReleaseDate(Instant.now());
        film.setDuration(Duration.ZERO); // нулевая продолжительность

        assertThrows(ValidationException.class, () -> {
            new FilmController().validateFilm(film);
        });
    }
}
