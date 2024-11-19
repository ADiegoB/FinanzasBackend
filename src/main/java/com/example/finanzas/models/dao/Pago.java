package com.example.finanzas.models.dao;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "PAGO")
public class Pago {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_PAGO")
    private Long id_pago;

    @Column(name = "MONTO_PAGO")
    private double monto_pago;

    @Column(name = "FECHA_PAGO")
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate fecha_pago;

    @ManyToOne
    @JoinColumn(name = "ID_FACTURA", nullable = false)
    private Factura factura;

    public Pago(){}
}
