package com.example.finanzas.Services.Interfaces;

import com.example.finanzas.models.dto.TasaDTO;

import java.util.List;

public interface ITasa {

    List<TasaDTO> getAllTasas();
    TasaDTO getTasa(Long id);
    void guardartasa(TasaDTO tasaDTO);
    void modificarTasa(TasaDTO tasaDTO, Long id);
    void eliminarTasa(Long id);
}
