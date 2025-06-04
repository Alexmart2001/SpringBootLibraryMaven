package com.biblioteca_autismo.response;

import com.biblioteca_autismo.model.PreguntasDeSeguridad;
import lombok.Data;

import java.util.List;

@Data
public class PreguntasDeSeguridadResponse {

    private List<PreguntasDeSeguridad> preguntasDeSeguridad;
}
