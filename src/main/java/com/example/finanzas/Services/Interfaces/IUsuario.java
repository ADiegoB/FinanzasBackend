package com.example.finanzas.Services.Interfaces;

import com.example.finanzas.models.dao.Factura;
import com.example.finanzas.models.dao.Usuario;
import com.example.finanzas.models.dto.GastoDTO;
import com.example.finanzas.models.dto.UsuarioDTO;

import java.util.List;

public interface IUsuario {
    List<UsuarioDTO> getAllUsuarios();
    UsuarioDTO getUsuario(Long id);
    void guardarUsuario(UsuarioDTO usuarioDTO);
    void modificarUsuario(UsuarioDTO usuarioDTO, Long id);
    void eliminarUsuario(Long id);

}
