package com.example.finanzas.models.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class TasaDTO {
    private Long id_tasa;
    private Double valor;
    private String tipo_tasa;  // Nominal o Efectiva

    public TasaDTO(){}
}
