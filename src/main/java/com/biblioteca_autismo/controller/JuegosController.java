package com.biblioteca_autismo.controller;

import com.biblioteca_autismo.model.Juegos;
import com.biblioteca_autismo.response.JuegosResponseRest;
import com.biblioteca_autismo.service.IJuegosService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/v1")
public class JuegosController {

    @Autowired
    private IJuegosService service;

    @PostMapping("/Juegos")
    public ResponseEntity<JuegosResponseRest> AddNewGame(@RequestBody Juegos juegos) {
        ResponseEntity<JuegosResponseRest> response = service.insertarJuego(juegos);
        return response;
    }

    @GetMapping("/Juegos")
    public ResponseEntity<JuegosResponseRest> getJuegos() {
        ResponseEntity<JuegosResponseRest> response = service.ObtenerJuegos();
        return response;
    }

    @DeleteMapping("/Juegos/{id}")
    public ResponseEntity<JuegosResponseRest> deleteJuego(@PathVariable Long id) {
        ResponseEntity<JuegosResponseRest> response = service.EliminarJuego(id);
        return response;
    }
}
