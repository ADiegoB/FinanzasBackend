package com.example.finanzas.Controllers;

import com.example.finanzas.Services.Interfaces.IFactura;
import com.example.finanzas.models.dao.Factura;
import com.example.finanzas.models.dto.FacturaDTO;

import com.example.finanzas.models.dto.GastoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/facturas")
@CrossOrigin(origins = "https://appweb-finanzas.vercel.app", allowedHeaders = "Authorization")
public class FacturaController {

    @Autowired
    private IFactura services;
    @GetMapping("/obtener")
    public ResponseEntity<List<FacturaDTO>> getAll() {
        return ResponseEntity.ok(services.getAllFacturas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<FacturaDTO> getUsuario(@PathVariable Long id) {
        FacturaDTO facturaDTO = services.getFactura(id);
        if (facturaDTO != null) {
            return ResponseEntity.ok(facturaDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/register")
    public ResponseEntity<Void> agregar(@RequestBody FacturaDTO facturaDTO) {
        services.guardarFactura(facturaDTO);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/modificar/{id}")
    public ResponseEntity<Void> modificar(@RequestBody FacturaDTO facturaDTO, @PathVariable Long id) {
        services.modificarFactura(facturaDTO, id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Void> eliminar (@PathVariable Long id){
        services.eliminarFactura(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{facturaId}/gastos")
    public ResponseEntity<Factura> agregarGastoAFactura(@PathVariable Long facturaId, @RequestBody GastoDTO gastoDTO) {
        try {
            Factura facturaActualizada = services.agregarGastoAFactura(facturaId, gastoDTO);
            return ResponseEntity.ok(facturaActualizada);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PutMapping("/{id}/estado")
    public ResponseEntity<String> cambiarEstadoFactura(@PathVariable Long id, @RequestBody Map<String, Boolean> request) {
        boolean nuevoEstado = request.get("estado");
        services.cambiarEstadoFactura(id, nuevoEstado);
        return ResponseEntity.ok("Estado de la factura actualizado correctamente");
    }


    @GetMapping("/cartera/{carteraId}")
    public List<FacturaDTO> obtenerFacturasPorCarteraId(@PathVariable Long carteraId) {
        return services.obtenerFacturasPorCarteraId(carteraId);
    }

}
