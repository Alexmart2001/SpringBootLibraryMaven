package com.biblioteca_autismo.service;

import com.biblioteca_autismo.model.Emocion;
import com.biblioteca_autismo.response.EmocionesResponseRest;
import org.springframework.http.ResponseEntity;

public interface IEmocionesService {

    public ResponseEntity<EmocionesResponseRest> insertarEmocion(Emocion emocion);

    public ResponseEntity<EmocionesResponseRest> ObtenerEmociones(Long userId);

    public ResponseEntity<EmocionesResponseRest> EliminarEmocion(Long id);

}
