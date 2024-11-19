package com.example.finanzas.Services.Interfaces;

import com.example.finanzas.models.dao.Factura;
import com.example.finanzas.models.dto.FacturaDTO;
import com.example.finanzas.models.dto.GastoDTO;

import java.util.List;

public interface IFactura {

    List<FacturaDTO> getAllFacturas();
    FacturaDTO getFactura(Long id);
    void guardarFactura(FacturaDTO facturaDTO);
    void modificarFactura(FacturaDTO facturaDTO, Long id);
    void eliminarFactura(Long id);
    Factura agregarGastoAFactura(Long facturaId, GastoDTO nuevoGastoDTO);
    void cambiarEstadoFactura(Long id, boolean nuevoEstado);
    List<FacturaDTO> obtenerFacturasPorCarteraId(Long carteraid);
    void actualizarGasto(GastoDTO gastoDTO, Long id);
    void eliminarGasto(Long gastoId);
}
