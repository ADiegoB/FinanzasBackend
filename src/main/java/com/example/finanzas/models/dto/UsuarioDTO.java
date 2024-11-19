package com.example.finanzas.models.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UsuarioDTO {
    private Long id_usuario;
    private String nombre_usuario;
    private String clave;

    public UsuarioDTO(){}
}
