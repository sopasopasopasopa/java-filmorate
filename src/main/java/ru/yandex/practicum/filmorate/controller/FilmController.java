package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.*;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/films")
public class FilmController {

    private final List<Film> films = new ArrayList<>();
    private Long nextId = 1L;

    @PostMapping
    public Film addFilm(@Valid @RequestBody Film film) {
        film.setId(getNextId());
        films.add(film);
        log.info("Фильм {} успешно добавлен", film);
        return film;
    }

    @PutMapping
    public Film updateFilm(@RequestBody Film film) {
        if (film.getId() == null) {
            log.error("Ошибка обновления фильма: {}", "Id должен быть указан");
            throw new ConditionsNotMetException("Id должен быть указан");
        }

        Film existingFilm = films.stream()
                .filter(f -> f.getId().equals(film.getId()))
                .findFirst()
                .orElseThrow(() -> {
                    log.error("Ошибка обновления фильма: {}", "Фильм не найден");
                    return new ConditionsNotMetException("Фильм не найден");
                });

        validateFilm(film);

        if (film.getName() != null) {
            existingFilm.setName(film.getName());
        }
        if (film.getDescription() != null) {
            existingFilm.setDescription(film.getDescription());
        }
        if (film.getReleaseDate() != null) {
            existingFilm.setReleaseDate(film.getReleaseDate());
        }
        if (film.getDuration() != null) {
            existingFilm.setDuration(film.getDuration());
        }

        log.info("Фильм {} успешно обновлен", existingFilm);
        return existingFilm;
    }

    public void validateFilm(Film film) {
        if (film.getName() == null || film.getName().isEmpty()) {
            log.error("Ошибка валидации фильма: {}", "Название фильма не может быть пустым");
            throw new ValidationException("Название фильма не может быть пустым");
        }
        if (film.getDescription() != null && film.getDescription().length() > 200) {
            log.error("Ошибка валидации фильма: {}", "Максимальная длина описания — 200 символов");
            throw new ValidationException("Максимальная длина описания — 200 символов");
        }
        if (film.getReleaseDate() == null || film.getReleaseDate().isBefore(LocalDate.of(1895, 12, 28).atStartOfDay(ZoneOffset.UTC).toInstant())) {
            log.error("Ошибка валидации фильма: {}", "Дата релиза не может быть раньше 28 декабря 1895 года");
            throw new ValidationException("Дата релиза не может быть раньше 28 декабря 1895 года");
        }
        if (film.getDuration() == null || film.getDuration().isNegative() || film.getDuration().isZero()) {
            log.error("Ошибка валидации фильма: {}", "Продолжительность фильма должна быть положительным числом");
            throw new ValidationException("Продолжительность фильма должна быть положительным числом");
        }
    }


    @GetMapping
    public List<Film> getAllFilms() {
        log.info("Получение списка всех фильмов");
        return films;
    }

    private Long getNextId() {
        return nextId++;
    }
}