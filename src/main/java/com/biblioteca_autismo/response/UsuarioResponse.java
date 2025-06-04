package com.biblioteca_autismo.response;

import com.biblioteca_autismo.model.Usuario;
import lombok.Data;

import java.util.List;

@Data
public class UsuarioResponse {

    private List<Usuario> usuarios;
}
