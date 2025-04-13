-- MPA Ratings
MERGE INTO mpa (mpa_id, name) VALUES
(1, 'G'),
(2, 'PG'),
(3, 'PG-13'),
(4, 'R'),
(5, 'NC-17');

-- Genres
MERGE INTO genres (genre_id, name) VALUES
(1, 'Comedy'),
(2, 'Drama'),
(3, 'Animation'),
(4, 'Thriller'),
(5, 'Documentary'),
(6, 'Action');

-- Sample Users
INSERT INTO users (email, login, name, birthday) VALUES
('user1@example.com', 'user1', 'John Doe', '1990-01-01'),
('user2@example.com', 'user2', 'Jane Smith', '1995-05-15');

-- Sample Films
INSERT INTO films (name, description, release_date, duration, mpa_id) VALUES
('Inception', 'A mind-bending thriller', '2010-07-16', 148, 3),
('The Shawshank Redemption', 'Two imprisoned men bond over a number of years', '1994-09-23', 142, 4);

-- Film Genres
INSERT INTO film_genre (film_id, genre_id) VALUES
(1, 4), -- Inception - Thriller
(1, 6), -- Inception - Action
(2, 2); -- Shawshank - Drama

-- Friendships
INSERT INTO friendship (user_id, friend_id) VALUES
(1, 2); -- User 1 is friends with User 2

-- Likes
INSERT INTO likes (film_id, user_id) VALUES
(1, 1), -- User 1 likes Inception
(2, 2); -- User 2 likes Shawshank