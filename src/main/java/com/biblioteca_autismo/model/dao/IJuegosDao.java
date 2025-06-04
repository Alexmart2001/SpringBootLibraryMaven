package com.biblioteca_autismo.model.dao;

import com.biblioteca_autismo.model.Juegos;
import org.springframework.data.repository.CrudRepository;

public interface IJuegosDao extends CrudRepository<Juegos, Long> {
}
