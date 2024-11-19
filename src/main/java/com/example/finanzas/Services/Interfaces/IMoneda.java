package com.example.finanzas.Services.Interfaces;

import com.example.finanzas.models.dao.Moneda;
import com.example.finanzas.models.dto.MonedaDTO;

import java.util.List;

public interface IMoneda {

    List<MonedaDTO> getAllMonedas();
    MonedaDTO getMoneda(Long id);
    void guardarMoneda(MonedaDTO monedaDTO);
    void modificarMoneda(MonedaDTO monedaDTO, Long id);
    void eliminarMoneda(Long id);
    String obtenerMonedaPorCarteraId(Long idCartera);
}
