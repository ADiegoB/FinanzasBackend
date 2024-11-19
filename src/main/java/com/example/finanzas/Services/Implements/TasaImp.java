package com.example.finanzas.Services.Implements;

import com.example.finanzas.Repository.TasaRepository;
import com.example.finanzas.Services.Interfaces.ITasa;
import com.example.finanzas.mappers.TasaMapper;
import com.example.finanzas.models.dao.Tasa;

import com.example.finanzas.models.dto.TasaDTO;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class TasaImp implements ITasa {
    @Autowired
    private TasaRepository repository;
    @Autowired
    private TasaMapper mapper;
    @Override
    public List<TasaDTO> getAllTasas(){
        List<Tasa> tasas = repository.findAll();
        List<TasaDTO> tasaDTOS = new ArrayList<>();
        for(Tasa tasa: tasas){
            TasaDTO tasaDTO = mapper.convertirToDTO(tasa);
            tasaDTOS.add(tasaDTO);
        }
        return tasaDTOS;
    }

    @Override
    public TasaDTO getTasa(Long id){
        Optional<Tasa> optionalTasa = repository.findById(id);
        if(optionalTasa.isPresent()){
            Tasa tasaB = optionalTasa.get();
            return mapper.convertirToDTO(tasaB);
        }
        else{
            throw new NoSuchElementException("No se encontró el libro con ID: "+id);
        }
    }

    @Override
    public void guardartasa (TasaDTO tasaDTO){
        Tasa tasaR = mapper.convertirToEntity(tasaDTO);
        repository.save(tasaR);
    }

    @Override
    public void modificarTasa(TasaDTO tasaDTO, Long id){
        Optional<Tasa> optionalTasa = repository.findById(id);
        if(optionalTasa.isPresent()){
            Tasa tasaM = optionalTasa.get();
            tasaM.setTipo_tasa(tasaDTO.getTipo_tasa());
            tasaM.setValor(tasaDTO.getValor());
            repository.save(tasaM);

        }
        else{
            throw new NoSuchElementException("No se encontró el usuario con ID: " + id);
        }
    }

    @Override
    public void eliminarTasa(Long id){
        Optional<Tasa> optionalTasa = repository.findById(id);
        if(optionalTasa.isPresent()){
            repository.deleteById(id);
        }
        else{
            throw new NoSuchElementException("No se encontró el empleado con ID: " + id);
        }

    }

}
