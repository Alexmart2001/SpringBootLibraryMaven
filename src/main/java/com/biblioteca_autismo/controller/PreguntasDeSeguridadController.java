package com.biblioteca_autismo.controller;

import com.biblioteca_autismo.model.PreguntasDeSeguridad;
import com.biblioteca_autismo.response.PreguntasDeSeguridadResponseRest;
import com.biblioteca_autismo.service.IPreguntasDeSeguridadService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/v1")
public class PreguntasDeSeguridadController {

    @Autowired
    private IPreguntasDeSeguridadService service;

    @PostMapping("/Conexion")
    public ResponseEntity<PreguntasDeSeguridadResponseRest> AddNewConnection(@RequestBody PreguntasDeSeguridad preguntasDeSeguridad) {
        ResponseEntity<PreguntasDeSeguridadResponseRest> response = service.insertarQuestion(preguntasDeSeguridad);
        return response;
    }

    @GetMapping("/Conexion/{id}")
    public ResponseEntity<PreguntasDeSeguridadResponseRest> GetConnection(@PathVariable Long id) {
        ResponseEntity<PreguntasDeSeguridadResponseRest> response = service.ObtenerPreguntasDeSeguridad(id);
        return response;
    }

    @GetMapping("/Conexion")
    public ResponseEntity<PreguntasDeSeguridadResponseRest> GetAllConnection() {
        ResponseEntity<PreguntasDeSeguridadResponseRest> response = service.ObtenerPreguntas();
        return response;
    }

    @DeleteMapping("/Conexion/{id}")
    public ResponseEntity<PreguntasDeSeguridadResponseRest> DeleteConnection(@PathVariable Long id) {
        ResponseEntity<PreguntasDeSeguridadResponseRest> response = service.EliminarPregunta(id);
        return response;
    }

    @GetMapping("/Conexion/Usuario/{id}")
    public ResponseEntity<PreguntasDeSeguridadResponseRest> GetConnectionUsuario(@PathVariable Long id) {
        ResponseEntity<PreguntasDeSeguridadResponseRest> response = service.BuscarPorUser(id);
        return response;
    }

    @PostMapping("/validarRespuesta")
    public ResponseEntity<Boolean> validarRespuesta(
            @RequestParam Long usuarioId,
            @RequestParam Long idPregunta,
            @RequestParam String respuestaUsuario) {
        return service.validarRespuesta(usuarioId,idPregunta, respuestaUsuario);
    }


}
