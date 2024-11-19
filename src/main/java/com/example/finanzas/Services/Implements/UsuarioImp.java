package com.example.finanzas.Services.Implements;

import com.example.finanzas.Repository.UsuarioRepository;
import com.example.finanzas.Services.Interfaces.IUsuario;
import com.example.finanzas.mappers.UsuarioMapper;
import com.example.finanzas.models.dao.Usuario;
import com.example.finanzas.models.dto.UsuarioDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class UsuarioImp implements IUsuario,UserDetailsService {
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private UsuarioMapper usuarioMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public List<UsuarioDTO> getAllUsuarios(){
        List<Usuario> usuarios = usuarioRepository.findAll();
        List<UsuarioDTO> usuarioDTOS = new ArrayList<>();

        for(Usuario usuario: usuarios){
            UsuarioDTO usuarioDTO = usuarioMapper.convertirToDTO(usuario);
            usuarioDTOS.add(usuarioDTO);
        }
        return usuarioDTOS;
    }

    @Override
    public UsuarioDTO getUsuario(Long id){
        Optional<Usuario> optionalUsuario = usuarioRepository.findById(id);
        if (optionalUsuario.isPresent()){
           Usuario usuarioB = optionalUsuario.get();
           return usuarioMapper.convertirToDTO(usuarioB);
        }
        else{
            throw new NoSuchElementException("No se encontró el Usuario con ID:"+ id);
        }
    }

    @Override
    public void guardarUsuario(UsuarioDTO usuarioDTO){
      Usuario usuario = usuarioMapper.convertirToEntity(usuarioDTO);
      usuarioRepository.save(usuario);
    }
    @Override
    public void modificarUsuario(UsuarioDTO usuarioDTO, Long id){
        Optional<Usuario> optionalUsuario = usuarioRepository.findById(id);
        if (optionalUsuario.isPresent()){
            Usuario usuario1 = optionalUsuario.get();
            usuario1.setNombre_usuario(usuarioDTO.getNombre_usuario());
            usuario1.setClave(passwordEncoder.encode(usuarioDTO.getClave()));
            usuarioRepository.save(usuario1);
        }
        else{
            throw new NoSuchElementException("No se encontró el usuario con ID: "+ id);
        }
    }

    @Override
    public void eliminarUsuario(Long id) {
        Optional<Usuario> optionalUsuario = usuarioRepository.findById(id);
        if(optionalUsuario.isPresent()){
            usuarioRepository.deleteById(id);
        }
        else{
            throw new NoSuchElementException("No se encontró el empleado con ID: " + id);
        }
    }

    @Override
    public UserDetails loadUserByUsername(String mail) throws UsernameNotFoundException {
        Usuario usuario =  usuarioRepository
                .findByUsername(mail)
                .orElseThrow(() -> new UsernameNotFoundException("El usuario con correo electronico" + mail + "no existe"));
        return usuario;
    }
}
