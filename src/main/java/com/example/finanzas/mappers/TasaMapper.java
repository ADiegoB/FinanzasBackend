package com.example.finanzas.mappers;

import com.example.finanzas.models.dao.Tasa;
import com.example.finanzas.models.dto.TasaDTO;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TasaMapper {
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    public TasaMapper(ModelMapper modelMapper){
        this.modelMapper=modelMapper;
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    }

    public TasaDTO convertirToDTO(Tasa tasa){ return modelMapper.map(tasa, TasaDTO.class);}
    public Tasa convertirToEntity (TasaDTO tasaDTO){ return modelMapper.map(tasaDTO, Tasa.class);}
}
