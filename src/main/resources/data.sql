-- Инициализация MPA с использованием MERGE (аналог ON CONFLICT для H2)
MERGE INTO mpa (mpa_id, name) KEY(mpa_id) VALUES
(1, 'G'),
(2, 'PG'),
(3, 'PG-13'),
(4, 'R'),
(5, 'NC-17');

-- Инициализация жанров
MERGE INTO genres (genre_id, name) KEY(genre_id) VALUES
(1, 'Комедия'),
(2, 'Драма'),
(3, 'Мультфильм'),
(4, 'Триллер'),
(5, 'Документальный'),
(6, 'Боевик');

-- Тестовые пользователи
MERGE INTO users (user_id, email, login, name, birthday) KEY(email) VALUES
(1, 'user1@example.com', 'user1', 'John Doe', '1990-01-01'),
(2, 'user2@example.com', 'user2', 'Jane Smith', '1995-05-15'),
(3, 'user3@example.com', 'user3', 'Mike Johnson', '2000-10-20');

-- Тестовые фильмы
MERGE INTO films (film_id, name, description, release_date, duration, mpa_id) KEY(film_id) VALUES
(1, 'Inception', 'A mind-bending thriller', '2010-07-16', 148, 3),
(2, 'The Shawshank Redemption', 'Two imprisoned men bond over a number of years', '1994-09-23', 142, 4);

-- Связи фильмов с жанрами
MERGE INTO film_genre (film_id, genre_id) KEY(film_id, genre_id) VALUES
(1, 4),
(1, 6),
(2, 2);

-- Дружеские связи
MERGE INTO friendship (user_id, friend_id) KEY(user_id, friend_id) VALUES
(1, 2),
(2, 3);

-- Лайки фильмов
MERGE INTO likes (film_id, user_id) KEY(film_id, user_id) VALUES
(1, 1),
(2, 2);