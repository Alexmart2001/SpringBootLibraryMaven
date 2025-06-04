package com.biblioteca_autismo.model.dao;

import com.biblioteca_autismo.model.Usuario;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface IUsuarioDao extends CrudRepository <Usuario, Long>{

    Optional<Usuario> findByEmail(String email);
}
