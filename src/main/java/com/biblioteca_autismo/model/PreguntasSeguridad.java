package com.biblioteca_autismo.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "preguntas_seguridad")
public class PreguntasSeguridad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 200)
    private String pregunta;
}