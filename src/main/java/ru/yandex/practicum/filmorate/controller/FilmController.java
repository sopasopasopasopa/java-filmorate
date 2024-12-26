package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;

import java.util.List;

@RestController
@RequestMapping("/films")
public class FilmController {

    private final FilmService filmService;

    public FilmController(FilmService filmService) {
        this.filmService = filmService;
    }

    @PostMapping
    public Film filmCreate(@Valid @RequestBody Film film) {
        return filmService.filmCreate(film);
    }

    @PutMapping
    public Film filmUpdate(@Valid @RequestBody Film updateFilm) {
        return filmService.filmUpdate(updateFilm);
    }
}
