package com.example.finanzas.mappers;

import com.example.finanzas.models.dao.Cartera;
import com.example.finanzas.models.dao.Factura;
import com.example.finanzas.models.dto.FacturaDTO;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FacturaMapper {
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    public FacturaMapper(ModelMapper modelMapper){
        this.modelMapper= modelMapper;
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        modelMapper.addMappings(new PropertyMap<Factura, FacturaDTO>() {
            @Override
            protected void configure(){
                map().setId_cartera(source.getCartera().getId_cartera());
            }
        });
        modelMapper.addMappings(new PropertyMap<FacturaDTO, Factura>() {
            @Override
            protected void configure(){
                using(context ->{
                    FacturaDTO dto = (FacturaDTO) context.getSource();
                    Long idCartera = dto.getId_cartera();
                    if(idCartera != null){
                        Cartera cartera = new Cartera();
                        cartera.setId_cartera(idCartera);
                        return cartera;
                    }else{
                        return null;
                    }
                }).map(source, destination.getCartera());
            }
        });
    }

    public FacturaDTO convertirToDTO(Factura factura){
        return modelMapper.map(factura, FacturaDTO.class);
    }

    public Factura convertirToEntity(FacturaDTO facturaDTO){
        return modelMapper.map(facturaDTO, Factura.class);
    }

}
