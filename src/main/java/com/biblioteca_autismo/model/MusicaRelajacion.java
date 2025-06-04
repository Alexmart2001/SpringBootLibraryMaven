package com.biblioteca_autismo.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "musica_relajante")
public class MusicaRelajacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 200)
    private String nombre;

    @Column(nullable = false, length = 500)
    private String src;
}
