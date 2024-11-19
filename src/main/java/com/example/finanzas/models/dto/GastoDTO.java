package com.example.finanzas.models.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class GastoDTO {
    private Long id_gasto;
    private Double monto_gasto;
    private boolean tipo_gasto;
    private Long id_factura;  // Relación con Factura

    public GastoDTO(){}
}
