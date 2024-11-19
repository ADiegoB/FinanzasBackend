package com.example.finanzas.Repository;

import com.example.finanzas.models.dao.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    @Query("SELECT u FROM Usuario u WHERE u.nombre_usuario = :nombre_usuario")
    Optional<Usuario> findByUsername(@Param("nombre_usuario") String nombre_usuario);



}
