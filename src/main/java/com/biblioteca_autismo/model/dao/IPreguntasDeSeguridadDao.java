package com.biblioteca_autismo.model.dao;

import com.biblioteca_autismo.model.PreguntasDeSeguridad;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface IPreguntasDeSeguridadDao extends CrudRepository<PreguntasDeSeguridad, Long> {

    Optional<PreguntasDeSeguridad> findByUsuarioId(Long UsuarioId);

    Optional<PreguntasDeSeguridad> findByUsuarioIdAndPreguntaId(Long usuarioId, Long preguntaId);

}
