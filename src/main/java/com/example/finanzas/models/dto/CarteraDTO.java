package com.example.finanzas.models.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;

@Getter @Setter
public class CarteraDTO {
    private Long id_cartera;
    private String nombre_cartera;
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate fecha_descuento;
    private Long id_usuario;  // Relación con Usuario
    private Long id_tasa;     // Relación con Tasa
    private Long id_moneda;   // Relación con Moneda

    public CarteraDTO(){}
}
