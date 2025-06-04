package com.biblioteca_autismo.service;

import com.biblioteca_autismo.Constants;
import com.biblioteca_autismo.controller.UsuarioController;
import com.biblioteca_autismo.model.MusicaRelajacion;
import com.biblioteca_autismo.model.dao.IMusicaRelajacionDao;
import com.biblioteca_autismo.response.MusicaRelajacionResponseRest;
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
public class MusicaRelajacionServiceImpl implements IMusicaRelajacionService {

    private static final Logger logger = LoggerFactory.getLogger(UsuarioController.class);

    @Autowired
    private IMusicaRelajacionDao musicaDao;


    @Override
    @Transactional
    public ResponseEntity<MusicaRelajacionResponseRest> insertarCancion(MusicaRelajacion musicaRelajacion) {
        logger.info(Constants.LOG_START_PROCESS);
        MusicaRelajacionResponseRest response = new MusicaRelajacionResponseRest();
        List<MusicaRelajacion> listaInsercion = new ArrayList<>();

        try {
            MusicaRelajacion insertarCancion = musicaDao.save(musicaRelajacion);
            if (insertarCancion != null) {
                listaInsercion.add(insertarCancion);
                response.getMusicaResponse().setRelajacion(listaInsercion);
            } else {
                logger.error(Constants.ERROR_INSERT_DATE);
                response.setMetadata(Constants.ERROR_INSERT_PROCESS, "-1", Constants.RETURN_FAILED);
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            response.setMetadata(Constants.ERROR_INSERT_DATE, "-1", Constants.RETURN_FAILED);
            logger.error(Constants.INSERT_FAILED, e);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.setMetadata("Respuesta ok", "00", "Insercion exitosa");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<MusicaRelajacionResponseRest> obtenerCancion() {
        logger.info("Consultando lista de canciones");
        MusicaRelajacionResponseRest response = new MusicaRelajacionResponseRest();

        try {
            List<MusicaRelajacion> canciones = (List<MusicaRelajacion>) musicaDao.findAll();
            response.getMusicaResponse().setRelajacion(canciones);
            response.setMetadata("Respuesta ok", "00", "Lista de canciones");
        } catch (Exception e) {
            response.setMetadata(Constants.GET_DATA_ERROR, "-1", Constants.RETURN_FAILED_RESPONSE);
            logger.error("No se pudo realizar la consulta de las canciones", e);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    @Transactional
    public ResponseEntity<MusicaRelajacionResponseRest> EditarCancion(MusicaRelajacion musicaRelajacion, Long id) {
        logger.info(Constants.START_MODIFY_PROCESS);
        MusicaRelajacionResponseRest response = new MusicaRelajacionResponseRest();
        List<MusicaRelajacion> listaUpdate = new ArrayList<>();

        try {
            Optional<MusicaRelajacion> cancion = musicaDao.findById(id);
            if (cancion.isPresent()) {
                MusicaRelajacion cancionExistente = cancion.get();

                cancionExistente.setNombre(musicaRelajacion.getNombre());
                cancionExistente.setSrc(musicaRelajacion.getSrc());

                MusicaRelajacion updateCancion = musicaDao.save(cancionExistente);

                if (updateCancion != null) {
                    listaUpdate.add(updateCancion);
                    response.getMusicaResponse().setRelajacion(listaUpdate);
                    response.setMetadata("Respuesta ok", "00", "Cancion actualizada exitosamente");
                } else {
                    logger.error("No se pudo realizar la modificacion de dato");
                    response.setMetadata("Modificación fallida", "-1", "No se pudo realizar la actualización del registro");
                    return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
                }
            } else {
                response.setMetadata("Modificación no exitosa", "-1", "la cancion no existe");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            logger.error("Error durante la actualización de la cancion", e);
            response.setMetadata("Error interno", "-1", "Respuesta fallida");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<MusicaRelajacionResponseRest> borrarCancion(Long id) {
        logger.info(Constants.DELETE_ERROR);
        MusicaRelajacionResponseRest response = new MusicaRelajacionResponseRest();

        try {
            Optional<MusicaRelajacion> cancion = musicaDao.findById(id);
            if (cancion.isPresent()) {
                musicaDao.deleteById(id);
                response.setMetadata("Eliminacion exitosa", "00", "Se ha eliminado el registro correctamente");
            } else {
                logger.info("No se pudo realizar la eliminacion de dato");
                response.setMetadata("Eliminacion fallida", "-1", "El registro a eliminar no existe en la tabla");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            response.setMetadata("Ocurrio un error durante la eliminacion del usuario", "-1", "Respuesta fallida");
            logger.error("No se pudo realizar la eliminacion de dato", e);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
