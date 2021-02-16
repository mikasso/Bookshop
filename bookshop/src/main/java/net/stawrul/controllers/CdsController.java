package net.stawrul.controllers;

import net.stawrul.model.Cd;
import net.stawrul.services.CdsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

import static org.springframework.http.HttpStatus.CONFLICT;


@RestController
@RequestMapping("/cds")
public class CdsController {
    final CdsService cdsService;

    public CdsController(CdsService cdsService) {
        this.cdsService = cdsService;
    }

    @GetMapping
    public List<Cd> listCds() {
        return cdsService.findAll();
    }

    @PostMapping
    public ResponseEntity<Void> addCd(@RequestBody Cd cd, UriComponentsBuilder uriBuilder) {

        if (cdsService.find(cd.getId()) == null) {
            cdsService.save(cd);

            URI location = uriBuilder.path("/cds/{id}").buildAndExpand(cd.getId()).toUri();
            return ResponseEntity.created(location).build();

        } else {
            return ResponseEntity.status(CONFLICT).build();
        }
    }


    @GetMapping("/{id}")
    public ResponseEntity<Cd> getCd(@PathVariable UUID id) {
        Cd cd = cdsService.find(id);

        return cd != null ? ResponseEntity.ok(cd) : ResponseEntity.notFound().build();
    }


    @PutMapping("/{id}")
    public ResponseEntity<Void> updateCd(@RequestBody Cd cd) {
        if (cdsService.find(cd.getId()) != null) {
            cdsService.save(cd);
            return ResponseEntity.ok().build();

        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
