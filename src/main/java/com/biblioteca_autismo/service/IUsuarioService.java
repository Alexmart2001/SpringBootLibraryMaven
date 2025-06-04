package com.biblioteca_autismo.service;

import com.biblioteca_autismo.model.Usuario;
import com.biblioteca_autismo.response.UsuarioResponseRest;
import org.springframework.http.ResponseEntity;

public interface IUsuarioService {

    public ResponseEntity<UsuarioResponseRest> insertarUsuario(Usuario usuario);

    public ResponseEntity<UsuarioResponseRest> findByCorreo(String email);

    public ResponseEntity<UsuarioResponseRest> obtenerUsuario();

    public ResponseEntity<UsuarioResponseRest> BuscarUsuario(Long id);

    public ResponseEntity<UsuarioResponseRest> actualizarUsuario(Usuario usuario, Long id);

    public ResponseEntity<UsuarioResponseRest> DeleteUser(Long id);

    public ResponseEntity<UsuarioResponseRest> validarUsuario(String email, String password);
}
