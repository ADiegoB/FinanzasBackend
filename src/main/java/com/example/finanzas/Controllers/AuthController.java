package com.example.finanzas.Controllers;

import com.example.finanzas.Services.Interfaces.IAuth;
import com.example.finanzas.Services.Interfaces.IUsuario;
import com.example.finanzas.models.dto.AuthResponse;
import com.example.finanzas.models.dto.CredentialsDTO;
import com.example.finanzas.models.dto.UsuarioDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins ="http://localhost:4200")
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private IAuth services;
    @Autowired
    private IUsuario servicesU;


    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody CredentialsDTO credentialsDTO){
        return ResponseEntity.ok(services.login(credentialsDTO));
    }

    @PostMapping("/usuarios/register")
    public ResponseEntity<Void> agregar(@RequestBody UsuarioDTO usuarioDTO) {
        servicesU.guardarUsuario(usuarioDTO);
        return ResponseEntity.ok().build();
    }


}
