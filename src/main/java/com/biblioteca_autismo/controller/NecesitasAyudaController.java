package com.biblioteca_autismo.controller;

import com.biblioteca_autismo.model.NecesitasAyuda;
import com.biblioteca_autismo.response.NecesitasAyudaResponseRest;
import com.biblioteca_autismo.service.INecesitasAyudaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/v1")
public class NecesitasAyudaController {

    @Autowired
    private INecesitasAyudaService service;


    @PostMapping("/Request")
    public ResponseEntity<NecesitasAyudaResponseRest> addNewComentary (@RequestBody NecesitasAyuda necesitasAyuda) {
        ResponseEntity<NecesitasAyudaResponseRest> response = service.insertarRequest(necesitasAyuda);
        return response;
    }

    @GetMapping("/Request")
    public ResponseEntity<NecesitasAyudaResponseRest> getAllComentary() {
        ResponseEntity<NecesitasAyudaResponseRest> response = service.obtenerRequest();
        return response;
    }

    @DeleteMapping("/Request/{id}")
    public ResponseEntity<NecesitasAyudaResponseRest> deleteComentary (@PathVariable Long id) {
        ResponseEntity<NecesitasAyudaResponseRest> response = service.EliminarRequest(id);
        return response;
    }


}
