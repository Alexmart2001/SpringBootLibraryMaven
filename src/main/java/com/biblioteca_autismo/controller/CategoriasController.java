package com.biblioteca_autismo.controller;


import com.biblioteca_autismo.model.Categoria;
import com.biblioteca_autismo.response.CategoriasResponseRest;
import com.biblioteca_autismo.service.ICategoriasService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/v1")
public class CategoriasController {

    @Autowired
    private ICategoriasService service;

    @PostMapping("/Categoria")
    public ResponseEntity<CategoriasResponseRest> AddCategoria(@RequestBody Categoria categoria) {
        ResponseEntity<CategoriasResponseRest> response = service.insertarCategoria(categoria);
        return response;
    }

    @GetMapping("/Categoria")
    public ResponseEntity<CategoriasResponseRest> getAllCategorias() {
        ResponseEntity<CategoriasResponseRest> response = service.obtenerCategorias();
        return response;
    }

    @GetMapping("/Categoria/{id}")
    public ResponseEntity<CategoriasResponseRest> getLibroById(@PathVariable Long id) {
        ResponseEntity<CategoriasResponseRest> response = service.obtenerCategoria(id);
        return response;
    }

    @PutMapping("/Categoria/{id}")
    public ResponseEntity<CategoriasResponseRest> UpdateCategoria(@RequestBody Categoria categoria, @PathVariable Long id) {
        ResponseEntity<CategoriasResponseRest> response = service.actualizarCategoria(categoria, id);
        return response;
    }

    @DeleteMapping("/Categoria/{id}")
    public ResponseEntity<CategoriasResponseRest> DeleteCategoria(@PathVariable Long id) {
        ResponseEntity<CategoriasResponseRest> response = service.eliminarCategoria(id);
        return response;
    }

}
