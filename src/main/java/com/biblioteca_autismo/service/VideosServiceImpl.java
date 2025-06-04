package com.biblioteca_autismo.service;

import com.biblioteca_autismo.Constants;
import com.biblioteca_autismo.controller.UsuarioController;
import com.biblioteca_autismo.model.Videos;
import com.biblioteca_autismo.model.dao.IVideosDao;
import com.biblioteca_autismo.response.VideosResponseRest;
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
public class VideosServiceImpl implements IVideosService {

    private static final Logger logger = LoggerFactory.getLogger(UsuarioController.class);

    @Autowired
    private IVideosDao videosDao;

    @Override
    @Transactional
    public ResponseEntity<VideosResponseRest> InsertarVideo(Videos videos) {
        logger.info(Constants.LOG_START_PROCESS);

        VideosResponseRest response = new VideosResponseRest();
        List<Videos> listaInsercion = new ArrayList<>();

        try {
            Videos insertarVideo = videosDao.save(videos);
            if (insertarVideo != null) {
                listaInsercion.add(insertarVideo);
                response.getVideosResponse().setVideos(listaInsercion);
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
    public ResponseEntity<VideosResponseRest> ObtenerVideos() {
        logger.info("Consultando lista de videos");
        VideosResponseRest response = new VideosResponseRest();

        try {
            List<Videos> videos = (List<Videos>) videosDao.findAll();
            response.getVideosResponse().setVideos(videos);
            response.setMetadata("Respuesta ok", "00", "Lista de videos");
        } catch (Exception e) {
            response.setMetadata(Constants.GET_DATA_ERROR, "-1", Constants.RETURN_FAILED_RESPONSE);
            logger.info("No se pudo realizar la consulta de los videos", e.getMessage());
            e.getStackTrace();
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.setMetadata("Respuesta ok", "00", "Lista de videos");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    @Transactional
    public ResponseEntity<VideosResponseRest> EliminarVideos(Long id) {
        logger.info(Constants.DELETE_ERROR);
        VideosResponseRest response = new VideosResponseRest();

        try {
            Optional<Videos> video = videosDao.findById(id);
            if (video.isPresent()) {
                videosDao.deleteById(id);
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
