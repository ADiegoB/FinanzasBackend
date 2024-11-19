package com.example.finanzas.mappers;

import com.example.finanzas.Repository.MonedaRepository;
import com.example.finanzas.models.dao.Moneda;
import com.example.finanzas.models.dto.MonedaDTO;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MonedaMapper {
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private MonedaRepository monedaRepository;

    @Autowired
    public MonedaMapper(ModelMapper modelMapper){
        this.modelMapper = modelMapper;
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    }

    public MonedaDTO convertirToDTO(Moneda moneda){
        return modelMapper.map(moneda, MonedaDTO.class);
    }

    public Moneda convertirToEntity(MonedaDTO monedaDTO){
       return modelMapper.map(monedaDTO, Moneda.class);

    }
}
