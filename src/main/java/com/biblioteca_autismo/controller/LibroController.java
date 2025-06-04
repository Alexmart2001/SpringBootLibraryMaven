package com.biblioteca_autismo.controller;

import com.biblioteca_autismo.model.Libro;
import com.biblioteca_autismo.response.LibroResponseRest;
import com.biblioteca_autismo.service.ILibroService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/v1")
public class LibroController {

    @Autowired
    private ILibroService service;


    @PostMapping("/Libros")
    public ResponseEntity<LibroResponseRest> AddNewBook(@RequestBody Libro libro) {
        ResponseEntity<LibroResponseRest> response = service.insertarLibro(libro);
        return response;
    }

    @GetMapping("/Libros")
    public ResponseEntity<LibroResponseRest> getAllLibros() {
        ResponseEntity<LibroResponseRest> response = service.ObtenerLibros();
        return response;
    }

    @GetMapping("/Libros/{id}")
    public ResponseEntity<LibroResponseRest> getLibroById(@PathVariable Long id) {
        ResponseEntity<LibroResponseRest> response = service.ObtenerLibro(id);
        return response;
    }

    @PutMapping("/Libros/{id}")
    public ResponseEntity<LibroResponseRest> UpdateLibro(@PathVariable Long id, @RequestBody Libro libro) {
        ResponseEntity<LibroResponseRest> response = service.ActualizarLibro(libro, id);
        return response;
    }

    @DeleteMapping("/Libros/{id}")
    public ResponseEntity<LibroResponseRest> DeleteLibro(@PathVariable Long id) {
        ResponseEntity<LibroResponseRest> response = service.EliminarLibro(id);
        return response;
    }


}
