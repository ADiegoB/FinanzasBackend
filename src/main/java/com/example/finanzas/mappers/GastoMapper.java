package com.example.finanzas.mappers;

import com.example.finanzas.models.dao.Factura;
import com.example.finanzas.models.dao.Gasto;

import com.example.finanzas.models.dto.GastoDTO;

import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GastoMapper {
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    public GastoMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        modelMapper.addMappings(new PropertyMap<Gasto, GastoDTO>() {
            @Override
            protected void configure() {
                map().setId_factura(source.getFactura().getId_factura());
            }
        });
        modelMapper.addMappings(new PropertyMap<GastoDTO, Gasto>() {
            @Override
            protected void configure() {
                using(context -> {
                    GastoDTO dto = (GastoDTO) context.getSource();
                    Long idFactura = dto.getId_factura();
                    if (idFactura != null) {
                        Factura factura = new Factura();
                        factura.setId_factura(idFactura);
                        return factura;
                    } else {
                        return null;
                    }
                }).map(source, destination.getFactura());
            }
        });
    }

    public GastoDTO convertirToDTO(Gasto gasto) {
        return modelMapper.map(gasto, GastoDTO.class);
    }

    public Gasto convertirToEntity(GastoDTO gastoDTO){
        return modelMapper.map(gastoDTO, Gasto.class);
    }
}
