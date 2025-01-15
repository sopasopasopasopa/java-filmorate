package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.service.LikeService;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/films")
public class FilmController {

    private final FilmService filmService;
    private final LikeService likeService;

    public FilmController(FilmService filmService, LikeService likeService) {
        this.filmService = filmService;
        this.likeService = likeService;
    }

    @PostMapping
    public Film filmCreate(@Valid @RequestBody Film film) {
        return filmService.filmCreate(film);
    }

    @PutMapping
    public Film filmUpdate(@Valid @RequestBody Film updateFilm) {
        return filmService.filmUpdate(updateFilm);
    }

    @PutMapping("/{filmId}/like/{userId}")
    public Set<Long> addLikeFilm(@PathVariable Long filmId, @PathVariable Long userId) {
        return likeService.addLike(filmId, userId);
    }

    @GetMapping
    public List<Film> getAllFilms() {
        return filmService.getAllFilms();
    }

    @GetMapping("/{filmId}")
    public Film findById(@PathVariable Long filmId) {
        return filmService.findById(filmId);
    }

    @GetMapping("/popular")
    public List<Film> topFilms(@RequestParam(defaultValue = "10") Integer count) {
        return likeService.topTenPopularMovies(count);
    }

    @DeleteMapping("/{filmId}/like/{userId}")
    public Set<Long> deleteLike(@PathVariable Long filmId, @PathVariable Long userId) {
        return likeService.deleteLike(filmId, userId);
    }

    @DeleteMapping("/{filmId}")
    public void deleteFilmById(@PathVariable Long filmId) {
        filmService.deleteFilmById(filmId);
    }
}
