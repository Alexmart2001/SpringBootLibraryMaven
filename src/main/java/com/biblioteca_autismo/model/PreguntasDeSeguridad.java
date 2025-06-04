package com.biblioteca_autismo.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "preguntas_de_seguridad")
public class PreguntasDeSeguridad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "pregunta_id")
    private PreguntasSeguridad pregunta;

    @Column(length = 200)
    private String respuesta;
}
