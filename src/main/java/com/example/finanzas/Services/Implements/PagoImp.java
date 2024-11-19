package com.example.finanzas.Services.Implements;

import com.example.finanzas.Repository.PagoRepository;
import com.example.finanzas.Services.Interfaces.IPago;
import com.example.finanzas.mappers.PagoMapper;
import com.example.finanzas.models.dao.Factura;
import com.example.finanzas.models.dao.Pago;
import com.example.finanzas.models.dao.Tasa;
import com.example.finanzas.models.dto.PagoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class PagoImp implements IPago {
    @Autowired
    private PagoRepository repository;
    @Autowired
    private PagoMapper mapper;

    @Override
    public List<PagoDTO> getAllPagos(){
        List<Pago> pagos =  repository.findAll();
        List<PagoDTO> pagoDTOS = new ArrayList<>();

        for(Pago pago: pagos){
            PagoDTO pagoDTO = mapper.convertirToDTO(pago);
            pagoDTOS.add(pagoDTO);
        }
        return pagoDTOS;
    }

    @Override
    public PagoDTO getPago(Long id){
        Optional<Pago> optionalPago = repository.findById(id);
        if(optionalPago.isPresent()){
            Pago pagoB = optionalPago.get();
            return mapper.convertirToDTO(pagoB);
        }
        else{
            throw new NoSuchElementException("No se encontró el usuario con ID: " + id);
        }
    }

    @Override
    public void guardarPago(PagoDTO pagoDTO){
        Pago pagoR = mapper.convertirToEntity(pagoDTO);
        repository.save(pagoR);
    }

    @Override
    public void modificarPago (PagoDTO pagoDTO, Long id){
        Optional<Pago> optionalPago = repository.findById(id);
        if(optionalPago.isPresent()){
            Pago pagoM = optionalPago.get();
            pagoM.setFecha_pago(pagoDTO.getFecha_pago());
            pagoM.setMonto_pago(pagoDTO.getMonto_pago());

            Factura factura = new Factura();
            factura.setId_factura(pagoDTO.getId_factura());
            pagoM.setFactura(factura);
        }
        else{
            throw new NoSuchElementException("No se encontró el usuario con ID: " + id);
        }
    }

    @Override
    public void eliminarPago(Long id){
        Optional<Pago> optionalPago = repository.findById(id);
        if(optionalPago.isPresent()){
            repository.deleteById(id);
        }
        else{
            throw new NoSuchElementException("No se encontró el empleado con ID: " + id);
        }
    }
}
