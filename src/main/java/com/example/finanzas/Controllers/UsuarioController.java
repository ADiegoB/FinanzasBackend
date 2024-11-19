package com.example.finanzas.Controllers;

import com.example.finanzas.Services.Interfaces.IUsuario;
import com.example.finanzas.models.dto.UsuarioDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins ="http://localhost:4200")
@RestController
@RequestMapping("/api")
public class UsuarioController {
    @Autowired
    private IUsuario services;

    @GetMapping("/usuarios")
    public ResponseEntity<List<UsuarioDTO>> getAll() {
        return ResponseEntity.ok(services.getAllUsuarios());
    }

    @GetMapping("/usuarios/{id}")
    public ResponseEntity<UsuarioDTO> getUsuario(@PathVariable Long id) {
        UsuarioDTO usuarioDTO = services.getUsuario(id);
        if (usuarioDTO != null) {
            return ResponseEntity.ok(usuarioDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/usuarios/{id}")
    public ResponseEntity<Void> modificar(@RequestBody UsuarioDTO usuarioDTO, @PathVariable Long id) {
        services.modificarUsuario(usuarioDTO, id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/usuarios/eliminar/{id}")
    public ResponseEntity<Void> eliminar (@PathVariable Long id){
        services.eliminarUsuario(id);
        return ResponseEntity.ok().build();
    }

}
