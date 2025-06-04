package com.biblioteca_autismo.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "emociones")
public class Emocion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String descripcion;

    @Column(name = "tipo_emoji")
    private String tipoEmoji;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;
}
