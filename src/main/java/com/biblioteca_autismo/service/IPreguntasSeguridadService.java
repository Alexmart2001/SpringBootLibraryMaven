package com.biblioteca_autismo.service;

import com.biblioteca_autismo.model.PreguntasSeguridad;
import com.biblioteca_autismo.response.PreguntaSeguridadResponseRest;
import org.springframework.http.ResponseEntity;

public interface IPreguntasSeguridadService {

    public ResponseEntity<PreguntaSeguridadResponseRest> insertarPregunta (PreguntasSeguridad preguntasSeguridad);

    public ResponseEntity<PreguntaSeguridadResponseRest> ObtenerPreguntas ();

    public ResponseEntity<PreguntaSeguridadResponseRest> EliminarPreguntas (Long id);
}
