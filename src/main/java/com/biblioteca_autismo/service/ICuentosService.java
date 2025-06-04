package com.biblioteca_autismo.service;

import com.biblioteca_autismo.model.Cuentos;
import com.biblioteca_autismo.response.CuentosResponseRest;
import org.springframework.http.ResponseEntity;

public interface ICuentosService {

    public ResponseEntity<CuentosResponseRest> insertarCuento (Cuentos cuentos);

    public ResponseEntity<CuentosResponseRest> ObtenerCuentos ();

    public ResponseEntity<CuentosResponseRest> EditarCuento (Cuentos cuentos, Long id);

    public ResponseEntity<CuentosResponseRest> EliminarCuento (Long id);

}
