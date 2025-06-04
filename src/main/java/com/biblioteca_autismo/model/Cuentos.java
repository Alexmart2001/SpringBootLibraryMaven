package com.biblioteca_autismo.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "cuentos")
public class Cuentos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 500)
    private String titulo;

    @Column(nullable = false, length = 500)
    private String contenido;
}
