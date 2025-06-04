package com.biblioteca_autismo.service;

import com.biblioteca_autismo.Constants;
import com.biblioteca_autismo.controller.UsuarioController;
import com.biblioteca_autismo.model.Cuentos;
import com.biblioteca_autismo.model.dao.ICuentosDao;
import com.biblioteca_autismo.response.CuentosResponseRest;
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
public class CuentosServiceImpl implements ICuentosService {

    private static final Logger logger = LoggerFactory.getLogger(UsuarioController.class);

    @Autowired
    private ICuentosDao cuentosDao;

    @Override
    @Transactional
    public ResponseEntity<CuentosResponseRest> insertarCuento(Cuentos cuentos) {
        logger.info(Constants.LOG_START_PROCESS);
        CuentosResponseRest response = new CuentosResponseRest();
        List<Cuentos> listaInsercion = new ArrayList<>();

        try {
            Cuentos insertarCuento = cuentosDao.save(cuentos);
            if (insertarCuento != null) {
                listaInsercion.add(insertarCuento);
                response.getCuentosResponse().setCuentos(listaInsercion);

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
    public ResponseEntity<CuentosResponseRest> ObtenerCuentos() {
        logger.info("Consultando lista de cuentos");
        CuentosResponseRest response = new CuentosResponseRest();

        try {
            List<Cuentos> cuentos = (List<Cuentos>) cuentosDao.findAll();
            response.getCuentosResponse().setCuentos(cuentos);
            response.setMetadata("Respuesta ok", "00", "Lista de cuentos");
        } catch (Exception e) {
            response.setMetadata(Constants.GET_DATA_ERROR, "-1", Constants.RETURN_FAILED_RESPONSE);
            logger.error("No se pudo realizar la consulta de los cuentos", e);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<CuentosResponseRest> EditarCuento(Cuentos cuentos, Long id) {
        logger.info(Constants.START_MODIFY_PROCESS);
        CuentosResponseRest response = new CuentosResponseRest();
        List<Cuentos> listaUpdate = new ArrayList<>();

        try {
            Optional<Cuentos> cuentosUpdate = cuentosDao.findById(id);
            if (cuentosUpdate.isPresent()) {
                Cuentos cuentoExistente = cuentosUpdate.get();

                cuentoExistente.setContenido(cuentos.getContenido());
                cuentoExistente.setTitulo(cuentos.getTitulo());

                Cuentos updateCuento = cuentosDao.save(cuentoExistente);

                if (updateCuento != null) {
                    listaUpdate.add(updateCuento);
                    response.getCuentosResponse().setCuentos(listaUpdate);
                    response.setMetadata("Respuesta ok", "00", "cuento actualizado exitosamente");
                } else {
                    logger.error("No se pudo realizar la modificacion de dato");
                    response.setMetadata("Modificación fallida", "-1", "No se pudo realizar la actualización del registro");
                    return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
                }
            } else {
                response.setMetadata("Modificación no exitosa", "-1", "El cuento no existe");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            logger.error("Error durante la actualización del cuento", e);
            response.setMetadata("Error interno", "-1", "Respuesta fallida");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<CuentosResponseRest> EliminarCuento(Long id) {
        logger.info(Constants.DELETE_ERROR);
        CuentosResponseRest response = new CuentosResponseRest();

        try {
            Optional<Cuentos> cuentos = cuentosDao.findById(id);
            if (cuentos.isPresent()) {
                cuentosDao.deleteById(id);
                response.setMetadata("Eliminacion exitosa", "00", "Se ha eliminado el registro correctamente");
            } else {
                logger.info("No se pudo realizar la eliminacion de dato");
                response.setMetadata("Eliminacion fallida", "-1", "El registro a eliminar no existe en la tabla");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            response.setMetadata("Ocurrio un error durante la eliminacion del cuento", "-1", "Respuesta fallida");
            logger.error("No se pudo realizar la eliminacion de dato", e);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
