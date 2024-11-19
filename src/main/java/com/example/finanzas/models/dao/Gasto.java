package com.example.finanzas.models.dao;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.autoconfigure.gson.GsonAutoConfiguration;
import org.springframework.cglib.reflect.FastClass;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "GASTO")
public class Gasto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_GASTO")
    private Long id_gasto;

    @Column(name = "MONTO_GASTO")
    private Double monto_gasto;

    @Column(name = "TIPO_GASTO")
    private boolean tipo_gasto; //1: final - 0: comienzo

    @ManyToOne
    @JoinColumn(name = "ID_FACTURA", nullable = false)
    private Factura factura;

    public Gasto(){}


}
