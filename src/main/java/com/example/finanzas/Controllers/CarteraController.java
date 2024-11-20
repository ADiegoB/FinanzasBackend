package com.example.finanzas.Controllers;

import com.example.finanzas.Repository.CarteraRepository;
import com.example.finanzas.Repository.MonedaRepository;
import com.example.finanzas.Services.Implements.CarteraImp;
import com.example.finanzas.Services.Interfaces.ICartera;
import com.example.finanzas.models.dao.Cartera;
import com.example.finanzas.models.dao.Moneda;
import com.example.finanzas.models.dto.CarteraDTO;
import com.example.finanzas.models.dto.CarteraResumenDTO;
import com.example.finanzas.models.dto.UsuarioDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;


@RestController
@RequestMapping("/api/carteras")
@CrossOrigin(origins = "https://appweb-finanzas.vercel.app", allowedHeaders = "Authorization")
public class CarteraController {
    @Autowired
    private ICartera services;
    @Autowired
    private CarteraRepository carteraRepository;
    @Autowired
    CarteraImp s;

    @GetMapping("/get")
    public ResponseEntity<List<CarteraDTO>> getAll() {
        return ResponseEntity.ok(services.getAllCarteras());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CarteraDTO> getCartera(@PathVariable Long id) {
        CarteraDTO carteraDTO = services.getCartera(id);
        if (carteraDTO != null) {
            return ResponseEntity.ok(carteraDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/register")
    public ResponseEntity<Void> agregar(@RequestBody CarteraDTO carteraDTO) {
        services.guardarCartera(carteraDTO);
        return ResponseEntity.ok().build();
    }
    @CrossOrigin(origins = "http://localhost:4200")
    @PutMapping("/modificar/{id}")
    public ResponseEntity<Void> modificar(@RequestBody CarteraDTO carteraDTO, @PathVariable Long id) {
        services.modificarCartera(carteraDTO, id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Void> eliminar (@PathVariable Long id){
        services.eliminarCartera(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}/valorNominal")
    public ResponseEntity<BigDecimal>obtenerValorNominalTotal(@PathVariable Long id){
        BigDecimal valorNominal = s.calcularValorNominalTotal(id);
        if(valorNominal !=null){
            return ResponseEntity.ok(valorNominal);
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{carteraId}/totales")
    public CarteraResumenDTO obtenerTotalesPorCartera(@PathVariable Long carteraId) {
        return services.calcularTotalesPorCartera(carteraId);
    }

}
