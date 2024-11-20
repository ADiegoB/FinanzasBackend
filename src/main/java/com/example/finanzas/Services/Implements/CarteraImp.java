package com.example.finanzas.Services.Implements;

import com.example.finanzas.Repository.CarteraRepository;
import com.example.finanzas.Repository.FacturaRepository;
import com.example.finanzas.Services.Interfaces.ICartera;
import com.example.finanzas.mappers.CarteraMapper;
import com.example.finanzas.models.dao.*;
import com.example.finanzas.models.dto.CarteraDTO;
import com.example.finanzas.models.dto.CarteraResumenDTO;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class CarteraImp implements ICartera {
    @Autowired
    private CarteraRepository repository;
    @Autowired
    private CarteraMapper mapper;
    @Autowired
    private FacturaRepository facturaRepository;
    @Autowired
    private CarteraRepository carteraRepository;
    @Autowired
    private FacturaImp facturaImp;
    @Override
    public List<CarteraDTO> getAllCarteras(){
        List<Cartera> carteras = repository.findAll();
        List<CarteraDTO> carteraDTOS = new ArrayList<>();

        for(Cartera cartera: carteras){
            CarteraDTO carteraDTO = mapper.convertirToDTO(cartera);
            carteraDTOS.add(carteraDTO);
        }
        return carteraDTOS;
    }

    @Override
    public CarteraDTO getCartera(Long id){
        Optional<Cartera> optionalCartera= repository.findById(id);

        if(optionalCartera.isPresent()){
            Cartera carteraB = optionalCartera.get();
            return mapper.convertirToDTO(carteraB);
        }else{
            throw new NoSuchElementException("No se encontró el usuario con ID: " + id);
        }
    }

    @Override
    public void guardarCartera(CarteraDTO carteraDTO){
        Cartera carteraR = mapper.convertirToEntity(carteraDTO);
        repository.save(carteraR);
    }

    @Override
    public void modificarCartera(CarteraDTO carteraDTO, Long id) {
        Optional<Cartera> optionalCartera = repository.findById(id);
        if (optionalCartera.isPresent()) {
            Cartera carteraM = optionalCartera.get();
            carteraM.setNombre_cartera(carteraDTO.getNombre_cartera());
            carteraM.setFecha_descuento(carteraDTO.getFecha_descuento());

            Usuario usuario = new Usuario();
            usuario.setId_usuario(carteraDTO.getId_usuario());
            carteraM.setUsuario(usuario);

            Tasa tasa = new Tasa();
            tasa.setId_tasa(carteraDTO.getId_tasa());
            carteraM.setTasa(tasa);

            Moneda moneda = new Moneda();
            moneda.setId_moneda(carteraDTO.getId_moneda());
            carteraM.setMoneda(moneda);

            // Actualiza la cartera
            repository.save(carteraM);

            // Obtener las facturas asociadas a la cartera
            List<Factura> facturas = facturaRepository.findByCarteraId(id);
            // Recalcular los valores de cada factura después de modificar la fecha de descuento de la cartera
            for (Factura factura : facturas) {
                facturaImp.calcularCamposFactura(factura);
            }

        } else {
            throw new NoSuchElementException("No se encontró la cartera con ID: " + id);
        }
    }


    @Override
    public void eliminarCartera(Long id){
        Optional<Cartera> optionalCartera = repository.findById(id);
        if(optionalCartera.isPresent()){
            repository.deleteById(id);
        }
        else{
            throw new NoSuchElementException("No se encontró el empleado con ID: " + id);
        }
    }

    public BigDecimal calcularValorNominalTotal(Long id){
        BigDecimal total = repository.calcularValorNominalTotalPorCartera(id);
        if(total ==null){
            return BigDecimal.ZERO;
        }
        return total;
    }

    public CarteraResumenDTO calcularTotalesPorCartera(Long carteraId) {
        // Obtener la cartera
        Cartera cartera = repository.findById(carteraId)
                .orElseThrow(() -> new RuntimeException("Cartera no encontrada con ID: " + carteraId));
        // Obtener las facturas de la cartera
        List<Factura> facturas = facturaRepository.findByCarteraId(carteraId);
        // Inicializar los totales
        double totalDescuento = 0.0;
        double totalValorNeto = 0.0;
        double totalValorRecibido = 0.0;
        double totalValorEntregado = 0.0;
        long totalDias = 0;
        // Sumar los valores de cada factura en la cartera
        for (Factura factura : facturas) {
            if(factura.isEstado_factura()){
                totalDescuento += factura.getDescuento();
                totalValorNeto += factura.getValor_neto();
                totalValorRecibido += factura.getValor_recibido();
                totalValorEntregado += factura.getValor_entregado();
                totalDias += ChronoUnit.DAYS.between(factura.getCartera().getFecha_descuento(), factura.getFecha_vencimiento());
            }
        }
        // TCEA = ((Valor Entregado / Valor Recibido)^(360 / totalDias)) - 1
        double tcea = (totalValorRecibido > 0 && totalDias > 0)
                ? 100 * (Math.pow((totalValorEntregado / totalValorRecibido), (360.0 / totalDias)) - 1)
                : 0.0;
        // Crear y devolver el DTO con los totales
        CarteraResumenDTO resumenDTO = new CarteraResumenDTO();
        resumenDTO.setCarteraId(carteraId);
        resumenDTO.setTotalDescuento(totalDescuento);
        resumenDTO.setTotalValorNeto(totalValorNeto);
        resumenDTO.setTotalValorRecibido(totalValorRecibido);
        resumenDTO.setTotalValorEntregado(totalValorEntregado);
        resumenDTO.setTotalDias(totalDias);
        resumenDTO.setTcea(tcea);
        return resumenDTO;
    }
}
