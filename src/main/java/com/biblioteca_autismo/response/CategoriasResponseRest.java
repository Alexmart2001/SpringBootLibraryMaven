package com.biblioteca_autismo.response;

import com.biblioteca_autismo.model.Categoria;
import lombok.Data;

@Data
public class CategoriasResponseRest extends ResponseRest{

    private CategoriasResponse categoria = new CategoriasResponse();
}
