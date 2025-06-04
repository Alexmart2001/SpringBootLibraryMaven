package com.biblioteca_autismo.service;

import com.biblioteca_autismo.Constants;
import com.biblioteca_autismo.controller.NecesitasAyudaController;
import com.biblioteca_autismo.model.NecesitasAyuda;
import com.biblioteca_autismo.model.dao.INecesitasAyudaDao;
import com.biblioteca_autismo.response.NecesitasAyudaResponseRest;
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
public class NecesitasAyudaServiceImpl implements INecesitasAyudaService {

    private static final Logger logger = LoggerFactory.getLogger(NecesitasAyudaController.class);

    @Autowired
    private INecesitasAyudaDao necesitasAyudaDao;

    @Override
    @Transactional
    public ResponseEntity<NecesitasAyudaResponseRest> insertarRequest(NecesitasAyuda necesitasAyuda) {
        logger.info(Constants.LOG_START_PROCESS);
        NecesitasAyudaResponseRest responseRest = new NecesitasAyudaResponseRest();
        List<NecesitasAyuda> listaInsercion = new ArrayList<>();

        try {
            NecesitasAyuda insertarComentario = necesitasAyudaDao.save(necesitasAyuda);
            if (insertarComentario != null) {
                listaInsercion.add(insertarComentario);
                responseRest.getNecesitasAyudaResponse().setNecesitasAyudas(listaInsercion);
            } else {
                logger.info(Constants.ERROR_INSERT_DATE);
                responseRest.setMetadata(Constants.ERROR_INSERT_PROCESS, "-1", Constants.RETURN_FAILED);
                return new ResponseEntity<>(responseRest, HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            responseRest.setMetadata(Constants.ERROR_INSERT_DATE, "-1", Constants.RETURN_FAILED);
            logger.info(Constants.INSERT_FAILED);
            e.getStackTrace();
            return new ResponseEntity<>(responseRest, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        responseRest.setMetadata("Respuesta ok", "00", Constants.OK_INSERTION);
        return new ResponseEntity<>(responseRest, HttpStatus.OK);
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<NecesitasAyudaResponseRest> obtenerRequest() {
        logger.info("Consultando lista de comentarios");
        NecesitasAyudaResponseRest response = new NecesitasAyudaResponseRest();

        try {
            List<NecesitasAyuda> comentario = (List<NecesitasAyuda>) necesitasAyudaDao.findAll();
            response.getNecesitasAyudaResponse().setNecesitasAyudas(comentario);
            response.setMetadata("Respuesta ok", "00", "Lista de comentarios");
        } catch (Exception e) {
            response.setMetadata(Constants.GET_DATA_ERROR, "-1", Constants.RETURN_FAILED_RESPONSE);
            logger.info("No se pudo realizar la consulta de los comentarios", e.getMessage());
            e.getStackTrace(); // se encargan de mostrar la traza de error
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.setMetadata("Respuesta ok", "00", "Lista de libros");
        return new ResponseEntity<>(response, HttpStatus.OK); // retorna 200
    }

    @Override
    @Transactional
    public ResponseEntity<NecesitasAyudaResponseRest> EliminarRequest(Long id) {
        logger.info(Constants.DELETE_ERROR);
        NecesitasAyudaResponseRest response = new NecesitasAyudaResponseRest();

        try {
            Optional<NecesitasAyuda> comentario = necesitasAyudaDao.findById(id);
            if (comentario.isPresent()) {
                necesitasAyudaDao.deleteById(id);
                response.setMetadata("Respuesta ok", "00", "Se ha eliminado correctamente el comentario");
            } else {
                logger.info("No se pudo realizar la eliminacion del dato");
                response.setMetadata("Eliminacion fallida", "-1", "El registro no existe en la tabla");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            response.setMetadata("Ocurrio un error durante la eliminacion del comentario", "-1", "Respuesta fallida");
            logger.info("No se pudo realizar la eliminacion de dato");
            e.getStackTrace();
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.setMetadata("Respuesta ok", "00", "Eliminacion exitosa");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
