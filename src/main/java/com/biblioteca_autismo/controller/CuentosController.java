package com.biblioteca_autismo.controller;


import com.biblioteca_autismo.model.Cuentos;
import com.biblioteca_autismo.response.CuentosResponseRest;
import com.biblioteca_autismo.service.ICuentosService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/v1")
public class CuentosController {

    @Autowired
    private ICuentosService service;

    @PostMapping("/cuento")
    public ResponseEntity<CuentosResponseRest> insertarCuento(@RequestBody Cuentos cuentos) {
        ResponseEntity<CuentosResponseRest> response = service.insertarCuento(cuentos);
        return response;
    }

    @GetMapping("/cuento")
    public ResponseEntity<CuentosResponseRest> getCuentos() {
        ResponseEntity<CuentosResponseRest> response = service.ObtenerCuentos();
        return response;
    }

    @PutMapping("/cuento/{id}")
    public ResponseEntity<CuentosResponseRest> editarCuento(@RequestBody Cuentos cuentos, @PathVariable Long id) {
        ResponseEntity<CuentosResponseRest> response = service.EditarCuento(cuentos, id);
        return response;
    }

    @DeleteMapping("/cuento/{id}")
    public ResponseEntity<CuentosResponseRest> eliminarCuento(@PathVariable Long id) {
        ResponseEntity<CuentosResponseRest> response = service.EliminarCuento(id);
        return response;
    }
}
