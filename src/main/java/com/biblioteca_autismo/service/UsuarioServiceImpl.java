package com.biblioteca_autismo.service;

import com.biblioteca_autismo.Constants;
import com.biblioteca_autismo.controller.UsuarioController;
import com.biblioteca_autismo.model.Usuario;
import com.biblioteca_autismo.model.dao.IUsuarioDao;
import com.biblioteca_autismo.response.UsuarioResponseRest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioServiceImpl implements IUsuarioService {

    private static final Logger logger = LoggerFactory.getLogger(UsuarioController.class);

    @Autowired
    private IUsuarioDao usuarioDao;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Override
    @Transactional
    public ResponseEntity<UsuarioResponseRest> insertarUsuario(Usuario usuario) {
        logger.info(Constants.LOG_START_PROCESS);
        UsuarioResponseRest response = new UsuarioResponseRest();
        List<Usuario> listaInsercion = new ArrayList<>();

        try {
            String hashedPassword = encoder.encode(usuario.getPassword());
            usuario.setPassword(hashedPassword);

            Usuario insertarUsuario = usuarioDao.save(usuario);
            if (insertarUsuario != null) {
                listaInsercion.add(insertarUsuario);
                response.getUsuarioResponse().setUsuarios(listaInsercion);
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
    public ResponseEntity<UsuarioResponseRest> findByCorreo(String email) {
        logger.info("Inicio proceso de extraccion de usuario");
        UsuarioResponseRest response = new UsuarioResponseRest();
        List<Usuario> lista = new ArrayList<>();
        try {
            Optional<Usuario> usuario = usuarioDao.findByEmail(email);
            if (usuario.isPresent()) {
                lista.add(usuario.get());
                response.getUsuarioResponse().setUsuarios(lista);
                response.setMetadata("Respuesta ok", "00", "Usuario encontrado");
            } else {
                logger.info("No se pudo realizar la consulta del usuario");
                response.setMetadata(Constants.FAILED_FIND_OBJECT, "-1", Constants.RETURN_FAILED_RESPONSE);
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }

        } catch (Exception e) {
            response.setMetadata(Constants.GET_DATA_ERROR, "-1", Constants.RETURN_FAILED_RESPONSE);
            logger.info(Constants.ERROR_DATE);
            e.getStackTrace();
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.setMetadata("Respuesta ok", "00", "usuario encontrado exitosamente");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<UsuarioResponseRest> obtenerUsuario() {
        logger.info("Consultando lista de usuarios");
        UsuarioResponseRest response = new UsuarioResponseRest();

        try {
            List<Usuario> usuarios = (List<Usuario>) usuarioDao.findAll();
            response.getUsuarioResponse().setUsuarios(usuarios);
            response.setMetadata("Respuesta ok", "00", "Lista de usuarios");
        } catch (Exception e) {
            response.setMetadata(Constants.GET_DATA_ERROR, "-1", Constants.RETURN_FAILED_RESPONSE);
            logger.error("No se pudo realizar la consulta del usuario", e);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<UsuarioResponseRest> BuscarUsuario(Long id) {
        logger.info("Consultando usuario con id = " + id);
        UsuarioResponseRest response = new UsuarioResponseRest();
        List<Usuario> list = new ArrayList<>();

        try {
            Optional<Usuario> usuario = usuarioDao.findById(id);
            if (usuario.isPresent()) {
                list.add(usuario.get());
                response.getUsuarioResponse().setUsuarios(list);
                response.setMetadata("Respuesta ok", "00", "Usuario encontrado");
            } else {
                logger.info("No se pudo realizar la consulta del usuario");
                response.setMetadata(Constants.FAILED_FIND_OBJECT, "-1", Constants.RETURN_FAILED_RESPONSE);
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            response.setMetadata(Constants.GET_DATA_ERROR, "-1", Constants.RETURN_FAILED_RESPONSE);
            logger.error(Constants.ERROR_DATE, e);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    @Transactional
    public ResponseEntity<UsuarioResponseRest> actualizarUsuario(Usuario usuario, Long id) {
        logger.info(Constants.START_MODIFY_PROCESS);
        UsuarioResponseRest response = new UsuarioResponseRest();
        List<Usuario> listaUpdate = new ArrayList<>();

        try {
            Optional<Usuario> usuarioUpdate = usuarioDao.findById(id);
            if (usuarioUpdate.isPresent()) {
                Usuario usuarioExistente = usuarioUpdate.get();

                usuarioExistente.setNombre(usuario.getNombre());
                usuarioExistente.setDispositivo(usuario.getDispositivo());
                usuarioExistente.setEmail(usuario.getEmail());
                usuarioExistente.setMetodoRegistro(usuario.getMetodoRegistro());
                usuarioExistente.setRol(usuario.getRol());

                if (usuario.getPassword() != null && !usuario.getPassword().isBlank()) {
                    usuarioExistente.setPassword(encoder.encode(usuario.getPassword()));
                }

                Usuario updateUsuario = usuarioDao.save(usuarioExistente);

                if (updateUsuario != null) {
                    listaUpdate.add(updateUsuario);
                    response.getUsuarioResponse().setUsuarios(listaUpdate);
                    response.setMetadata("Respuesta ok", "00", "Usuario actualizado exitosamente");
                } else {
                    logger.error("No se pudo realizar la modificacion de dato");
                    response.setMetadata("Modificación fallida", "-1", "No se pudo realizar la actualización del registro");
                    return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
                }
            } else {
                response.setMetadata("Modificación no exitosa", "-1", "El usuario no existe");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            logger.error("Error durante la actualización de usuario", e);
            response.setMetadata("Error interno", "-1", "Respuesta fallida");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    @Transactional
    public ResponseEntity<UsuarioResponseRest> DeleteUser(Long id) {
        logger.info(Constants.DELETE_ERROR);
        UsuarioResponseRest response = new UsuarioResponseRest();

        try {
            Optional<Usuario> usuario = usuarioDao.findById(id);
            if (usuario.isPresent()) {
                usuarioDao.deleteById(id);
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

    @Override
    public ResponseEntity<UsuarioResponseRest> validarUsuario(String email, String password) {
        UsuarioResponseRest response = new UsuarioResponseRest();

        try {
            Optional<Usuario> usuarioOpt = usuarioDao.findByEmail(email);

            if (usuarioOpt.isPresent()) {
                Usuario usuario = usuarioOpt.get();

                if (encoder.matches(password, usuario.getPassword())) {
                    response.getUsuarioResponse().setUsuarios(List.of(usuario));
                    return ResponseEntity.ok(response);
                } else {
                    response.setMetadata("Error", "Usuario o contraseña incorrectos", "-1");
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
                }
            } else {
                response.setMetadata("Error", "Usuario o contraseña incorrectos", "-1");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
            }

        } catch (Exception e) {
            logger.error("Error validando usuario", e);
            response.setMetadata("Error", "Error interno", "99");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
