package ru.yandex.practicum.filmorate.service;

import jakarta.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.Comparator;
import java.util.List;
import java.util.Set;

@Service
public class LikeService {
    private static final Logger log = LoggerFactory.getLogger(LikeService.class);
    private final FilmStorage filmStorage;
    private final UserStorage userStorage;

    public LikeService(FilmStorage filmStorage, UserStorage userStorage) {
        this.filmStorage = filmStorage;
        this.userStorage = userStorage;
    }

    public Set<Long> addLike(Long filmId, Long userId) {
        Film film = filmStorage.getFilmById(filmId)
                .orElseThrow(() -> new NotFoundException("Фильм не найден"));
        log.debug("Adding like from user {} to film {}", userId, filmId);

        Film likedFilm = filmStorage.getFilmById(filmId)
                .orElseThrow(() -> {
                    log.error("Film with ID {} not found", filmId);
                    return new ValidationException("Film not found");
                });

        User user = userStorage.getUserById(userId)
                .orElseThrow(() -> {
                    log.error("User with ID {} who is adding the like does not exist", userId);
                    return new NotFoundException("User with Id " + userId + " not found");
                });

        if (!likedFilm.getUserLikes().add(userId)) {
            log.warn("User {} has already liked film {}", userId, filmId);
            throw new ValidationException("User can only like a film once");
        }

        likedFilm.setLikes(likedFilm.getLikes() + 1);
        filmStorage.updateFilm(likedFilm);

        log.info("User {} successfully liked film {}", userId, filmId);
        return likedFilm.getUserLikes();
    }

    public Set<Long> deleteLike(@NotNull Long filmId, @NotNull Long userId) {
        log.debug("Removing like from user {} to film {}", userId, filmId);

        Film film = filmStorage.getFilmById(filmId)
                .orElseThrow(() -> {
                    log.warn("Film with ID {} does not exist", filmId);
                    return new NotFoundException("Film with Id " + filmId + " not found");
                });

        User user = userStorage.getUserById(userId)
                .orElseThrow(() -> {
                    log.error("User with ID {} who is removing the like does not exist", userId);
                    return new NotFoundException("User with Id " + userId + " not found");
                });

        if (!film.getUserLikes().remove(userId)) {
            throw new NotFoundException("Like not found");
        }

        film.setLikes(film.getLikes() - 1);
        filmStorage.updateFilm(film);
        log.debug("User {} successfully removed like from film {}", userId, filmId);

        return film.getUserLikes();
    }

    public List<Film> getTopFilms(Integer count) {
        log.debug("Getting top {} films", count);
        List<Film> films = filmStorage.getAllFilms();

        return films.stream()
                .sorted(Comparator.comparingInt(Film::getLikes).reversed())
                .limit(count)
                .toList();
    }
}