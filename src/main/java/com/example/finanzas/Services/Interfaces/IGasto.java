package com.example.finanzas.Services.Interfaces;

import com.example.finanzas.models.dto.GastoDTO;

import java.util.List;

public interface IGasto {

    List<GastoDTO> getAllGastos();
    GastoDTO getGasto(Long id);
    void guardarGasto(GastoDTO gastoDTO);
    void modificarGasto(GastoDTO gastoDTO, Long id);
    void eliminarGasto(Long id);
    List<GastoDTO> obtenerGastosPorFacturaId(Long facturaId);
}
