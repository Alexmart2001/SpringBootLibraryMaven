package com.biblioteca_autismo.model.dao;

import com.biblioteca_autismo.model.Categoria;
import org.springframework.data.repository.CrudRepository;

public interface ICategoriasDao extends CrudRepository<Categoria, Long> {
}
