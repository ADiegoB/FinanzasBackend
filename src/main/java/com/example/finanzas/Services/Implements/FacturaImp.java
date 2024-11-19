package com.example.finanzas.Services.Implements;

import com.example.finanzas.Repository.FacturaRepository;
import com.example.finanzas.Repository.GastoRepository;
import com.example.finanzas.Repository.TasaRepository;
import com.example.finanzas.Services.Interfaces.IFactura;
import com.example.finanzas.mappers.FacturaMapper;
import com.example.finanzas.models.dao.Cartera;
import com.example.finanzas.models.dao.Factura;

import com.example.finanzas.models.dao.Gasto;
import com.example.finanzas.models.dao.Tasa;
import com.example.finanzas.models.dto.FacturaDTO;
import com.example.finanzas.models.dto.GastoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
public class FacturaImp implements IFactura {
    @Autowired
    private FacturaRepository repository;
    @Autowired
    private TasaRepository tasaRepository;
    @Autowired
    private FacturaMapper mapper;
    @Autowired
    private GastoImp gastoImp;
    @Autowired
    private GastoRepository gastoRepository;
    @Override
    public List<FacturaDTO>getAllFacturas(){
        List<Factura> facturas = repository.findAll();
        List<FacturaDTO>facturaDTOS = new ArrayList<>();

        for(Factura factura: facturas){
            FacturaDTO facturaDTO = mapper.convertirToDTO(factura);
            facturaDTOS.add(facturaDTO);
        }
        return facturaDTOS;
    }

    @Override
    public FacturaDTO getFactura(Long id){
        Optional<Factura> optionalFactura = repository.findById(id);
        if(optionalFactura.isPresent()){
            Factura facturaB = optionalFactura.get();
            return mapper.convertirToDTO(facturaB);
        }else{
            throw new NoSuchElementException("No se encontró el libro con ID: " + id);
        }
    }

    @Override
    public void guardarFactura(FacturaDTO facturaDTO){
        Factura facturaR = mapper.convertirToEntity(facturaDTO);
        repository.save(facturaR);
        calcularCamposFactura(facturaR);
        repository.save(facturaR);
    }

    @Override
    public void modificarFactura (FacturaDTO facturaDTO, Long id){
        Optional<Factura> optionalFactura = repository.findById(id);
        if(optionalFactura.isPresent()){
            Factura facturaM = optionalFactura.get();
            facturaM.setNombre_factura(facturaDTO.getNombre_factura());
            facturaM.setValor_nominal(facturaDTO.getValor_nominal());
            facturaM.setFecha_emision(facturaDTO.getFecha_emision());
            facturaM.setFecha_vencimiento(facturaDTO.getFecha_vencimiento());
            facturaM.setEstado_factura(facturaDTO.isEstado_factura());

            Cartera cartera = new Cartera();
            cartera.setId_cartera(facturaDTO.getId_cartera());
            facturaM.setCartera(cartera);
            calcularCamposFactura(facturaM);
            repository.save(facturaM);
        }
        else {
            throw new NoSuchElementException("No se encontró el usuario con ID: " + id);
        }
    }

    @Override
    public void eliminarFactura(Long id){
        Optional<Factura> optionalFactura = repository.findById(id);
        if(optionalFactura.isPresent()){
            repository.deleteById(id);
        }
        else{
            throw new NoSuchElementException("No se encontró el empleado con ID: " + id);
        }
    }

    public Factura agregarGastoAFactura(Long facturaId, GastoDTO nuevoGastoDTO) {
        Factura factura = repository.findById(facturaId)
                .orElseThrow(() -> new RuntimeException("Factura no encontrada con ID: " + facturaId));

        // Configurar el ID de factura en GastoDTO
        nuevoGastoDTO.setId_factura(facturaId);

        // Guardar el nuevo gasto usando GastoService
        gastoImp.guardarGasto(nuevoGastoDTO);

        // Recalcular los campos de la factura teniendo en cuenta el nuevo gasto
        calcularCamposFactura(factura);

        // Guardar la factura con los valores recalculados
        return repository.save(factura);

    }



    // Método para calcular los campos de la factura
    public void calcularCamposFactura(Factura factura) {


        Double valorNominal = factura.getValor_nominal();


        // Obtener el valor de la tasa usando el repositorio
        Double tasaEfectivaValor = repository.findValorTasaByIdFactura(factura.getId_factura());

        if (tasaEfectivaValor == null) {
            throw new RuntimeException("No se encontró el valor de la tasa para la factura con ID: " + factura.getId_factura());
        }



        // Obtener el tipo de tasa usando el repositorio
        String tipoTasa = repository.findTipoTasaByIdFactura(factura.getId_factura());
        if (tipoTasa == null) {
            throw new RuntimeException("No se encontró el tipo de tasa para la factura con ID: " + factura.getId_factura());
        }

        // Si el tipo de tasa es TNA, convertir a TEA
        if ("TNA".equals(tipoTasa)) {
            Double resultado = (Math.pow(1 + (tasaEfectivaValor / 100) / 360, 360) - 1) * 100; // Conversión de TNA a TEA
            tasaEfectivaValor = resultado;
        }



        // Si tienes acceso a la fecha de descuento
        LocalDate fechaDescuento = repository.findFechaDescuentoByFacturaId(factura.getId_factura());
        LocalDate fechaVencimiento = factura.getFecha_vencimiento();
        if (fechaDescuento == null || fechaVencimiento == null) {
            throw new RuntimeException("Las fechas de descuento o vencimiento no son válidas.");
        }

        // Calcular N: Días entre la fecha de vencimiento y la fecha de descuento

        long N = ChronoUnit.DAYS.between(fechaDescuento,fechaVencimiento);// Convertir milisegundos a días

        // Tasa efectiva
        Double tasaEfectiva =  ((Math.pow((1 + (tasaEfectivaValor/100)), ((double) N / 360)) - 1));


        // Tasa descontada
        Double tasaDescontada = tasaEfectiva / (1 + tasaEfectiva);

        // Descuento
        Double descuento = valorNominal * tasaDescontada;

        // Valor neto
        Double valorNeto = valorNominal - descuento;

        // Supongamos que los gastos iniciales y finales son propiedades de la factura
        Double gastosIniciales = factura.getGastos() == null ? 0.0 :
                factura.getGastos().stream()
                        .filter(gasto -> !gasto.isTipo_gasto()) // Tipo false es gasto inicial
                        .mapToDouble(Gasto::getMonto_gasto)
                        .sum();

        Double gastosFinales = factura.getGastos() == null ? 0.0 :
                factura.getGastos().stream()
                        .filter(Gasto::isTipo_gasto) // Tipo true es gasto final
                        .mapToDouble(Gasto::getMonto_gasto)
                        .sum();
        // Valor recibido
        Double valorRecibido = valorNeto - gastosIniciales ;

        // Valor entregado
        Double valorEntregado = valorNominal + gastosFinales;

        // Asignar los valores calculados a la factura
        factura.setTasa_efectiva(tasaEfectiva);
        factura.setTasa_descontada(tasaDescontada);
        factura.setDescuento(descuento);
        factura.setValor_neto(valorNeto);
        factura.setValor_recibido(valorRecibido);
        factura.setValor_entregado(valorEntregado);
    }

    @Override
    public void cambiarEstadoFactura(Long id, boolean nuevoEstado) {
        Optional<Factura> optionalFactura = repository.findById(id);
        if (optionalFactura.isPresent()) {
            Factura factura = optionalFactura.get();
            factura.setEstado_factura(nuevoEstado);
            repository.save(factura); // Guarda la factura con el nuevo estado
        } else {
            throw new NoSuchElementException("No se encontró la factura con ID: " + id);
        }
    }

    @Override
    public List<FacturaDTO> obtenerFacturasPorCarteraId(Long carteraId) {
        List<Factura> facturas = repository.findByCarteraId(carteraId);
        List<FacturaDTO> facturaDTOS = new ArrayList<>();

        for (Factura factura : facturas) {
            FacturaDTO facturaDTO = mapper.convertirToDTO(factura);
            facturaDTOS.add(facturaDTO);
        }
        return facturaDTOS;
    }

    @Override
    public void actualizarGasto(GastoDTO gastoDTO, Long id) {
        Optional<Gasto> optionalGasto = gastoRepository.findById(id);
        if (optionalGasto.isPresent()) {
            Gasto gastoAActualizar = optionalGasto.get();

            // Actualiza los campos del gasto con los nuevos datos de gastoDTO
            gastoAActualizar.setMonto_gasto(gastoDTO.getMonto_gasto());
            gastoAActualizar.setTipo_gasto(gastoDTO.isTipo_gasto());

            // Actualiza la factura asociada a este gasto
            if (gastoAActualizar.getFactura() != null) {
                Factura factura = gastoAActualizar.getFactura();

                // Recalcular los valores de la factura
                calcularCamposFactura(factura);

                // Guardar la factura actualizada
                repository.save(factura);
            }

            // Guardar el gasto actualizado
            gastoRepository.save(gastoAActualizar);
        } else {
            throw new NoSuchElementException("No se encontró el gasto con ID: " + id);
        }
    }


    @Override
    public void eliminarGasto(Long gastoId) {
        // Buscar el gasto por ID
        Optional<Gasto> optionalGasto = gastoRepository.findById(gastoId);
        if (optionalGasto.isPresent()) {
            Gasto gasto = optionalGasto.get();

            // Obtener la factura asociada al gasto
            Factura factura = gasto.getFactura();

            // Eliminar el gasto
            gastoRepository.delete(gasto);

            // Recalcular los campos de la factura después de eliminar el gasto
            calcularCamposFactura(factura);

            // Guardar la factura actualizada
            repository.save(factura);
        } else {
            throw new NoSuchElementException("No se encontró el gasto con ID: " + gastoId);
        }
    }

}
