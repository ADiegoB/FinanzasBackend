package com.example.finanzas.mappers;

import com.example.finanzas.models.dao.Cartera;
import com.example.finanzas.models.dao.Moneda;
import com.example.finanzas.models.dao.Tasa;
import com.example.finanzas.models.dao.Usuario;
import com.example.finanzas.models.dto.CarteraDTO;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CarteraMapper {
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    public CarteraMapper(ModelMapper modelMapper){
        this.modelMapper=modelMapper;
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        modelMapper.addMappings(new PropertyMap<Cartera, CarteraDTO>() {
            @Override
            protected void configure(){
            map().setId_moneda(source.getMoneda().getId_moneda());
            map().setId_tasa(source.getTasa().getId_tasa());
            map().setId_usuario(source.getUsuario().getId_usuario());
            }
        });
        modelMapper.addMappings(new PropertyMap<CarteraDTO, Cartera>() {
            @Override
            protected void configure(){
                using(context ->{
                    CarteraDTO dto = (CarteraDTO) context.getSource();
                    Long idMoneda = dto.getId_moneda();
                    if(idMoneda != null){
                        Moneda moneda = new Moneda();
                        moneda.setId_moneda(idMoneda);
                        return moneda;
                    }else{
                        return null;
                    }
                }).map(source,destination.getMoneda());

                using(context->{
                    CarteraDTO dto = (CarteraDTO) context.getSource();
                    Long idTasa = dto.getId_tasa();
                    if (idTasa !=null){
                        Tasa tasa = new Tasa();
                        tasa.setId_tasa(idTasa);
                        return tasa;
                    }else {
                        return null;
                    }
                }).map(source,destination.getTasa());

                using(context ->{
                    CarteraDTO dto = (CarteraDTO) context.getSource();
                    Long idUsuario = dto.getId_usuario();
                    if(idUsuario != null){
                        Usuario usuario = new Usuario();
                        usuario.setId_usuario(idUsuario);
                        return usuario;
                    }else {
                        return null;
                    }
                }).map(source, destination.getUsuario());
            }

        });
    }

    public CarteraDTO convertirToDTO(Cartera cartera){
        return modelMapper.map(cartera, CarteraDTO.class);
    }

    public Cartera convertirToEntity(CarteraDTO carteraDTO){
        return modelMapper.map(carteraDTO, Cartera.class);
    }
}
