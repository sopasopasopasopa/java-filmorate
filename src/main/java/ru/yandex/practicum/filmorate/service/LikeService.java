package ru.yandex.practicum.filmorate.service;

import jakarta.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.Comparator;
import java.util.List;
import java.util.Set;

public class LikeService {

    private static Logger log = LoggerFactory.getLogger(LikeService.class);
    private final FilmStorage filmStorage;
    private final UserStorage userStorage;

    public LikeService(FilmStorage filmStorage, UserStorage userStorage) {
        this.filmStorage = filmStorage;
        this.userStorage = userStorage;
    }

    public Set<Long> addLike(@NotNull Long filmId, @NotNull Long userId) {
        log.debug("Adding like from user {} to film {}", userId, filmId);

        Film LikedFilm = filmStorage.getFilmById(filmId);
        if (LikedFilm == null) {
            log.error("Film with ID {} not found", filmId);
            throw new ValidationException("Film not found");
        }
        if (userStorage.getUserById(userId) == null) {
            log.error("User  with ID {} who is adding the like does not exist", userId);
            throw new NotFoundException("User with Id " + userId + " who wants to like does not exist");
        }
        if (!LikedFilm.getUserLikes().add(userId)) {
            log.warn("User  {} has already liked film {}", userId, filmId);
            throw new ValidationException("User can only like a film once");
        }

        LikedFilm.setLikes(LikedFilm.getLikes() + 1);
        filmStorage.updateFilm(LikedFilm);

        log.info("User  {} successfully liked film {}", userId, filmId);

        return LikedFilm.getUserLikes();
    }

    public Set<Long> deleteLike(@NotNull Long filmId, @NotNull Long userId) {
        log.debug("Removing like from user {} to film {}", userId, filmId);

        Film removeLikeForFilm = filmStorage.getFilmById(filmId);
        if (removeLikeForFilm == null) {
            log.warn("Film with ID {} does not exist", filmId);
            throw new NotFoundException("Film with Id " + filmId + " does not exist");
        }
        if (userStorage.getUserById(userId) == null || !removeLikeForFilm.getUserLikes().remove(userId)) {
            log.error("User  with ID {} who is removing the like does not exist", userId);
            throw new NotFoundException("User with Id " + userId + " who wants to remove like does not exist");
        }

        removeLikeForFilm.setLikes(removeLikeForFilm.getLikes() - 1);
        filmStorage.updateFilm(removeLikeForFilm);
        log.debug("User  {} successfully removed like from film {}", userId, filmId);

        return removeLikeForFilm.getUserLikes();
    }

    public List<Film> topTenPopularMovies(Integer count) {
        log.debug("Number of films to create the list {}", count);

        List<Film> films = filmStorage.getAllFilms();
        log.debug("List of films for sorting {}", films);

        return films.stream()
                .sorted(Comparator.comparingInt(Film::getLikes).reversed())
                .limit(count)
                .toList();
    }
}
