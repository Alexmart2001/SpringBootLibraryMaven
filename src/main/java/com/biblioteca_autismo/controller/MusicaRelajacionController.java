package com.biblioteca_autismo.controller;

import com.biblioteca_autismo.model.MusicaRelajacion;
import com.biblioteca_autismo.response.MusicaRelajacionResponseRest;
import com.biblioteca_autismo.service.IMusicaRelajacionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/v1")
public class MusicaRelajacionController {

    @Autowired
    IMusicaRelajacionService service;

    @PostMapping("/cancion")
    public ResponseEntity<MusicaRelajacionResponseRest> insertarCancion(@RequestBody MusicaRelajacion musicaRelajacion) {
        ResponseEntity<MusicaRelajacionResponseRest> response = service.insertarCancion(musicaRelajacion);
        return response;
    }

    @PutMapping("/cancion/{id}")
    public ResponseEntity<MusicaRelajacionResponseRest> editarCancion(@RequestBody MusicaRelajacion musicaRelajacion, @PathVariable Long id) {
        ResponseEntity<MusicaRelajacionResponseRest> response = service.EditarCancion(musicaRelajacion, id);
        return response;
    }

    @GetMapping("/cancion")
    public ResponseEntity<MusicaRelajacionResponseRest> getCancion() {
        ResponseEntity<MusicaRelajacionResponseRest> response = service.obtenerCancion();
        return response;
    }

    @DeleteMapping("/cancion/{id}")
    public ResponseEntity<MusicaRelajacionResponseRest> borrarCancion(@PathVariable Long id) {
        ResponseEntity<MusicaRelajacionResponseRest> response = service.borrarCancion(id);
        return response;
    }


}
