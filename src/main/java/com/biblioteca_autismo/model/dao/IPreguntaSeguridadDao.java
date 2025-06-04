package com.biblioteca_autismo.model.dao;

import com.biblioteca_autismo.model.PreguntasSeguridad;
import org.springframework.data.repository.CrudRepository;

public interface IPreguntaSeguridadDao extends CrudRepository <PreguntasSeguridad, Long> {
}
