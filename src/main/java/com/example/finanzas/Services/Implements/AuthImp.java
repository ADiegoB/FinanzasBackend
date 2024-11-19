package com.example.finanzas.Services.Implements;

import com.example.finanzas.Repository.UsuarioRepository;
import com.example.finanzas.Services.Interfaces.IAuth;
import com.example.finanzas.jwt.JwtService;
import com.example.finanzas.models.dao.Usuario;
import com.example.finanzas.models.dto.AuthResponse;
import com.example.finanzas.models.dto.CredentialsDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthImp implements IAuth {
    private final UsuarioRepository repository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Override
    public AuthResponse login(CredentialsDTO credentialsDTO) {
        // Autentica al usuario con nombre de usuario y contraseña
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(credentialsDTO.getNombre(), credentialsDTO.getClave())
        );

        // Busca el usuario en la base de datos por su nombre de usuario
        Usuario usuario = repository.findByUsername(credentialsDTO.getNombre())
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

        // Genera el token JWT para el usuario autenticado
        String token = jwtService.getToken((UserDetails) usuario);

        // Devuelve la respuesta de autenticación sin incluir rol
        return AuthResponse.builder()
                .id_usuario(usuario.getId_usuario())
                .nombre_usuario(usuario.getNombre_usuario())
                .token(token)
                .build();
    }

}

