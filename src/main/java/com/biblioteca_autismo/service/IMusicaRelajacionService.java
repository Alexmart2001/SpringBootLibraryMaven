package com.biblioteca_autismo.service;

import com.biblioteca_autismo.model.MusicaRelajacion;
import com.biblioteca_autismo.response.MusicaRelajacionResponseRest;
import org.springframework.http.ResponseEntity;

public interface IMusicaRelajacionService {

    public ResponseEntity<MusicaRelajacionResponseRest> insertarCancion (MusicaRelajacion musicaRelajacion);

    public ResponseEntity <MusicaRelajacionResponseRest> obtenerCancion ();

    public ResponseEntity <MusicaRelajacionResponseRest> EditarCancion (MusicaRelajacion musicaRelajacion, Long id);

    public ResponseEntity <MusicaRelajacionResponseRest> borrarCancion (Long id);
}
