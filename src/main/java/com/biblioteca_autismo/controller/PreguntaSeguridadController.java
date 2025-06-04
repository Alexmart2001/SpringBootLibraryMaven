package com.biblioteca_autismo.controller;

import com.biblioteca_autismo.model.PreguntasSeguridad;
import com.biblioteca_autismo.response.PreguntaSeguridadResponseRest;
import com.biblioteca_autismo.service.IPreguntasSeguridadService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/v1")
public class PreguntaSeguridadController {


    @Autowired
    private IPreguntasSeguridadService service;

    @PostMapping("/Preguntas")
    public ResponseEntity<PreguntaSeguridadResponseRest> AddNewQuestion(@RequestBody PreguntasSeguridad preguntasSeguridad) {
        ResponseEntity<PreguntaSeguridadResponseRest> response = service.insertarPregunta(preguntasSeguridad);
        return response;
    }

    @GetMapping("/Preguntas")
    public ResponseEntity<PreguntaSeguridadResponseRest> getAllPreguntas() {
        ResponseEntity<PreguntaSeguridadResponseRest> response = service.ObtenerPreguntas();
        return response;
    }

    @DeleteMapping("/Preguntas/{id}")
    public ResponseEntity<PreguntaSeguridadResponseRest> deletePregunta(@PathVariable Long id) {
        ResponseEntity<PreguntaSeguridadResponseRest> response = service.EliminarPreguntas(id);
        return response;
    }

}
