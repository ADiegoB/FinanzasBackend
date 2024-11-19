package com.example.finanzas.mappers;

import com.example.finanzas.models.dao.Factura;
import com.example.finanzas.models.dao.Pago;
import com.example.finanzas.models.dto.PagoDTO;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Component;

@Component
public class PagoMapper {
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    public PagoMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        modelMapper.addMappings(new PropertyMap<Pago, PagoDTO>() {
            @Override
            protected void configure() {
                map().setId_factura(source.getFactura().getId_factura());
            }
        });
        modelMapper.addMappings(new PropertyMap<PagoDTO, Pago>() {
            @Override
            protected void configure() {
                using(context -> {
                    PagoDTO dto = (PagoDTO) context.getSource();
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

    public PagoDTO convertirToDTO(Pago pago) {
        return modelMapper.map(pago, PagoDTO.class);
    }

    public Pago convertirToEntity(PagoDTO pagoDTO){
        return modelMapper.map(pagoDTO, Pago.class);
    }
}
