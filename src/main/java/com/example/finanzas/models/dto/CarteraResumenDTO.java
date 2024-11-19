package com.example.finanzas.models.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CarteraResumenDTO {
    private Long carteraId;
    private Double totalDescuento;
    private Double totalValorNeto;
    private Double totalValorRecibido;
    private Double totalValorEntregado;
    private Long totalDias; // Suma de los valores N de cada factura
    private Double tcea; // Tasa de Costo Efectivo Anual
}
