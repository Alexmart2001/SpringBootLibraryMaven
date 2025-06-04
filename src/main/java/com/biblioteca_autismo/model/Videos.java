package com.biblioteca_autismo.model;


import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "videos")
public class Videos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "url")
    private String url;
}
