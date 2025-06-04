package com.biblioteca_autismo.model.dao;

import com.biblioteca_autismo.model.Cuentos;
import org.springframework.data.repository.CrudRepository;

public interface ICuentosDao  extends CrudRepository <Cuentos, Long> {
}
