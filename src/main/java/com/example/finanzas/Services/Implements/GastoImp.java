package com.example.finanzas.Services.Implements;

import com.example.finanzas.Repository.GastoRepository;
import com.example.finanzas.Services.Interfaces.IGasto;
import com.example.finanzas.mappers.GastoMapper;
import com.example.finanzas.models.dao.Factura;
import com.example.finanzas.models.dao.Gasto;
import com.example.finanzas.models.dao.Pago;
import com.example.finanzas.models.dto.GastoDTO;
import com.example.finanzas.models.dto.PagoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class GastoImp implements IGasto {
    @Autowired
    private GastoRepository repository;
    @Autowired
    private GastoMapper mapper;

    @Override
    public List<GastoDTO> getAllGastos(){
        List<Gasto> gastos =  repository.findAll();
        List<GastoDTO> gastoDTOS = new ArrayList<>();
        for(Gasto gasto: gastos){
            GastoDTO gastoDTO = mapper.convertirToDTO(gasto);
            gastoDTOS.add(gastoDTO);
        }
        return gastoDTOS;
    }
    @Override
    public GastoDTO getGasto(Long id){
        Optional<Gasto> optionalGasto = repository.findById(id);
        if(optionalGasto.isPresent()){
            Gasto gastoB = optionalGasto.get();
            return mapper.convertirToDTO(gastoB);
        }else{
            throw new NoSuchElementException("No se encontró el gasto con ID: " + id);
        }
    }

    @Override
    public void guardarGasto(GastoDTO gastoDTO){
        Gasto gastoR = mapper.convertirToEntity(gastoDTO);
        repository.save(gastoR);

    }

    @Override
    public void modificarGasto(GastoDTO gastoDTO, Long id){
        Optional<Gasto> optionalGasto = repository.findById(id);
        if(optionalGasto.isPresent()){
            Gasto gastoM = optionalGasto.get();

            gastoM.setMonto_gasto(gastoDTO.getMonto_gasto());
            gastoM.setTipo_gasto(gastoDTO.isTipo_gasto());

            Factura factura = new Factura();
            factura.setId_factura(gastoDTO.getId_factura());
            gastoM.setFactura(factura);

            repository.save(gastoM);
        }
        else{
            throw new NoSuchElementException("No se encontró el gasto con ID: " + id);
        }
    }

    @Override
    public void eliminarGasto(Long id){
        Optional<Gasto> optionalGasto = repository.findById(id);
        if(optionalGasto.isPresent()){
            repository.deleteById(id);
        }
        else{
            throw new NoSuchElementException("No se encontró el gasto con ID: " + id);
        }
    }
    @Override
    public List<GastoDTO> obtenerGastosPorFacturaId(Long facturaId) {
        List<Gasto> gastos= repository.findByFacturaId(facturaId);
        List<GastoDTO> gastoDTOS = new ArrayList<>();

        for(Gasto gasto: gastos){
            GastoDTO gastoDTO = mapper.convertirToDTO(gasto);
            gastoDTOS.add(gastoDTO);
        }
        return gastoDTOS;
    }

}
