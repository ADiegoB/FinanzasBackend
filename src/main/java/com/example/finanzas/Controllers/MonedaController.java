package com.example.finanzas.Controllers;

import com.example.finanzas.Services.Interfaces.IMoneda;
import com.example.finanzas.models.dao.Moneda;
import com.example.finanzas.models.dto.MonedaDTO;
import com.example.finanzas.models.dto.UsuarioDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/monedas")
@CrossOrigin(origins = "https://finanzastf.onrender.com", allowedHeaders = "Authorization")
public class MonedaController {

    @Autowired
    private IMoneda services;

    @GetMapping("/get")
    public ResponseEntity<List<MonedaDTO>> getAll() {
       return ResponseEntity.ok(services.getAllMonedas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MonedaDTO> getMoneda(@PathVariable Long id) {
        MonedaDTO monedaDTO = services.getMoneda(id);
        if (monedaDTO != null) {
            return ResponseEntity.ok(monedaDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/register")
    public ResponseEntity<Void> agregar(@RequestBody MonedaDTO monedaDTO) {
        services.guardarMoneda(monedaDTO);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> modificar(@RequestBody MonedaDTO monedaDTO, @PathVariable Long id) {
        services.modificarMoneda(monedaDTO, id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Void> eliminar (@PathVariable Long id){
        services.eliminarMoneda(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/simbolo/{carteraId}")
    public String obtenerSimbolo(@PathVariable Long carteraId) {
        return services.obtenerMonedaPorCarteraId(carteraId);
    }

}
