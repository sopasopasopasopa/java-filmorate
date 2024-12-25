package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.controller.FilmController;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.Duration;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class FilmControllerTest {

    private FilmController filmController;

    @BeforeEach
    public void setUp() {
        filmController = new FilmController();
    }

    @Test
    public void testValidateFilm_NullName() {
        Film film = new Film();
        film.setName(null);
        film.setDescription("Some description");
        film.setReleaseDate(LocalDate.of(2000, 1, 1));
        film.setDuration(Duration.ofMinutes(120));

        ValidationException exception = assertThrows(ValidationException.class, () -> {
            filmController.validateFilm(film);
        });

        assertEquals("Film name cannot be empty.", exception.getMessage());
    }

    @Test
    public void testValidateFilm_BlankName() {
        Film film = new Film();
        film.setName(" ");
        film.setDescription("Some description");
        film.setReleaseDate(LocalDate.of(2000, 1, 1));
        film.setDuration(Duration.ofMinutes(120));

        ValidationException exception = assertThrows(ValidationException.class, () -> {
            filmController.validateFilm(film);
        });

        assertEquals("Film name cannot be empty.", exception.getMessage());
    }

    @Test
    public void testValidateFilm_DescriptionTooLong() {
        Film film = new Film();
        film.setName("Valid Name");
        film.setDescription("A".repeat(201)); // 201 символ
        film.setReleaseDate(LocalDate.of(2000, 1, 1));
        film.setDuration(Duration.ofMinutes(120));

        ValidationException exception = assertThrows(ValidationException.class, () -> {
            filmController.validateFilm(film);
        });

        assertEquals("Film description cannot exceed 200 characters.", exception.getMessage());
    }

    @Test
    public void testValidateFilm_ReleaseDateTooEarly() {
        Film film = new Film();
        film.setName("Valid Name");
        film.setDescription("Some description");
        film.setReleaseDate(LocalDate.of(1895, 12, 27)); // до минимальной даты
        film.setDuration(Duration.ofMinutes(120));

        ValidationException exception = assertThrows(ValidationException.class, () -> {
            filmController.validateFilm(film);
        });

        assertEquals("Release date cannot be before December 28, 1895.", exception.getMessage());
    }

    @Test
    public void testValidateFilm_NegativeDuration() {
        Film film = new Film();
        film.setName("Valid Name");
        film.setDescription("Some description");
        film.setReleaseDate(LocalDate.of(2000, 1, 1));
        film.setDuration(Duration.ofMinutes(-1)); // отрицательная продолжительность

        ValidationException exception = assertThrows(ValidationException.class, () -> {
            filmController.validateFilm(film);
        });

        assertEquals("Film duration must be a positive number.", exception.getMessage());
    }

    @Test
    public void testValidateFilm_ValidFilm() {
        Film film = new Film();
        film.setName("Valid Name");
        film.setDescription("Some description");
        film.setReleaseDate(LocalDate.of(2000, 1, 1));
        film.setDuration(Duration.ofMinutes(120));

        // Не должно выбрасывать исключение
        filmController.validateFilm(film);
    }
}
