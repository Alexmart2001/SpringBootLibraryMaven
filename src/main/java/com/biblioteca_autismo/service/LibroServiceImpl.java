package com.biblioteca_autismo.service;

import com.biblioteca_autismo.Constants;
import com.biblioteca_autismo.controller.UsuarioController;
import com.biblioteca_autismo.model.Categoria;
import com.biblioteca_autismo.model.Libro;
import com.biblioteca_autismo.model.dao.ICategoriasDao;
import com.biblioteca_autismo.model.dao.ILibroDao;
import com.biblioteca_autismo.response.LibroResponseRest;
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
public class LibroServiceImpl implements ILibroService {

    private static final Logger logger = LoggerFactory.getLogger(UsuarioController.class);

    @Autowired
    private ILibroDao libroDao;

    @Autowired
    private ICategoriasDao categoriaDao;

    @Override
    @Transactional
    public ResponseEntity<LibroResponseRest> insertarLibro(Libro libro) {
        logger.info(Constants.LOG_START_PROCESS);
        LibroResponseRest response = new LibroResponseRest();
        List<Libro> listaInsercion = new ArrayList<>();

        try {
            // Validar y asignar la categoría si viene el id en el libro
            if (libro.getCategoria() != null && libro.getCategoria().getId() != null) {
                Optional<Categoria> categoriaOpt = categoriaDao.findById(libro.getCategoria().getId());
                if (categoriaOpt.isEmpty()) {
                    response.setMetadata("Categoría no encontrada", "-1", Constants.RETURN_FAILED);
                    return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
                }
                // Asignar la categoría gestionada por JPA para que se persista bien la relación
                libro.setCategoria(categoriaOpt.get());
            } else {
                // Si la categoría es obligatoria podrías lanzar error aquí
                libro.setCategoria(null);
            }

            Libro insertarLibro = libroDao.save(libro);
            if (insertarLibro != null) {
                listaInsercion.add(insertarLibro);
                response.getLibroResponse().setLibro(listaInsercion);
            } else {
                logger.info(Constants.ERROR_INSERT_DATE);
                response.setMetadata(Constants.ERROR_INSERT_PROCESS, "-1", Constants.RETURN_FAILED);
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            response.setMetadata(Constants.ERROR_INSERT_DATE, "-1", Constants.RETURN_FAILED);
            logger.error(Constants.INSERT_FAILED, e);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.setMetadata("Respuesta ok", "00", Constants.OK_INSERTION);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }



    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<LibroResponseRest> ObtenerLibro(Long id) {
        logger.info(Constants.LOG_START_PROCESS + "con id: " + id);
        LibroResponseRest response = new LibroResponseRest();
        List<Libro> lista = new ArrayList<>();

        try {
            Optional<Libro> libro = libroDao.findById(id);
            if (libro.isPresent()) {
                lista.add(libro.get());
                response.getLibroResponse().setLibro(lista);
                response.setMetadata("Respuesta ok", "00", "Libro encontrado");
            } else {
                logger.info("No se pudo realizar la consulta del libro");
                response.setMetadata(Constants.FAILED_FIND_OBJECT, "-1", Constants.RETURN_FAILED_RESPONSE);
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            response.setMetadata(Constants.GET_DATA_ERROR, "-1", Constants.RETURN_FAILED_RESPONSE);
            logger.info(Constants.ERROR_DATE);
            e.getStackTrace();
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.setMetadata("Respuesta ok", "00", "Libro encontrado exitosamente");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<LibroResponseRest> ObtenerLibros() {
        logger.info("Consultando lista de libros");
        LibroResponseRest response = new LibroResponseRest();

        try {
            List<Libro> libros = (List<Libro>) libroDao.findAll();
            response.getLibroResponse().setLibro(libros);
            response.setMetadata("Respuesta ok", "00", "Lista de libros");
        } catch (Exception e) {
            response.setMetadata(Constants.GET_DATA_ERROR, "-1", Constants.RETURN_FAILED_RESPONSE);
            logger.info("No se pudo realizar la consulta del libro", e.getMessage());
            e.getStackTrace(); // se encargan de mostrar la traza de error
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.setMetadata("Respuesta ok", "00", "Lista de libros");
        return new ResponseEntity<>(response, HttpStatus.OK); // retorna 200
    }

    @Override
    @Transactional
    public ResponseEntity<LibroResponseRest> ActualizarLibro(Libro libro, Long id) {
        logger.info(Constants.START_MODIFY_PROCESS);
        LibroResponseRest response = new LibroResponseRest();
        List<Libro> listaUpdate = new ArrayList<>();

        try {
            Optional<Libro> libroUpdate = libroDao.findById(id);
            if (libroUpdate.isPresent()) {
                libroUpdate.get().setTitulo(libro.getTitulo());
                libroUpdate.get().setDescripcion(libro.getDescripcion());
                libroUpdate.get().setImagen(libro.getImagen());
                libroUpdate.get().setCategoria(libro.getCategoria());

                Libro updateLibro = libroDao.save(libroUpdate.get());// realiza la modificacion en DB

                if (updateLibro != null) {
                    response.setMetadata("Respuesta ok", "00", "Libro actualizado exitosamente");
                    listaUpdate.add(updateLibro);
                    response.getLibroResponse().setLibro(listaUpdate);
                } else {
                    logger.info("No se pudo realizar la modificacion de dato");
                    response.setMetadata("Modificacion fallida", "-1", "No se pudo realizar la actualizacion del registro");
                    return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
                }
            } else {
                response.setMetadata("Modificacion no exitosa", "-1", "El libro en cuestion no existe en la tabla");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            response.setMetadata("Ocurrio un error durante la ejecucion del proceso de modificación", "-1", "Respuesta fallida");
            logger.info(Constants.START_MODIFY_PROCESS);
            e.getStackTrace();
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.setMetadata("Respuesta ok", "00", "Usuario actualizado exitosamente");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    @Transactional
    public ResponseEntity<LibroResponseRest> EliminarLibro(Long id) {
        logger.info(Constants.DELETE_ERROR);
        LibroResponseRest response = new LibroResponseRest();

        try {
            Optional<Libro> libro = libroDao.findById(id);
            if (libro.isPresent()) {
                libroDao.deleteById(id);
                response.setMetadata("Respuesta ok", "00", "Se ha eliminado el registro correctamente");
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
