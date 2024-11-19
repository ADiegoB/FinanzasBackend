package com.example.finanzas.Services.Interfaces;

import com.example.finanzas.models.dao.Moneda;
import com.example.finanzas.models.dto.CarteraDTO;
import com.example.finanzas.models.dto.CarteraResumenDTO;

import java.util.List;

public interface ICartera {

    List<CarteraDTO> getAllCarteras();
    CarteraDTO getCartera(Long id);
    void guardarCartera(CarteraDTO carteraDTO);
    void modificarCartera(CarteraDTO carteraDTO, Long id);
    void eliminarCartera(Long id);
    CarteraResumenDTO calcularTotalesPorCartera(Long carteraId);

}
