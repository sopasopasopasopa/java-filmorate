package ru.yandex.practicum.filmorate.service;

import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;

import java.time.LocalDate;
import java.util.*;


@Service
public class FilmService {

    private final FilmStorage filmStorage;

    public FilmService(FilmStorage filmStorage) {
        this.filmStorage = filmStorage;
    }

    public Film filmCreate(Film film) {
        if (film.getReleaseDate().isBefore(LocalDate.of(1895, 12, 28))) {
            throw new ValidationException("Дата релиза не может быть раньше 28 декабря 1895 года");
        }

        if (film.getDuration() <= 0) {
            throw new ValidationException("Продолжительность фильма должна быть положительной");
        }

        return filmStorage.addFilm(film);
    }

    public Film filmUpdate(Film updateFilm) {
        return filmStorage.updateFilm(updateFilm);
    }

    public Film findById(Long filmId) {
        return filmStorage.getFilmById(filmId)
                .orElseThrow(() -> new NotFoundException("Film with id " + filmId + " not found"));
    }

    public List<Film> getAllFilms() {
        return filmStorage.getAllFilms();
    }

    public void clearFilm() {
        filmStorage.clear();
    }

    public void deleteFilmById(Long filmId) {
        filmStorage.deleteFilmById(filmId);
    }
}
