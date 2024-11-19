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
@Table(name = "FACTURA")
public class Factura {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_FACTURA")
    private Long id_factura;

    @Column(name = "NUMERO_FACTURA")
    private String nombre_factura;

    @Column(name = "VALOR_NOMINAL")
    private Double valor_nominal;

    @Column(name = "FECHA_EMISION")
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate fecha_emision;

    @Column(name = "FECHA_VENCIMIENTO")
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate fecha_vencimiento;


    @Column(name = "ESTADO_FACTURA")
    private boolean estado_factura;//1 es no pagado y 0 es pagado

    @Column(name = "TASA_EFECTIVA")
    private Double tasa_efectiva;

    @Column(name = "TASA_DESCONTADA")
    private Double tasa_descontada;

    @Column(name = "DESCUENTO")
    private Double descuento; // Descuento calculado

    @Column(name = "VALOR_NETO")
    private Double valor_neto; // Valor neto calculado

    @Column(name = "VALOR_RECIBIDO")
    private Double valor_recibido; // Valor recibido calculado

    @Column(name = "VALOR_ENTREGADO")
    private Double valor_entregado; // Valor entregado calculado

    @ManyToOne
    @JoinColumn(name = "CARTERA_ID", nullable = false)
    private Cartera cartera;

    @OneToMany(mappedBy = "factura", cascade = CascadeType.ALL)
    private List<Gasto> gastos;

    @OneToMany(mappedBy = "factura", cascade = CascadeType.ALL)
    private List<Pago> pagos;

    public Factura(){}

}
