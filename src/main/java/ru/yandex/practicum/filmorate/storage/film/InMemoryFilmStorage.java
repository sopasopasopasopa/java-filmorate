package ru.yandex.practicum.filmorate.storage.film;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class InMemoryFilmStorage implements FilmStorage {
    private static final LocalDate MIN_RELEASE_DATE = LocalDate.of(1895, 12, 28);
    private Map<Long, Film> films = new HashMap<>();

    @Override
    public Film addFilm(Film film) {
        validateFilm(film);
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

    @Override
    public Film updateFilm(Film updateFilm) {
        if (updateFilm.getId() == null) {
            throw new ValidationException("Id should not be empty");
        }
        if (!films.containsKey(updateFilm.getId())) {
            throw new NotFoundException("Film not found");
        }
        validateFilm(updateFilm);

        films.put(updateFilm.getId(), updateFilm);
        return updateFilm;
    }

    @Override
    public List<Film> getAllFilms() {
        return new ArrayList<>(films.values());
    }

    @Override
    public void deleteFilmById(Long filmId) {
        Film removeFilm = films.get(filmId);
        if (removeFilm != null) {
            films.remove(filmId);
        } else {
            throw new NotFoundException("User  for deletion with ID " + filmId + " not found");
        }
    }

    @Override
    public void clear() {
        films.clear();
    }

    private void validateFilm(Film film) {
        if (film.getName() == null || film.getName().isEmpty()) {
            throw new ValidationException("Film title should not be empty");
        }
        if (film.getDescription().length() >= 200) {
            throw new ValidationException("Maximum description length — 200 characters");
        }
        if (film.getReleaseDate().isBefore(MIN_RELEASE_DATE)) {
            throw new ValidationException("Film release date: no earlier than 28.12.1895");
        }
        if (film.getDuration() == null || film.getDuration() <= 0) {
            throw new ValidationException("Film duration should not be negative");
        }
    }

    @Override
    public Film getFilmById(Long id) {
        if (!films.containsKey(id)) {
            throw new NotFoundException("Film not found");
        }
        return films.get(id);
    }

    private long nextId() {
        return films.keySet()
                .stream()
                .mapToLong(Long::longValue)
                .max()
                .orElse(0L) + 1;
    }
}
