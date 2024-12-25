package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/films")
@Slf4j
public class FilmController {

    private final List<Film> films = new ArrayList<>();
    private int currentId = 1;
    private static final LocalDate MIN_RELEASE_DATE = LocalDate.of(1895, 12, 28);

    @PostMapping
    public Film addFilm(@RequestBody Film film) {
        try {
            validateFilm(film);
            film.setId(currentId++);
            films.add(film);
            log.info("Film added: {}", film);
            return film;
        } catch (ValidationException e) {
            log.error("Validation failed for film: {}. Reason: {}", film, e.getMessage());
            throw e;
        }
    }

    @PutMapping("/{id}")
    public Film updateFilm(@PathVariable int id, @RequestBody Film updatedFilm) {
        try {
            validateFilm(updatedFilm);
            for (int i = 0; i < films.size(); i++) {
                if (films.get(i).getId() == id) {
                    updatedFilm.setId(id);
                    films.set(i, updatedFilm);
                    log.info("Film updated: {}", updatedFilm);
                    return updatedFilm;
                }
            }
            log.error("Film not found with id: {}", id);
            throw new RuntimeException("Film not found");
        } catch (ValidationException e) {
            log.error("Validation failed for film: {}. Reason: {}", updatedFilm, e.getMessage());
            throw e;
        }
    }

    @GetMapping
    public List<Film> getAllFilms() {
        log.info("Fetching all films. Total films: {}", films.size());
        return films;
    }

    public void validateFilm(Film film) {
        if (film.getName() == null || film.getName().isBlank()) {
            throw new ValidationException("Film name cannot be empty.");
        }
        if (film.getDescription() != null && film.getDescription().length() > 200) {
            throw new ValidationException("Film description cannot exceed 200 characters.");
        }
        if (film.getReleaseDate() == null || film.getReleaseDate().isBefore(MIN_RELEASE_DATE)) {
            throw new ValidationException("Release date cannot be before December 28, 1895.");
        }
        if (film.getDuration() == null || film.getDuration().isNegative()) {
            throw new ValidationException("Film duration must be a positive number.");
        }
    }
}
