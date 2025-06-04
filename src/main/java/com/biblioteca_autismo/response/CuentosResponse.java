package com.biblioteca_autismo.response;

import com.biblioteca_autismo.model.Cuentos;
import lombok.Data;

import java.util.List;

@Data
public class CuentosResponse {

    private List<Cuentos> cuentos;
}
