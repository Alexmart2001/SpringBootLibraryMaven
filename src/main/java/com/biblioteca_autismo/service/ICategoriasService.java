package com.biblioteca_autismo.service;


import com.biblioteca_autismo.model.Categoria;
import com.biblioteca_autismo.response.CategoriasResponseRest;
import org.springframework.http.ResponseEntity;

public interface ICategoriasService {

    public ResponseEntity<CategoriasResponseRest> insertarCategoria(Categoria categoria);

    public ResponseEntity<CategoriasResponseRest> obtenerCategoria(Long id);

    public ResponseEntity<CategoriasResponseRest> obtenerCategorias();

    public ResponseEntity<CategoriasResponseRest> actualizarCategoria(Categoria categoria, Long id);

    public ResponseEntity<CategoriasResponseRest> eliminarCategoria(Long id);
}
