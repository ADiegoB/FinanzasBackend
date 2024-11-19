package com.example.finanzas.models.dao;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Getter @Setter
@Entity
@Table(name ="USUARIO")
public class Usuario  implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_USUARIO")
    private Long id_usuario;

    @Column(name ="NOMBRE_USUARIO", length = 100)
    private String nombre_usuario;

    @Column(name ="clave", length = 100)
    private String clave;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
    private List<Cartera> carteras;

    public Usuario(){}


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(); // Retorna una lista vacía
    }
    @Override
    public String getPassword() {
        return clave;
    }

    @Override
    public String getUsername() {
        return nombre_usuario;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }


}