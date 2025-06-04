package com.biblioteca_autismo.service;

import com.biblioteca_autismo.Constants;
import com.biblioteca_autismo.controller.UsuarioController;
import com.biblioteca_autismo.model.PreguntasSeguridad;
import com.biblioteca_autismo.model.dao.IPreguntaSeguridadDao;
import com.biblioteca_autismo.response.PreguntaSeguridadResponseRest;
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
public class PreguntasSeguridadServiceImpl implements IPreguntasSeguridadService {

    private static final Logger logger = LoggerFactory.getLogger(UsuarioController.class);

    @Autowired
    private IPreguntaSeguridadDao preguntaDao;

    @Override
    @Transactional
    public ResponseEntity<PreguntaSeguridadResponseRest> insertarPregunta(PreguntasSeguridad preguntasSeguridad) {
        logger.info(Constants.LOG_START_PROCESS);

        PreguntaSeguridadResponseRest response = new PreguntaSeguridadResponseRest();
        List<PreguntasSeguridad> listaInsercion = new ArrayList<>();

        try {
            PreguntasSeguridad insertarPregunta = preguntaDao.save(preguntasSeguridad);
            if (insertarPregunta != null) {
                listaInsercion.add(insertarPregunta);
                response.getPreguntaResponse().setPreguntas(listaInsercion);
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
    public ResponseEntity<PreguntaSeguridadResponseRest> ObtenerPreguntas() {
        logger.info("Consultando lista de preguntas");
        PreguntaSeguridadResponseRest response = new PreguntaSeguridadResponseRest();

        try {
            List<PreguntasSeguridad> preguntas = (List<PreguntasSeguridad>) preguntaDao.findAll();
            response.getPreguntaResponse().setPreguntas(preguntas);
            response.setMetadata("Respuesta ok", "00", "Lista de preguntas");
        } catch (Exception e) {
            response.setMetadata(Constants.GET_DATA_ERROR, "-1", Constants.RETURN_FAILED_RESPONSE);
            logger.info("No se pudo realizar la consulta de las preguntas", e.getMessage());
            e.getStackTrace();
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.setMetadata("Respuesta ok", "00", "Lista de preguntas");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    @Transactional
    public ResponseEntity<PreguntaSeguridadResponseRest> EliminarPreguntas(Long id) {
        logger.info(Constants.DELETE_ERROR);
        PreguntaSeguridadResponseRest response = new PreguntaSeguridadResponseRest();

        try {
            Optional<PreguntasSeguridad> preguntas = preguntaDao.findById(id);
            if (preguntas.isPresent()) {
                preguntaDao.deleteById(id);
                response.setMetadata("Respuesta ok", "00", "Se ha eliminado el registro correctamente");
            } else {
                logger.info("No se pudo realizar la eliminacion del dato");
                response.setMetadata("Eliminacion fallida", "-1", "El registro no existe en la tabla");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            response.setMetadata("Ocurrio un error durante la eliminacion de la pregunta", "-1", "Respuesta fallida");
            logger.info("No se pudo realizar la eliminacion de dato");
            e.getStackTrace();
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.setMetadata("Respuesta ok", "00", "Eliminacion exitosa");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
