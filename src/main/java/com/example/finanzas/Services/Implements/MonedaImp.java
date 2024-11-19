package com.example.finanzas.Services.Implements;

import com.example.finanzas.Repository.MonedaRepository;
import com.example.finanzas.Services.Interfaces.IMoneda;
import com.example.finanzas.mappers.MonedaMapper;
import com.example.finanzas.models.dao.Moneda;
import com.example.finanzas.models.dto.MonedaDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class MonedaImp implements IMoneda {
    @Autowired
    private MonedaRepository repository;
    @Autowired
    private MonedaMapper mapper;

    @Override
    public List<MonedaDTO> getAllMonedas(){
        List<Moneda> monedas = repository.findAll();
        List<MonedaDTO> monedaDTOS = new ArrayList<>();
        for (Moneda moneda: monedas){
            MonedaDTO monedaDTO = mapper.convertirToDTO(moneda);
            monedaDTOS.add(monedaDTO);
        }
        return monedaDTOS;
    }
    @Override
    public MonedaDTO getMoneda(Long id){
        Optional<Moneda> optionalMoneda = repository.findById(id);
        if(optionalMoneda.isPresent()){
            Moneda monedaB = optionalMoneda.get();
            return mapper.convertirToDTO(monedaB);
        }
        else {
            throw new NoSuchElementException("No se encontró el autor con ID: " + id);
        }
    }
    @Override
    public void guardarMoneda(MonedaDTO monedaDTO){
        Moneda monedaR = mapper.convertirToEntity(monedaDTO);
        repository.save(monedaR);
    }
    @Override
    public void modificarMoneda(MonedaDTO monedaDTO, Long id){
        Optional<Moneda> optionalMoneda = repository.findById(id);
        if(optionalMoneda.isPresent()){
            Moneda monedaM = optionalMoneda.get();
            monedaM.setNombre_moneda(monedaDTO.getNombre_moneda());
            monedaM.setSimbolo(monedaDTO.getSimbolo());
            repository.save(monedaM);
        }
    }

    @Override
    public void eliminarMoneda (Long id){
        Optional<Moneda> optionalMoneda=repository.findById(id);
        if(optionalMoneda.isPresent()){
            repository.deleteById(id);
        }
        else{
            throw new NoSuchElementException("No se encontró el empleado con ID: " + id);
        }
    }
        @Override
     public String obtenerMonedaPorCarteraId(Long idCartera) {
            return repository.findSimboloByCarteraId(idCartera);
    }

}
