package com.example.finanzas.Repository;

import com.example.finanzas.models.dao.Cartera;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public interface CarteraRepository extends JpaRepository<Cartera, Long> {
    @Query("SELECT SUM(f.valor_nominal) FROM Factura f WHERE f.cartera.id = :carteraId")
    BigDecimal calcularValorNominalTotalPorCartera(@Param("carteraId") Long carteraId);






}
