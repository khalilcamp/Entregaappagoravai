package com.logistica.entregas.controller;

import com.logistica.entregas.dto.RelatorioResponseDTO;
import com.logistica.entregas.entity.Entrega;
import com.logistica.entregas.enumeration.StatusEntrega;
import com.logistica.entregas.service.EntregaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/entregas")
public class EntregaController {

    @Autowired EntregaService entregaService;

    @PostMapping
    public Entrega cadastrar(@RequestBody Entrega entrega){
        return entregaService.salvar(entrega);
    }

    @PutMapping("/{id}")
    public Entrega atualizar(@PathVariable Long id, @RequestBody Entrega entrega){
        return entregaService.atualizar(id, entrega);
    }

    @PatchMapping("/{id}/status")
    public Entrega alterarStatus(@PathVariable Long id, @RequestBody StatusEntrega novoStatus){
        return entregaService.alterarStatus(id, novoStatus);
    }

    @GetMapping
    public Page<Entrega> listar(@RequestParam(required = false) StatusEntrega status, @RequestParam(required = false) LocalDate dataEnvioInicio, @RequestParam(required = false) LocalDate dataEnvioFim, @RequestParam(required = false) String destinatario, Pageable pageable) {
        return entregaService.listar(status, dataEnvioInicio, dataEnvioFim, destinatario, pageable);
    }

    @GetMapping("/{id}")
    public Entrega buscarPorId(@PathVariable Long id){

        return entregaService.buscarPorId(id);
    }

    @GetMapping("/relatorio")
    public RelatorioResponseDTO buscarPorRelatorio(@RequestParam LocalDate inicio, @RequestParam LocalDate fim){
        return entregaService.buscarPorRelatorio(inicio, fim);
    }

    @PostMapping("/importacao")
    public List<Entrega> importacaoCSV(@RequestParam MultipartFile arquivo) throws Exception {
        return entregaService.importarCSV(arquivo);
    }
}
