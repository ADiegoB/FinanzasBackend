package com.example.finanzas.models.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class MonedaDTO {
    private Long id_moneda;
    private String nombre_moneda;
    private String simbolo;

    public MonedaDTO(){}
}
