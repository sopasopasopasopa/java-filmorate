package ru.yandex.practicum.filmorate.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.storage.mpa.MpaDao;

import java.util.List;

@RestController
@RequestMapping("/mpa")
public class MpaController {
    private final MpaDao mpaDao;

    @Autowired
    public MpaController(MpaDao mpaDao) {
        this.mpaDao = mpaDao;
    }

    @GetMapping
    public List<Mpa> getAllMpa() {
        return mpaDao.getAllMpa();
    }

    @GetMapping("/{id}")
    public Mpa getMpaById(@PathVariable int id) {
        return mpaDao.getMpaById(id)
                .orElseThrow(() -> new NotFoundException("MPA с id = " + id + " не найден"));
    }
}