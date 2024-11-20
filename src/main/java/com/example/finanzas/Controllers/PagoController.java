package com.example.finanzas.Controllers;

import com.example.finanzas.Services.Interfaces.IPago;


import com.example.finanzas.models.dto.PagoDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pagos")
@CrossOrigin(origins = "https://finanzastf.onrender.com", allowedHeaders = "Authorization")
public class PagoController {
    @Autowired
    private IPago services;

    @GetMapping("/get")
    public ResponseEntity<List<PagoDTO>> getAll() {
        return ResponseEntity.ok(services.getAllPagos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PagoDTO> getPago(@PathVariable Long id) {
        PagoDTO pagoDTO = services.getPago(id);
        if (pagoDTO != null) {
            return ResponseEntity.ok(pagoDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/register")
    public ResponseEntity<Void> agregar(@RequestBody PagoDTO pagoDTO) {
        services.guardarPago(pagoDTO);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> modificar(@RequestBody PagoDTO pagoDTO, @PathVariable Long id) {
        services.modificarPago(pagoDTO, id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Void> eliminar (@PathVariable Long id){
        services.eliminarPago(id);
        return ResponseEntity.ok().build();
    }
}
