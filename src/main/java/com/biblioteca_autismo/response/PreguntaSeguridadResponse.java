package com.biblioteca_autismo.response;

import com.biblioteca_autismo.model.PreguntasSeguridad;
import lombok.Data;

import java.util.List;

@Data
public class PreguntaSeguridadResponse {

    private List<PreguntasSeguridad> preguntas;
}
