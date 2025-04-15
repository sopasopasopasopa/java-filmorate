-- Инициализация MPA
INSERT INTO mpa (mpa_id, name)
VALUES (1, 'G'),
       (2, 'PG'),
       (3, 'PG-13'),
       (4, 'R'),
       (5, 'NC-17')
ON CONFLICT (mpa_id) DO NOTHING;

-- Инициализация жанров
INSERT INTO genres (genre_id, name)
VALUES (1, 'Комедия'),
       (2, 'Драма'),
       (3, 'Мультфильм'),
       (4, 'Триллер'),
       (5, 'Документальный'),
       (6, 'Боевик')
ON CONFLICT (genre_id) DO NOTHING;

-- Тестовые пользователи
INSERT INTO users (email, login, name, birthday)
VALUES ('user1@example.com', 'user1', 'John Doe', '1990-01-01'),
       ('user2@example.com', 'user2', 'Jane Smith', '1995-05-15'),
       ('user3@example.com', 'user3', 'Mike Johnson', '2000-10-20')
ON CONFLICT (email) DO NOTHING;

-- Тестовые фильмы
INSERT INTO films (name, description, release_date, duration, mpa_id)
VALUES ('Inception', 
        'A mind-bending thriller', 
        '2010-07-16', 
        148, 
        (SELECT mpa_id FROM mpa WHERE name = 'PG-13')),
       
       ('The Shawshank Redemption', 
        'Two imprisoned men bond over a number of years', 
        '1994-09-23', 
        142, 
        (SELECT mpa_id FROM mpa WHERE name = 'R'))
ON CONFLICT (name) DO NOTHING;

-- Связи фильмов с жанрами
INSERT INTO film_genre (film_id, genre_id)
VALUES ((SELECT film_id FROM films WHERE name = 'Inception'), 4),
       ((SELECT film_id FROM films WHERE name = 'Inception'), 6),
       ((SELECT film_id FROM films WHERE name = 'The Shawshank Redemption'), 2)
ON CONFLICT (film_id, genre_id) DO NOTHING;

-- Дружеские связи
INSERT INTO friendship (user_id, friend_id)
VALUES (1, 2),
       (2, 3)
ON CONFLICT (user_id, friend_id) DO NOTHING;

-- Лайки фильмов
INSERT INTO likes (film_id, user_id)
VALUES (1, 1),
       (2, 2)
ON CONFLICT (film_id, user_id) DO NOTHING;