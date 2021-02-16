package net.stawrul.controllers;

import net.stawrul.model.Film;
import net.stawrul.services.FilmsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

import static org.springframework.http.HttpStatus.CONFLICT;



@RestController
@RequestMapping("/films")
public class FilmsController {

    final FilmsService filmsService;

    public FilmsController(FilmsService filmsService) {
        this.filmsService = filmsService;
    }

    @GetMapping
    public List<Film> listFilms() {
        return filmsService.findAll();
    }


    @PostMapping
    public ResponseEntity<Void> addFilm(@RequestBody Film film, UriComponentsBuilder uriBuilder) {

        if (filmsService.find(film.getId()) == null) {
            filmsService.save(film);
            URI location = uriBuilder.path("/films/{id}").buildAndExpand(film.getId()).toUri();
            return ResponseEntity.created(location).build();

        } else {
            return ResponseEntity.status(CONFLICT).build();
        }
    }


    @GetMapping("/{id}")
    public ResponseEntity<Film> getFilm(@PathVariable UUID id) {
        Film film = filmsService.find(id);
        return film != null ? ResponseEntity.ok(film) : ResponseEntity.notFound().build();
    }


    @PutMapping("/{id}")
    public ResponseEntity<Void> updateFilm(@RequestBody Film film) {
        if (filmsService.find(film.getId()) != null) {
            filmsService.save(film);
            return ResponseEntity.ok().build();

        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
