package com.example.finanzas.models.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;

@Getter @Setter
public class PagoDTO {
    private Long id_pago;
    private double monto_pago;
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate fecha_pago;
    private Long id_factura;  // Relación con Factura

    public PagoDTO(){}
}
