package com.biblioteca_autismo.controller;

import com.biblioteca_autismo.model.Usuario;
import com.biblioteca_autismo.response.UsuarioResponseRest;
import com.biblioteca_autismo.service.IUsuarioService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@Slf4j
@RestController
@RequestMapping("/v1")
public class UsuarioController {

    @Autowired
    private IUsuarioService service;

    @PostMapping("/Usuario")
    public ResponseEntity<UsuarioResponseRest> AddNewUser(@RequestBody Usuario usuario) {
        ResponseEntity<UsuarioResponseRest> response = service.insertarUsuario(usuario);
        return response;
    }

    @PostMapping("/Usuario/validar")
    public ResponseEntity<UsuarioResponseRest> validarUsuario(@RequestBody Map<String, String> credenciales) {
        String email = credenciales.get("email");
        String password = credenciales.get("password");
        ResponseEntity<UsuarioResponseRest> response = service.validarUsuario(email, password);
        return response;
    }


    @GetMapping("/Usuario")
    public ResponseEntity<UsuarioResponseRest> GetUsuario() {
        ResponseEntity<UsuarioResponseRest> response = service.obtenerUsuario();
        return response;
    }

    @PutMapping("/Usuario/{id}")
    public ResponseEntity<UsuarioResponseRest> UpdateUsuario(@RequestBody Usuario usuario, @PathVariable Long id) {
        ResponseEntity<UsuarioResponseRest> response = service.actualizarUsuario(usuario, id);
        return response;
    }

    @DeleteMapping("/Usuario/{id}")
    public ResponseEntity<UsuarioResponseRest> DeleteUsuario(@PathVariable Long id) {
        ResponseEntity<UsuarioResponseRest> response = service.DeleteUser(id);
        return response;
    }

    @GetMapping("/Usuario/{id}")
    public ResponseEntity<UsuarioResponseRest> GetUsuarioById(@PathVariable Long id) {
        ResponseEntity<UsuarioResponseRest> response = service.BuscarUsuario(id);
        return response;
    }

    @GetMapping("/Usuario/correo/{correo}")
    public ResponseEntity<UsuarioResponseRest> GetCorreo(@PathVariable String correo) {
        ResponseEntity<UsuarioResponseRest> response = service.findByCorreo(correo);
        return response;
    }
}
