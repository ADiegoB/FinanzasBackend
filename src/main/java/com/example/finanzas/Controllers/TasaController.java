package com.example.finanzas.Controllers;

import com.example.finanzas.Services.Interfaces.ITasa;
import com.example.finanzas.models.dto.TasaDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/tasas")
@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "Authorization")
public class TasaController {

    @Autowired
    private ITasa services;


    @GetMapping("/get")
    public ResponseEntity<List<TasaDTO>> getAll() {
        return ResponseEntity.ok(services.getAllTasas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TasaDTO> getTasa(@PathVariable Long id) {
        TasaDTO tasaDTO = services.getTasa(id);
        if (tasaDTO != null) {
            return ResponseEntity.ok(tasaDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/register")
    public ResponseEntity<Void> agregar(@RequestBody TasaDTO tasaDTO) {
        services.guardartasa(tasaDTO);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> modificar(@RequestBody TasaDTO tasaDTO, @PathVariable Long id) {
        services.modificarTasa(tasaDTO, id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Void> eliminar (@PathVariable Long id){
        services.eliminarTasa(id);
        return ResponseEntity.ok().build();
    }


}
