package com.example.finanzas.models.dao;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "MONEDA")
public class Moneda {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_MONEDA")
    private Long id_moneda;

    @Column(name = "NOMBRE_MONEDA")
    private String nombre_moneda;

    @Column(name = "SIMBOLO_MONEDA")
    private String simbolo;

    @OneToMany(mappedBy = "moneda",cascade = CascadeType.ALL)
    private List<Cartera> carteras;

    public Moneda(){}
}
