package com.example.finanzas.models.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;

@Getter @Setter
public class FacturaDTO {
    private Long id_factura;
    private String nombre_factura;
    private Double valor_nominal;
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate fecha_emision;
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate fecha_vencimiento;
    private boolean estado_factura;  // Se guarda como String
    private Long id_cartera;  // Relación con Cartera

    private Double tasa_efectiva;  // Tasa efectiva calculada
    private Double tasa_descontada; // Tasa descontada calculada
    private Double descuento; // Descuento calculado
    private Double valor_neto; // Valor neto calculado
    private Double valor_recibido; // Valor recibido calculado
    private Double valor_entregado; // Valor entregado calculado
    public FacturaDTO(){}

}
