-- Clear existing tables if they exist
DROP TABLE IF EXISTS film_genre;
DROP TABLE IF EXISTS friendship;
DROP TABLE IF EXISTS likes;
DROP TABLE IF EXISTS films;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS genres;
DROP TABLE IF EXISTS mpa;

-- Create tables
CREATE TABLE IF NOT EXISTS mpa (
    mpa_id INT PRIMARY KEY,
    name VARCHAR(20) NOT NULL
);

CREATE TABLE IF NOT EXISTS genres (
    genre_id INT PRIMARY KEY,
    name VARCHAR(50) NOT NULL
);

CREATE TABLE IF NOT EXISTS users (
    user_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(50) NOT NULL,
    login VARCHAR(50) NOT NULL,
    name VARCHAR(50),
    birthday DATE NOT NULL,
    CONSTRAINT uq_user_email UNIQUE (email),
    CONSTRAINT uq_user_login UNIQUE (login)
);

CREATE TABLE IF NOT EXISTS films (
    film_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    description VARCHAR(200),
    release_date DATE NOT NULL,
    duration INT NOT NULL,
    mpa_id INT REFERENCES mpa (mpa_id),
    CONSTRAINT min_release_date CHECK (release_date >= '1895-12-28')
);

CREATE TABLE IF NOT EXISTS film_genre (
    film_id BIGINT REFERENCES films (film_id) ON DELETE CASCADE,
    genre_id INT REFERENCES genres (genre_id) ON DELETE CASCADE,
    PRIMARY KEY (film_id, genre_id)
);

CREATE TABLE IF NOT EXISTS likes (
    film_id BIGINT REFERENCES films (film_id) ON DELETE CASCADE,
    user_id BIGINT REFERENCES users (user_id) ON DELETE CASCADE,
    PRIMARY KEY (film_id, user_id)
);

CREATE TABLE IF NOT EXISTS friendship (
    user_id BIGINT REFERENCES users (user_id) ON DELETE CASCADE,
    friend_id BIGINT REFERENCES users (user_id) ON DELETE CASCADE,
    PRIMARY KEY (user_id, friend_id)
);-- Clear existing tables if they exist
  DROP TABLE IF EXISTS film_genre;
  DROP TABLE IF EXISTS friendship;
  DROP TABLE IF EXISTS likes;
  DROP TABLE IF EXISTS films;
  DROP TABLE IF EXISTS users;
  DROP TABLE IF EXISTS genres;
  DROP TABLE IF EXISTS mpa;

  -- Create tables
  CREATE TABLE IF NOT EXISTS mpa (
      mpa_id INT PRIMARY KEY,
      name VARCHAR(20) NOT NULL
  );

  CREATE TABLE IF NOT EXISTS genres (
      genre_id INT PRIMARY KEY,
      name VARCHAR(50) NOT NULL
  );

  CREATE TABLE IF NOT EXISTS users (
      user_id BIGINT AUTO_INCREMENT PRIMARY KEY,
      email VARCHAR(50) NOT NULL,
      login VARCHAR(50) NOT NULL,
      name VARCHAR(50),
      birthday DATE NOT NULL,
      CONSTRAINT uq_user_email UNIQUE (email),
      CONSTRAINT uq_user_login UNIQUE (login)
  );

  CREATE TABLE IF NOT EXISTS films (
      film_id BIGINT AUTO_INCREMENT PRIMARY KEY,
      name VARCHAR(100) NOT NULL,
      description VARCHAR(200),
      release_date DATE NOT NULL,
      duration INT NOT NULL,
      mpa_id INT REFERENCES mpa (mpa_id),
      CONSTRAINT min_release_date CHECK (release_date >= '1895-12-28')
  );

  CREATE TABLE IF NOT EXISTS film_genre (
      film_id BIGINT REFERENCES films (film_id) ON DELETE CASCADE,
      genre_id INT REFERENCES genres (genre_id) ON DELETE CASCADE,
      PRIMARY KEY (film_id, genre_id)
  );

  CREATE TABLE IF NOT EXISTS likes (
      film_id BIGINT REFERENCES films (film_id) ON DELETE CASCADE,
      user_id BIGINT REFERENCES users (user_id) ON DELETE CASCADE,
      PRIMARY KEY (film_id, user_id)
  );

  CREATE TABLE IF NOT EXISTS friendship (
      user_id BIGINT REFERENCES users (user_id) ON DELETE CASCADE,
      friend_id BIGINT REFERENCES users (user_id) ON DELETE CASCADE,
      PRIMARY KEY (user_id, friend_id)
  );-- Clear existing tables if they exist
    DROP TABLE IF EXISTS film_genre;
    DROP TABLE IF EXISTS friendship;
    DROP TABLE IF EXISTS likes;
    DROP TABLE IF EXISTS films;
    DROP TABLE IF EXISTS users;
    DROP TABLE IF EXISTS genres;
    DROP TABLE IF EXISTS mpa;

    -- Create tables
    CREATE TABLE IF NOT EXISTS mpa (
        mpa_id INT PRIMARY KEY,
        name VARCHAR(20) NOT NULL
    );

    CREATE TABLE IF NOT EXISTS genres (
        genre_id INT PRIMARY KEY,
        name VARCHAR(50) NOT NULL
    );

    CREATE TABLE IF NOT EXISTS users (
        user_id BIGINT AUTO_INCREMENT PRIMARY KEY,
        email VARCHAR(50) NOT NULL,
        login VARCHAR(50) NOT NULL,
        name VARCHAR(50),
        birthday DATE NOT NULL,
        CONSTRAINT uq_user_email UNIQUE (email),
        CONSTRAINT uq_user_login UNIQUE (login)
    );

    CREATE TABLE IF NOT EXISTS films (
        film_id BIGINT AUTO_INCREMENT PRIMARY KEY,
        name VARCHAR(100) NOT NULL,
        description VARCHAR(200),
        release_date DATE NOT NULL,
        duration INT NOT NULL,
        mpa_id INT REFERENCES mpa (mpa_id),
        CONSTRAINT min_release_date CHECK (release_date >= '1895-12-28')
    );

    CREATE TABLE IF NOT EXISTS film_genre (
        film_id BIGINT REFERENCES films (film_id) ON DELETE CASCADE,
        genre_id INT REFERENCES genres (genre_id) ON DELETE CASCADE,
        PRIMARY KEY (film_id, genre_id)
    );

    CREATE TABLE IF NOT EXISTS likes (
        film_id BIGINT REFERENCES films (film_id) ON DELETE CASCADE,
        user_id BIGINT REFERENCES users (user_id) ON DELETE CASCADE,
        PRIMARY KEY (film_id, user_id)
    );

    CREATE TABLE IF NOT EXISTS friendship (
        user_id BIGINT REFERENCES users (user_id) ON DELETE CASCADE,
        friend_id BIGINT REFERENCES users (user_id) ON DELETE CASCADE,
        PRIMARY KEY (user_id, friend_id)
    );-- Clear existing tables if they exist
      DROP TABLE IF EXISTS film_genre;
      DROP TABLE IF EXISTS friendship;
      DROP TABLE IF EXISTS likes;
      DROP TABLE IF EXISTS films;
      DROP TABLE IF EXISTS users;
      DROP TABLE IF EXISTS genres;
      DROP TABLE IF EXISTS mpa;

      -- Create tables
      CREATE TABLE IF NOT EXISTS mpa (
          mpa_id INT PRIMARY KEY,
          name VARCHAR(20) NOT NULL
      );

      CREATE TABLE IF NOT EXISTS genres (
          genre_id INT PRIMARY KEY,
          name VARCHAR(50) NOT NULL
      );

      CREATE TABLE IF NOT EXISTS users (
          user_id BIGINT AUTO_INCREMENT PRIMARY KEY,
          email VARCHAR(50) NOT NULL,
          login VARCHAR(50) NOT NULL,
          name VARCHAR(50),
          birthday DATE NOT NULL,
          CONSTRAINT uq_user_email UNIQUE (email),
          CONSTRAINT uq_user_login UNIQUE (login)
      );

      CREATE TABLE IF NOT EXISTS films (
          film_id BIGINT AUTO_INCREMENT PRIMARY KEY,
          name VARCHAR(100) NOT NULL,
          description VARCHAR(200),
          release_date DATE NOT NULL,
          duration INT NOT NULL,
          mpa_id INT REFERENCES mpa (mpa_id),
          CONSTRAINT min_release_date CHECK (release_date >= '1895-12-28')
      );

      CREATE TABLE IF NOT EXISTS film_genre (
          film_id BIGINT REFERENCES films (film_id) ON DELETE CASCADE,
          genre_id INT REFERENCES genres (genre_id) ON DELETE CASCADE,
          PRIMARY KEY (film_id, genre_id)
      );

      CREATE TABLE IF NOT EXISTS likes (
          film_id BIGINT REFERENCES films (film_id) ON DELETE CASCADE,
          user_id BIGINT REFERENCES users (user_id) ON DELETE CASCADE,
          PRIMARY KEY (film_id, user_id)
      );

      CREATE TABLE IF NOT EXISTS friendship (
          user_id BIGINT REFERENCES users (user_id) ON DELETE CASCADE,
          friend_id BIGINT REFERENCES users (user_id) ON DELETE CASCADE,
          PRIMARY KEY (user_id, friend_id)
      );