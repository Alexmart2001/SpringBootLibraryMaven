package com.biblioteca_autismo.response;

import com.biblioteca_autismo.model.Juegos;
import lombok.Data;

import java.util.List;

@Data
public class JuegosResponse{

    private List<Juegos> juegos;
}
