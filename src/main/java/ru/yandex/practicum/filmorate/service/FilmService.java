package ru.yandex.practicum.filmorate.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Service
public class FilmService {

    private static final LocalDate MIN_RELESE_DATE = LocalDate.of(1895, 12, 28);
    private Map<Long, Film> films = new HashMap<>();
    private static Logger log = LoggerFactory.getLogger(FilmService.class);

}
