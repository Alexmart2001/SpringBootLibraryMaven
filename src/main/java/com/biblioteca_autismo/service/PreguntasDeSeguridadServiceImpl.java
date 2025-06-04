package com.biblioteca_autismo.service;

import com.biblioteca_autismo.Constants;
import com.biblioteca_autismo.controller.UsuarioController;
import com.biblioteca_autismo.model.PreguntasDeSeguridad;
import com.biblioteca_autismo.model.PreguntasSeguridad;
import com.biblioteca_autismo.model.Usuario;
import com.biblioteca_autismo.model.dao.IPreguntaSeguridadDao;
import com.biblioteca_autismo.model.dao.IPreguntasDeSeguridadDao;
import com.biblioteca_autismo.model.dao.IUsuarioDao;
import com.biblioteca_autismo.response.PreguntasDeSeguridadResponseRest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PreguntasDeSeguridadServiceImpl implements IPreguntasDeSeguridadService {

    private static final Logger logger = LoggerFactory.getLogger(UsuarioController.class);

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    private IPreguntasDeSeguridadDao questionDao;

    @Autowired
    private IUsuarioDao usuarioDao;

    @Autowired
    private IPreguntaSeguridadDao preguntasSeguridadDao;


    @Override
    @Transactional
    public ResponseEntity<PreguntasDeSeguridadResponseRest> insertarQuestion(PreguntasDeSeguridad preguntasDeSeguridad) {
        PreguntasDeSeguridadResponseRest response = new PreguntasDeSeguridadResponseRest();
        List<PreguntasDeSeguridad> listaInsercion = new ArrayList<>();

        try {
            Optional<Usuario> usuarioOpt = usuarioDao.findById(preguntasDeSeguridad.getUsuario().getId());
            Optional<PreguntasSeguridad> preguntaOpt = preguntasSeguridadDao.findById(preguntasDeSeguridad.getPregunta().getId());

            if (usuarioOpt.isEmpty() || preguntaOpt.isEmpty()) {
                response.setMetadata("Usuario o pregunta no encontrada", "-1", Constants.RETURN_FAILED);
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }

            preguntasDeSeguridad.setUsuario(usuarioOpt.get());
            preguntasDeSeguridad.setPregunta(preguntaOpt.get());

            String respuestaOriginal = preguntasDeSeguridad.getRespuesta();

            String respuestaHasheada = passwordEncoder.encode(respuestaOriginal);

            preguntasDeSeguridad.setRespuesta(respuestaHasheada);

            PreguntasDeSeguridad insertarQuestion = questionDao.save(preguntasDeSeguridad);

            PreguntasDeSeguridad preguntaParaResponse = new PreguntasDeSeguridad();
            preguntaParaResponse.setId(insertarQuestion.getId());
            preguntaParaResponse.setUsuario(insertarQuestion.getUsuario());
            preguntaParaResponse.setPregunta(insertarQuestion.getPregunta());
            preguntaParaResponse.setRespuesta(null);  // no enviar la respuesta

            listaInsercion.add(preguntaParaResponse);
            response.getPreguntasDeSeguridad().setPreguntasDeSeguridad(listaInsercion);
            response.setMetadata("Respuesta ok", "00", Constants.OK_INSERTION);
            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (Exception e) {
            logger.error("Error al insertar pregunta", e);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            response.setMetadata(Constants.ERROR_INSERT_DATE, "-1", Constants.RETURN_FAILED);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<PreguntasDeSeguridadResponseRest> ObtenerPreguntasDeSeguridad(Long id) {
        PreguntasDeSeguridadResponseRest responseRest = new PreguntasDeSeguridadResponseRest();
        List<PreguntasDeSeguridad> lista = new ArrayList<>();

        try {
            Optional<PreguntasDeSeguridad> preguntas = questionDao.findById(id);
            if (preguntas.isPresent()) {
                PreguntasDeSeguridad pregunta = preguntas.get();
                pregunta.setRespuesta(null);

                lista.add(pregunta);
                responseRest.getPreguntasDeSeguridad().setPreguntasDeSeguridad(lista);
                responseRest.setMetadata("Respuesta ok", "00", "Pregunta Encontrada");
            } else {
                responseRest.setMetadata(Constants.FAILED_FIND_OBJECT, "-1", Constants.RETURN_FAILED_RESPONSE);
                return new ResponseEntity<>(responseRest, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            responseRest.setMetadata(Constants.GET_DATA_ERROR, "-1", Constants.RETURN_FAILED_RESPONSE);
            logger.error("Error al obtener pregunta", e);
            return new ResponseEntity<>(responseRest, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(responseRest, HttpStatus.OK);
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<PreguntasDeSeguridadResponseRest> ObtenerPreguntas() {
        logger.info("Consultando lista de preguntas");
        PreguntasDeSeguridadResponseRest response = new PreguntasDeSeguridadResponseRest();

        try {
            List<PreguntasDeSeguridad> lista = (List<PreguntasDeSeguridad>) questionDao.findAll();
            response.getPreguntasDeSeguridad().setPreguntasDeSeguridad(lista);
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
    public ResponseEntity<PreguntasDeSeguridadResponseRest> EliminarPregunta(Long id) {
        logger.info(Constants.DELETE_ERROR);
        PreguntasDeSeguridadResponseRest response = new PreguntasDeSeguridadResponseRest();

        try {
            Optional<PreguntasDeSeguridad> preguntas = questionDao.findById(id);
            if (preguntas.isPresent()) {
                questionDao.deleteById(id);
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

    @Override
    public ResponseEntity<PreguntasDeSeguridadResponseRest> BuscarPorUser(Long id) {
        PreguntasDeSeguridadResponseRest response = new PreguntasDeSeguridadResponseRest();
        Optional<PreguntasDeSeguridad> result = questionDao.findByUsuarioId(id);

        try {
            if (result.isPresent()) {
                PreguntasDeSeguridad pregunta = result.get();
                response.getPreguntasDeSeguridad().setPreguntasDeSeguridad(List.of(pregunta));
                response.setMetadata("Respuesta ok", "00", "Pregunta Encontrada");
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                response.setMetadata("Consulta sin resultados", "01", "No hay pregunta registrada para este usuario");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            response.setMetadata(Constants.GET_DATA_ERROR, "-1", Constants.RETURN_FAILED_RESPONSE);
            logger.error("Error al buscar pregunta por usuario", e);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    @Transactional
    public ResponseEntity<Boolean> validarRespuesta(Long usuarioId, Long idPregunta, String respuestaUsuario) {
        Optional<PreguntasDeSeguridad> preguntaOpt = questionDao.findByUsuarioIdAndPreguntaId(usuarioId, idPregunta);
        if (preguntaOpt.isEmpty()) {
            return new ResponseEntity<>(false, HttpStatus.NOT_FOUND);
        }

        PreguntasDeSeguridad pregunta = preguntaOpt.get();
        String respuestaHasheada = pregunta.getRespuesta();

        boolean matches = passwordEncoder.matches(respuestaUsuario, respuestaHasheada);

        return new ResponseEntity<>(matches, HttpStatus.OK);
    }
}
