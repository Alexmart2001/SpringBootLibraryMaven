package com.biblioteca_autismo.response;

import com.biblioteca_autismo.model.Emocion;
import lombok.Data;

import java.util.List;

@Data
public class EmocionesResponse {

    private List<Emocion> emociones;

}
