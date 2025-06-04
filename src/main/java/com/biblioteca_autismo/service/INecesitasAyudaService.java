package com.biblioteca_autismo.service;

import com.biblioteca_autismo.model.NecesitasAyuda;
import com.biblioteca_autismo.response.NecesitasAyudaResponseRest;
import org.springframework.http.ResponseEntity;

public interface INecesitasAyudaService {

    public ResponseEntity<NecesitasAyudaResponseRest> insertarRequest (NecesitasAyuda necesitasAyuda);

    public ResponseEntity<NecesitasAyudaResponseRest> obtenerRequest ();

    public ResponseEntity<NecesitasAyudaResponseRest> EliminarRequest (Long id);

}
