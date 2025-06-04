package com.biblioteca_autismo.service;

import com.biblioteca_autismo.model.Libro;
import com.biblioteca_autismo.response.LibroResponseRest;
import org.springframework.http.ResponseEntity;

public interface ILibroService {


    public ResponseEntity<LibroResponseRest> insertarLibro (Libro libro);

    public ResponseEntity<LibroResponseRest> ObtenerLibro (Long id);

    public ResponseEntity<LibroResponseRest> ObtenerLibros ();

    public ResponseEntity<LibroResponseRest> ActualizarLibro (Libro libro, Long id);

    public ResponseEntity<LibroResponseRest> EliminarLibro (Long id);
}
