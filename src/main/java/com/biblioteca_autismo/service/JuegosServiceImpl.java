package com.biblioteca_autismo.service;

import com.biblioteca_autismo.Constants;
import com.biblioteca_autismo.controller.UsuarioController;
import com.biblioteca_autismo.model.Juegos;
import com.biblioteca_autismo.model.dao.IJuegosDao;
import com.biblioteca_autismo.response.JuegosResponseRest;
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
public class JuegosServiceImpl implements IJuegosService {

    @Autowired
    private IJuegosDao juegosDao;

    private static final Logger logger = LoggerFactory.getLogger(UsuarioController.class);

    @Override
    @Transactional
    public ResponseEntity<JuegosResponseRest> insertarJuego(Juegos juegos) {
        logger.info(Constants.LOG_START_PROCESS);
        JuegosResponseRest response = new JuegosResponseRest();
        List<Juegos> listaJuegos = new ArrayList<>();

        try {
            Juegos insertarJuegos = juegosDao.save(juegos);
            if (insertarJuegos != null) {
                listaJuegos.add(insertarJuegos);
                response.getJuegosResponse().setJuegos(listaJuegos);
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
        response.setMetadata("Respuesta ok", "00", Constants.OK_INSERTION);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<JuegosResponseRest> ObtenerJuegos() {
        logger.info("Consultando lista de juegos");
        JuegosResponseRest response = new JuegosResponseRest();

        try {
            List<Juegos> juegos = (List<Juegos>) juegosDao.findAll();
            response.getJuegosResponse().setJuegos(juegos);
            response.setMetadata("Respuesta ok", "00", "Lista de juegos");
        } catch (Exception e) {
            response.setMetadata(Constants.GET_DATA_ERROR, "-1", Constants.RETURN_FAILED_RESPONSE);
            logger.info("No se pudo realizar la consulta de los juegos", e.getMessage());
            e.getStackTrace();
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.setMetadata("Respuesta ok", "00", "Lista de juegos");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    @Transactional
    public ResponseEntity<JuegosResponseRest> EliminarJuego(Long id) {
        logger.info(Constants.DELETE_ERROR);
        JuegosResponseRest response = new JuegosResponseRest();

        try {
            Optional<Juegos> juegos = juegosDao.findById(id);
            if (juegos.isPresent()) {
                juegosDao.deleteById(id);
                response.setMetadata("Respuesta ok", "00", "Se ha eliminado el registro correctamente");
            } else {
                logger.info("No se pudo realizar la eliminacion del dato");
                response.setMetadata("Eliminacion fallida", "-1", "El registro no existe en la tabla");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            response.setMetadata("Ocurrio un error durante la eliminacion del juego", "-1", "Respuesta fallida");
            logger.info("No se pudo realizar la eliminacion de dato");
            e.getStackTrace();
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.setMetadata("Respuesta ok", "00", "Eliminacion exitosa");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
