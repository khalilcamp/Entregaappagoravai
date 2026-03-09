package com.logistica.entregas.repository;

import com.logistica.entregas.entity.Entrega;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface EntregaRepository extends JpaRepository<Entrega, Long>, JpaSpecificationExecutor<Entrega> {

    @Query("SELECT COUNT(e), SUM(e.valorFrete) FROM Entrega e WHERE e.status = 'ENTREGUE' AND e.dataEntrega BETWEEN :inicio AND :fim")
    List<Object[]> buscarRelatorio(@Param("inicio") LocalDate inicio, @Param("fim") LocalDate fim);
}
