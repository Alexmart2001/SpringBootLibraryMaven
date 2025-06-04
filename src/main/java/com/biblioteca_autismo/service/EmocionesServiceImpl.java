package com.biblioteca_autismo.service;

import com.biblioteca_autismo.Constants;
import com.biblioteca_autismo.controller.UsuarioController;
import com.biblioteca_autismo.model.Emocion;
import com.biblioteca_autismo.model.dao.IEmocionesDao;
import com.biblioteca_autismo.response.EmocionesResponseRest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EmocionesServiceImpl implements IEmocionesService {

    private static final Logger logger = LoggerFactory.getLogger(UsuarioController.class);

    @Autowired
    private IEmocionesDao emocionDao;

    @Override
    public ResponseEntity<EmocionesResponseRest> insertarEmocion(Emocion emocion) {
        logger.info(Constants.LOG_START_PROCESS);
        EmocionesResponseRest response = new EmocionesResponseRest();
        List<Emocion> listaInsercion = new ArrayList<>();

        try {
            Emocion insertarEmocion = emocionDao.save(emocion);
            if (insertarEmocion != null) {
                listaInsercion.add(insertarEmocion);
                response.getEmocionesResponse().setEmociones(listaInsercion);
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
    public ResponseEntity<EmocionesResponseRest> ObtenerEmociones(Long userId) {
        logger.info("Consultando lista de emociones del usuario");
        EmocionesResponseRest response = new EmocionesResponseRest();

        try {
            List<Emocion> emociones = emocionDao.findByUsuarioId(userId);
            response.getEmocionesResponse().setEmociones(emociones);
            response.setMetadata("Respuesta ok", "00", "Lista de emociones del usuario");
        } catch (Exception e) {
            response.setMetadata(Constants.GET_DATA_ERROR, "-1", Constants.RETURN_FAILED_RESPONSE);
            logger.error("No se pudo realizar la consulta de las emociones del usuario", e);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<EmocionesResponseRest> EliminarEmocion(Long id) {
        logger.info(Constants.DELETE_ERROR);
        EmocionesResponseRest response = new EmocionesResponseRest();

        try {
            Optional<Emocion> emocion = emocionDao.findById(id);
            if (emocion.isPresent()) {
                emocionDao.deleteById(id);
                response.setMetadata("Eliminacion exitosa", "00", "Se ha eliminado el registro correctamente");
            } else {
                logger.info("No se pudo realizar la eliminacion de dato");
                response.setMetadata("Eliminacion fallida", "-1", "El registro a eliminar no existe en la tabla");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            response.setMetadata("Ocurrio un error durante la eliminacion de la emocion", "-1", "Respuesta fallida");
            logger.error("No se pudo realizar la eliminacion de dato", e);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
