package com.biblioteca_autismo.service;

import com.biblioteca_autismo.model.PreguntasDeSeguridad;
import com.biblioteca_autismo.response.PreguntasDeSeguridadResponseRest;
import org.springframework.http.ResponseEntity;

public interface IPreguntasDeSeguridadService {

    public ResponseEntity<PreguntasDeSeguridadResponseRest> insertarQuestion (PreguntasDeSeguridad preguntasDeSeguridad);

    public ResponseEntity<PreguntasDeSeguridadResponseRest> ObtenerPreguntasDeSeguridad (Long id);

    public ResponseEntity<PreguntasDeSeguridadResponseRest> ObtenerPreguntas ();

    public ResponseEntity<PreguntasDeSeguridadResponseRest> EliminarPregunta (Long id);

    public ResponseEntity<PreguntasDeSeguridadResponseRest> BuscarPorUser (Long id);

    public ResponseEntity<Boolean> validarRespuesta(Long usuarioId, Long idPregunta, String respuesta);
}
