package com.example.finanzas.Repository;

import com.example.finanzas.models.dao.Tasa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TasaRepository extends JpaRepository<Tasa, Long> {
}
