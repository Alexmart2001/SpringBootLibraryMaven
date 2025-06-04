package com.biblioteca_autismo.response;

import com.biblioteca_autismo.model.Categoria;
import lombok.Data;

import java.util.List;

@Data
public class CategoriasResponse {

    private List<Categoria> categorias;
}
