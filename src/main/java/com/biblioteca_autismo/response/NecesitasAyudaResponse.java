package com.biblioteca_autismo.response;

import com.biblioteca_autismo.model.NecesitasAyuda;
import lombok.Data;

import java.util.List;

@Data
public class NecesitasAyudaResponse {

    private List<NecesitasAyuda> necesitasAyudas;

}
