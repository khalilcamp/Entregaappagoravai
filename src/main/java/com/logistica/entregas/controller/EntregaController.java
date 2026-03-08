package com.logistica.entregas.controller;

import com.logistica.entregas.service.EntregaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/entregas")
public class EntregaController {

    @Autowired EntregaService entregaService;

    @PostMapping
    public String cadastrar(){
        return "Cadastro de usuário";
    }

    @PutMapping("/{id}")
    public String atualizar(){
        return null;
    }

    @PatchMapping("/{id}/status")
    public String alterarStatus(){
        return null;
    }

    @GetMapping
    public String listar(){
        return null;
    }

    @GetMapping("/{id}")
    public String buscarPorId(){
        return null;
    }
}
