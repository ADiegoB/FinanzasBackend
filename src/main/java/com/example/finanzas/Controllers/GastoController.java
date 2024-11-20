package com.example.finanzas.Controllers;

import com.example.finanzas.Services.Interfaces.IFactura;
import com.example.finanzas.Services.Interfaces.IGasto;
import com.example.finanzas.models.dto.FacturaDTO;
import com.example.finanzas.models.dto.GastoDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;


@RestController
@RequestMapping("/api/gastos")
@CrossOrigin(origins = "https://appweb-finanzas.vercel.app", allowedHeaders = "Authorization")
public class GastoController {
    @Autowired
    private IGasto services;
    @Autowired
    private IFactura iFactura;

    @GetMapping("/get")
    public ResponseEntity<List<GastoDTO>> getAll() {
        return ResponseEntity.ok(services.getAllGastos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<GastoDTO> getGasto(@PathVariable Long id) {
        GastoDTO gastoDTO = services.getGasto(id);
        if (gastoDTO != null) {
            return ResponseEntity.ok(gastoDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/register")
    public ResponseEntity<Void> agregar(@RequestBody GastoDTO gastoDTO) {
        services.guardarGasto(gastoDTO);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> modificar(@RequestBody GastoDTO gastoDTO, @PathVariable Long id) {
        services.modificarGasto(gastoDTO, id);
        return ResponseEntity.ok().build();
    }


    @GetMapping("/factura/{facturaId}")
    public List<GastoDTO> obtenerGastosPorFacturaId(@PathVariable Long facturaId) {
        return services.obtenerGastosPorFacturaId(facturaId);
    }

    @PutMapping("/actualizar/{id}")
    public ResponseEntity<String> actualizarGasto(@PathVariable Long id, @RequestBody GastoDTO gastoDTO) {
        try {
            iFactura.actualizarGasto(gastoDTO, id);
            return ResponseEntity.ok().build();
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
    @DeleteMapping("/eliminar/{gastoId}")
    public ResponseEntity<Void> eliminarGasto(@PathVariable Long gastoId) {

            // Llamar al servicio para eliminar el gasto y recalcular la factura
            iFactura.eliminarGasto(gastoId);
            return ResponseEntity.ok().build();
    }

}
