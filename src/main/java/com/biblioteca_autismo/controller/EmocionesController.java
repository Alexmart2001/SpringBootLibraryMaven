package com.biblioteca_autismo.controller;


import com.biblioteca_autismo.model.Emocion;
import com.biblioteca_autismo.response.EmocionesResponseRest;
import com.biblioteca_autismo.service.IEmocionesService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/v1")
public class EmocionesController {

    @Autowired
    private IEmocionesService service;

    @PostMapping("/Emocion")
    public ResponseEntity<EmocionesResponseRest> AddNewDate (@RequestBody Emocion emocion) {
        ResponseEntity<EmocionesResponseRest> response = service.insertarEmocion(emocion);
        return response;
    }

    @GetMapping("/Emocion/{id}")
    public ResponseEntity<EmocionesResponseRest> GetEmocion (@PathVariable Long id) {
        ResponseEntity<EmocionesResponseRest> response = service.ObtenerEmociones(id);
        return response;
    }

    @DeleteMapping("/Emocion/{id}")
    public ResponseEntity<EmocionesResponseRest> DeleteEmocion (@PathVariable Long id) {
        ResponseEntity<EmocionesResponseRest> response = service.EliminarEmocion(id);
        return response;
    }
}
