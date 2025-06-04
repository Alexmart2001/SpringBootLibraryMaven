package com.biblioteca_autismo.service;

import com.biblioteca_autismo.model.Juegos;
import com.biblioteca_autismo.response.JuegosResponseRest;
import org.springframework.http.ResponseEntity;

public interface IJuegosService {

    public ResponseEntity<JuegosResponseRest> insertarJuego (Juegos juegos);

    public ResponseEntity<JuegosResponseRest> ObtenerJuegos();

    public ResponseEntity<JuegosResponseRest> EliminarJuego (Long id);
}
