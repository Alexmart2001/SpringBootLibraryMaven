package com.biblioteca_autismo.model.dao;

import com.biblioteca_autismo.model.Emocion;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface IEmocionesDao  extends CrudRepository<Emocion, Long> {

    List<Emocion> findByUsuarioId(Long userId);
}
