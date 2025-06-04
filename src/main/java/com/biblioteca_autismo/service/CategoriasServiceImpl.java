package com.biblioteca_autismo.service;

import com.biblioteca_autismo.Constants;
import com.biblioteca_autismo.controller.UsuarioController;
import com.biblioteca_autismo.model.Categoria;
import com.biblioteca_autismo.model.dao.ICategoriasDao;
import com.biblioteca_autismo.response.CategoriasResponseRest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CategoriasServiceImpl implements ICategoriasService {

    private static final Logger logger = LoggerFactory.getLogger(UsuarioController.class);

    @Autowired
    private ICategoriasDao categoriaDao;

    @Override
    @Transactional
    public ResponseEntity<CategoriasResponseRest> insertarCategoria(Categoria categoria) {
        logger.info(Constants.LOG_START_PROCESS);
        CategoriasResponseRest response = new CategoriasResponseRest();
        List<Categoria> listaInsercion = new ArrayList<>();

        try {
            Categoria insertarCategoria = categoriaDao.save(categoria);
            if (insertarCategoria != null) {
                listaInsercion.add(insertarCategoria);
                response.getCategoria().setCategorias(listaInsercion);
            } else {
                logger.info(Constants.ERROR_INSERT_DATE);
                response.setMetadata(Constants.ERROR_INSERT_PROCESS, "-1", Constants.RETURN_FAILED);
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            response.setMetadata(Constants.ERROR_INSERT_DATE, "-1", Constants.RETURN_FAILED);
            logger.info(Constants.INSERT_FAILED);
            e.getStackTrace();
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.setMetadata("Respuesta ok", "00", Constants.RETURN_FAILED);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<CategoriasResponseRest> obtenerCategoria(Long id) {
        logger.info(Constants.LOG_START_PROCESS + "con id: " + id);
        CategoriasResponseRest response = new CategoriasResponseRest();
        List<Categoria> lista = new ArrayList<>();

        try {
            Optional<Categoria> categoria = categoriaDao.findById(id);
            if (categoria.isPresent()) {
                lista.add(categoria.get());
                response.getCategoria().setCategorias(lista);
                response.setMetadata("Respuesta ok", "00", "Categoria Encontrada");
            } else {
                logger.info("No se pudo realizar la consulta de la categoria");
                response.setMetadata(Constants.FAILED_FIND_OBJECT, "-1", Constants.RETURN_FAILED_RESPONSE);
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            response.setMetadata(Constants.GET_DATA_ERROR, "-1", Constants.RETURN_FAILED_RESPONSE);
            logger.info(Constants.ERROR_DATE);
            e.getStackTrace();
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.setMetadata("Respuesta ok", "00", "Categoria encontrada exitosamente");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<CategoriasResponseRest> obtenerCategorias() {
        logger.info("Consultando lista de categorias");
        CategoriasResponseRest response = new CategoriasResponseRest();

        try {
            List<Categoria> categorias = (List<Categoria>) categoriaDao.findAll();
            response.getCategoria().setCategorias(categorias);
            response.setMetadata("Respuesta ok", "00", "Lista de categorias");
        } catch (Exception e) {
            response.setMetadata(Constants.GET_DATA_ERROR, "-1", Constants.RETURN_FAILED_RESPONSE);
            logger.info("No se pudo realizar la consulta de la categoria", e.getMessage());
            e.getStackTrace();
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.setMetadata("Respuesta ok", "00", "Lista de categorias");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    @Transactional
    public ResponseEntity<CategoriasResponseRest> actualizarCategoria(Categoria categoria, Long id) {
        logger.info(Constants.START_MODIFY_PROCESS);
        CategoriasResponseRest response = new CategoriasResponseRest();
        List<Categoria> listaUpdate = new ArrayList<>();

        try {
            Optional<Categoria> categoriaUpdate = categoriaDao.findById(id);
            if (categoriaUpdate.isPresent()) {
                categoriaUpdate.get().setNombre(categoria.getNombre());
                categoriaUpdate.get().setLibros(categoria.getLibros());

                Categoria updateCategoria = categoriaDao.save(categoriaUpdate.get());

                if (updateCategoria != null) {
                    response.setMetadata("Respuesta ok", "00", "Categoria actualizada exitosamente");
                    listaUpdate.add(updateCategoria);
                    response.getCategoria().setCategorias(listaUpdate);
                } else {
                    logger.info("No se pudo realizar la modificacion de dato");
                    response.setMetadata("Modificacion fallida", "-1", "No se pudo realizar la actualizacion del registro");
                    return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
                }
            } else {
                response.setMetadata("Modificacion no exitosa", "-1", "La Categoria en cuestion no existe en la tabla");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            response.setMetadata("Ocurrio un error durante la ejecucion del proceso de modificación", "-1", "Respuesta fallida");
            logger.info(Constants.START_MODIFY_PROCESS);
            e.getStackTrace();
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.setMetadata("Respuesta ok", "00", "Categoria actualizado exitosamente");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    @Transactional
    public ResponseEntity<CategoriasResponseRest> eliminarCategoria(Long id) {
        logger.info(Constants.DELETE_ERROR);
        CategoriasResponseRest response = new CategoriasResponseRest();

        try {
            Optional<Categoria> categoria = categoriaDao.findById(id);
            if (categoria.isPresent()) {
                categoriaDao.deleteById(id);
                response.setMetadata("Respuesta ok", "00", "Categoria eliminada");
            } else {
                logger.info("No se pudo realizar la eliminacion del dato");
                response.setMetadata("Eliminacion fallida", "-1", "El registro no existe en la tabla");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            response.setMetadata("Ocurrio un error durante la eliminacion del libro", "-1", "Respuesta fallida");
            logger.info("No se pudo realizar la eliminacion de dato");
            e.getStackTrace();
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.setMetadata("Respuesta ok", "00", "Eliminacion exitosa");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
