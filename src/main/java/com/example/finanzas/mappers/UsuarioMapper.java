package com.example.finanzas.mappers;

import com.example.finanzas.Repository.UsuarioRepository;
import com.example.finanzas.models.dao.Usuario;
import com.example.finanzas.models.dto.UsuarioDTO;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.convention.MatchingStrategies;
import org.modelmapper.spi.MatchingStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UsuarioMapper {
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    public UsuarioMapper(ModelMapper modelMapper){
        this.modelMapper = modelMapper;
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

    }

    public UsuarioDTO convertirToDTO(Usuario usuario){
        return modelMapper.map(usuario,UsuarioDTO.class);
    }

    public Usuario convertirToEntity(UsuarioDTO usuarioDTO){
        Usuario usuario = modelMapper.map(usuarioDTO, Usuario.class);
        usuario.setClave(passwordEncoder.encode(usuarioDTO.getClave()));
        return usuario;

    }
}
