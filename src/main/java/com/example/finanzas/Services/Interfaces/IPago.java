package com.example.finanzas.Services.Interfaces;

import com.example.finanzas.models.dto.PagoDTO;

import java.util.List;

public interface IPago {
    List<PagoDTO> getAllPagos();
    PagoDTO getPago(Long id);
    void guardarPago(PagoDTO pagoDTO);
    void modificarPago(PagoDTO pagoDTO, Long id);
    void eliminarPago(Long id);

}
