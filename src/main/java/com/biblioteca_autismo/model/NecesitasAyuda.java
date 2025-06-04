package com.biblioteca_autismo.model;


import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "necesitas_ayuda")
public class NecesitasAyuda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @Column(length = 200)
    private String comentario;

    @Column(name = "numero_telefono", length = 200)
    private String numeroTelefono;

    @Column(name = "correo_electronico", length = 200)
    private String correoElectronico;
}
