package com.example.finanzas.models.dao;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "CARTERA")
public class Cartera {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_CARTERA")
    private Long id_cartera;

    @Column(name = "NOMBRE_CARTERA")
    private String nombre_cartera;


    @Column(name = "FECHA_DESCUENTO")
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate fecha_descuento;

    @ManyToOne
    @JoinColumn(name = "ID_USUARIO", nullable = false)
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "ID_TASA", nullable = false)
    private Tasa tasa;

    @ManyToOne
    @JoinColumn(name = "ID_MONEDA", nullable = false)
    private Moneda moneda;

    @OneToMany(mappedBy = "cartera", cascade = CascadeType.ALL)
    private List<Factura> facturas;

    public Cartera(){}
}
