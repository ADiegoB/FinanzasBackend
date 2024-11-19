package com.example.finanzas.Repository;

import com.example.finanzas.models.dao.Factura;
import com.example.finanzas.models.dao.Gasto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GastoRepository extends JpaRepository<Gasto, Long> {
    @Query("SELECT g FROM Gasto g WHERE g.factura.id = :facturaId")
    List<Gasto> findByFacturaId(@Param("facturaId") Long facturaId);
}
