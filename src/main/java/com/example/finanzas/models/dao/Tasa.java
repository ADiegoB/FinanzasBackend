package com.example.finanzas.models.dao;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "TASA")
public class Tasa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_TASA")
    private Long id_tasa;

    @Column(name = "VALOR")
    private Double valor;

    @Column(name = "TIPO_TASA")
    private String tipo_tasa; // EFECTIVA O NOMINAL

    @OneToMany(mappedBy = "tasa", cascade = CascadeType.ALL)
    private List<Cartera> carteras;
    public Tasa(){}

}
