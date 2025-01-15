package ru.yandex.practicum.filmorate.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;

import java.util.*;


@Service
public class FilmService {

    private final FilmStorage filmStorage;
    private static Logger log = LoggerFactory.getLogger(FilmService.class);
    private Map<Long, Set<Long>> filmLikes = new HashMap<>();

    public FilmService(FilmStorage filmStorage) {
        this.filmStorage = filmStorage;
    }

    public Film filmCreate(Film film) {
        return filmStorage.addFilm(film);
    }

    public Film filmUpdate(Film updateFilm) {
        return filmStorage.updateFilm(updateFilm);
    }

    public Film findById(Long filmId) {
        return filmStorage.getFilmById(filmId);
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
