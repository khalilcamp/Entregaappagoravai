package com.logistica.entregas.service;

import com.logistica.entregas.entity.Entrega;
import com.logistica.entregas.enumeration.StatusEntrega;
import com.logistica.entregas.repository.EntregaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;

@Service
public class EntregaService {

    @Autowired private EntregaRepository entregaRepository;

    public Entrega salvar(Entrega entrega){
        Entrega entregaItem = new Entrega();
        return entregaItem;
    }

    public Entrega atualizar(Long id, Entrega entrega){
        Entrega entregaItem = new Entrega();
        return entregaItem;
    }

    public Entrega alterarStatus(Long id, StatusEntrega novoStatus){
        Entrega entregaItem = new Entrega();
        return entregaItem;
    }

    public Page<Entrega> listar(Pageable pageable){
        return null;
    }

    public Entrega buscarPorId(Long id){
        Entrega entregaItem = new Entrega();
        return entregaItem;
    }
}
