package ru.yandex.practicum.filmorate.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FilmService {

    private static final LocalDate MIN_RELEASE_DATE = LocalDate.of(1895, 12, 28);
    private Map<Long, Film> films = new HashMap<>();
    private static Logger log = LoggerFactory.getLogger(FilmService.class);

    public Film filmCreate(Film film) {
        if (film.getName() == null || film.getName().isEmpty()) {
            log.warn("Film title {}", film.getName());
            throw new ValidationException("Film title should not be empty");
        }
        if (film.getDescription().length() >= 200) {
            log.warn("Film description {}", film.getDescription());
            throw new ValidationException("Maximum description length — 200 characters");
        }
        if (film.getReleaseDate().isBefore(MIN_RELEASE_DATE)) {
            log.warn("Film releaseDate {}", film.getReleaseDate());
            throw new ValidationException("Film release date: no earlier than 28.12.1895");
        }
        if (film.getDuration() == null || film.getDuration() <= 0) {
            log.warn("Film duration {}", film.getDuration());
            throw new ValidationException("Film duration should ot be negative");
        }

        Film createFilm = Film.builder()
                .id(nextId())
                .name(film.getName())
                .description(film.getDescription())
                .releaseDate(film.getReleaseDate())
                .duration(film.getDuration())
                .build();

        films.put(createFilm.getId(), createFilm);

        return createFilm;

    }

    public Film filmUpdate(Film updateFilm) {
        if (updateFilm.getId() == null) {
            log.warn("Id is empty");
            throw new ValidationException("Id should not be empty");
        }
        if (!films.containsKey(updateFilm.getId())) {
            log.warn("Film with this Id not found {}", updateFilm.getId());
            throw new NotFoundException("Film not found");
        }
        Film existingFilm = films.get(updateFilm.getId());

        Film newFilm = Film.builder()
                .id(existingFilm.getId())
                .name(updateFilm.getName())
                .description(updateFilm.getDescription())
                .releaseDate(updateFilm.getReleaseDate())
                .duration(updateFilm.getDuration())
                .build();

        films.put(newFilm.getId(), newFilm);
        return newFilm;
    }

    public List<Film> getAllFilms() {
        return new ArrayList<>(films.values());
    }

    private long nextId() {
        return films.keySet()
                .stream()
                .mapToLong(Long::longValue)
                .max()
                .orElse(0L) + 1;
    }

}
