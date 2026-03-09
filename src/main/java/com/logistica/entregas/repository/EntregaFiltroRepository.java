package com.logistica.entregas.repository;

import com.logistica.entregas.entity.Entrega;
import com.logistica.entregas.enumeration.StatusEntrega;
import jakarta.persistence.criteria.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
public class EntregaFiltroRepository {

    public static Specification<Entrega> filtrar(StatusEntrega status, LocalDate dataEnvioInicio, LocalDate dataEnvioFim, String destinatario) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (status != null) {
                predicates.add(cb.equal(root.get("status"), status));
            }

            if (dataEnvioInicio != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("dataEnvio"), dataEnvioInicio));
            }

            if (dataEnvioFim != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("dataEnvio"), dataEnvioFim));
            }

            if (destinatario != null && !destinatario.isEmpty()) {
                predicates.add(cb.like(cb.lower(root.get("destinatario")), "%" + destinatario.toLowerCase() + "%"));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
