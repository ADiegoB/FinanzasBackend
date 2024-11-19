package com.example.finanzas.Repository;

import com.example.finanzas.models.dao.Moneda;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MonedaRepository extends JpaRepository<Moneda,Long> {
    @Query("SELECT c.moneda.simbolo FROM Cartera c WHERE c.id = :carteraId")
    String findSimboloByCarteraId(@Param("carteraId") Long carteraId);
}
