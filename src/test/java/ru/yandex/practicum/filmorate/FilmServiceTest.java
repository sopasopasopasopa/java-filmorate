package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.InMemoryFilmStorage;

import java.time.LocalDate;
import java.util.List;

class FilmServiceTest {

    private FilmService filmService;
    private InMemoryFilmStorage filmStorage;

    private static final LocalDate MIN_RELEASE_DATE = LocalDate.of(1895, 12, 28);
    private Film film;

    @BeforeEach
    public void setUp() {
        filmStorage = new InMemoryFilmStorage();
        filmService = new FilmService(filmStorage);
        film = Film.builder()
                .name("Titanic")
                .description("A long description")
                .releaseDate(LocalDate.of(1997, 12, 16))
                .duration(195L)
                .build();
    }

    @Test
    public void shouldCreateFilm() {
        Film createdFilm = filmService.filmCreate(film);

        Assertions.assertNotNull(createdFilm);
        Assertions.assertEquals("Titanic", createdFilm.getName());
        Assertions.assertEquals(1L, createdFilm.getId());
    }

    @Test
    public void shouldUpdateFilm() {
        Film createdFilm = filmService.filmCreate(film);
        createdFilm.setName("Titanic Updated");

        Film updatedFilm = filmService.filmUpdate(createdFilm);

        Assertions.assertNotNull(updatedFilm);
        Assertions.assertEquals("Titanic Updated", updatedFilm.getName());
    }

    @Test
    public void shouldFindFilmById() {
        Film createdFilm = filmService.filmCreate(film);

        Film foundFilm = filmService.findById(createdFilm.getId());

        Assertions.assertNotNull(foundFilm);
        Assertions.assertEquals("Titanic", foundFilm.getName());
    }

    @Test
    public void shouldGetAllFilms() {
        filmService.filmCreate(film);
        Film anotherFilm = Film.builder()
                .name("Avatar")
                .description("A long description")
                .releaseDate(LocalDate.of(2009, 12, 18))
                .duration(162L)
                .build();
        filmService.filmCreate(anotherFilm);

        List<Film> films = filmService.getAllFilms();

        Assertions.assertNotNull(films);
        Assertions.assertEquals(2, films.size());
    }

    @Test
    public void shouldDeleteFilmById() {
        Film createdFilm = filmService.filmCreate(film);
        filmService.deleteFilmById(createdFilm.getId());

        Assertions.assertThrows(NotFoundException.class, () -> filmService.findById(createdFilm.getId()));
    }

    @Test
    public void shouldThrowNotFoundExceptionWhenFilmNotFound() {
        Assertions.assertThrows(NotFoundException.class, () -> filmService.findById(999L)); // Non-existing ID
    }

    @Test
    public void shouldThrowValidationExceptionWhenFilmIsInvalid() {
        Film invalidFilm = Film.builder()
                .name("")
                .description("A long description")
                .releaseDate(LocalDate.of(1890, 1, 1)) // Before the minimum date
                .duration(-10L) // Invalid duration
                .build();

        Assertions.assertThrows(ValidationException.class, () -> filmService.filmCreate(invalidFilm));
    }
}