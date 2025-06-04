package com.biblioteca_autismo.model.dao;

import com.biblioteca_autismo.model.Libro;
import org.springframework.data.repository.CrudRepository;

public interface ILibroDao  extends CrudRepository<Libro, Long> {
}
