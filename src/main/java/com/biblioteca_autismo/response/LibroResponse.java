package com.biblioteca_autismo.response;

import com.biblioteca_autismo.model.Libro;
import lombok.Data;
import java.util.List;

@Data
public class LibroResponse {

    private List<Libro> libro;
}
