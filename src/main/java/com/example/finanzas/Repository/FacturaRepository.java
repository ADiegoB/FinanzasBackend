package com.example.finanzas.Repository;

import com.example.finanzas.models.dao.Factura;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Repository
public interface FacturaRepository extends JpaRepository<Factura, Long> {
    @Query(value = "SELECT t.valor AS valor_tasa " +
            "FROM factura f " +
            "JOIN cartera c ON f.cartera_id = c.id_cartera " +
            "JOIN tasa t ON c.id_tasa = t.id_tasa " +
            "WHERE f.id_factura = :id_factura", nativeQuery = true)
    Double findValorTasaByIdFactura(@Param("id_factura") Long idFactura);

    @Query("SELECT c.fecha_descuento FROM Cartera c JOIN c.facturas f WHERE f.id_factura = :idFactura")
    LocalDate findFechaDescuentoByFacturaId(@Param("idFactura") Long idFactura);

    @Query("SELECT f FROM Factura f WHERE f.cartera.id = :carteraId")
    List<Factura> findByCarteraId(@Param("carteraId") Long carteraId);

    @Query(value = "SELECT t.tipo_tasa AS tipo_tasa " +
            "FROM factura f " +
            "JOIN cartera c ON f.cartera_id = c.id_cartera " +
            "JOIN tasa t ON c.id_tasa = t.id_tasa " +
            "WHERE f.id_factura = :id_factura", nativeQuery = true)
    String findTipoTasaByIdFactura(@Param("id_factura") Long idFactura);
}
