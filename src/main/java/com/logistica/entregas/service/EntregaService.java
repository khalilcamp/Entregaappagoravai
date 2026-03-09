package com.logistica.entregas.service;

import com.logistica.entregas.dto.RelatorioResponseDTO;
import com.logistica.entregas.entity.Entrega;
import com.logistica.entregas.enumeration.StatusEntrega;
import com.logistica.entregas.repository.EntregaFiltroRepository;
import com.logistica.entregas.repository.EntregaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class EntregaService {

    @Autowired private EntregaRepository entregaRepository;
    @Autowired private EntregaFiltroRepository entregaFiltroRepository;
    @Autowired private CsvService csvService;

    public Entrega salvar(Entrega entrega){
        if (entrega.getValorFrete().compareTo(BigDecimal.ZERO) <= 0 || entrega.getDataEntrega() != null && entrega.getDataEntrega().isBefore(entrega.getDataEnvio())){
            throw new RuntimeException("Valores inválidos. Favor checar e tentar novamente.");
        }
        entrega.setStatus(StatusEntrega.PENDENTE);
        entregaRepository.save(entrega);
        return entrega;
    }

    public Entrega atualizar(Long id, Entrega entrega) {
        Entrega entregaExistente = buscarPorId(id);
        entregaExistente.setCodigoRastreamento(entrega.getCodigoRastreamento());
        entregaExistente.setDataEnvio(entrega.getDataEnvio());
        entregaExistente.setDataEntrega(entrega.getDataEntrega());
        entregaExistente.setValorFrete(entrega.getValorFrete());
        entregaExistente.setDestinatario(entrega.getDestinatario());
        entregaExistente.setEndereco(entrega.getEndereco());
        return entregaRepository.save(entregaExistente);
    }

    public Entrega alterarStatus(Long id, StatusEntrega novoStatus){
        Entrega entregaId = buscarPorId(id);
        StatusEntrega statusAtual = entregaId.getStatus();

        boolean transicaoValida = (statusAtual == StatusEntrega.PENDENTE && novoStatus == StatusEntrega.EM_TRANSITO) ||
                (statusAtual == StatusEntrega.PENDENTE && novoStatus == StatusEntrega.CANCELADO) ||
                (statusAtual == StatusEntrega.EM_TRANSITO && novoStatus == StatusEntrega.ENTREGUE) ||
                (statusAtual == StatusEntrega.EM_TRANSITO && novoStatus == StatusEntrega.CANCELADO);


        if (!transicaoValida){
                    throw new RuntimeException("Transição de Status inválida:" + statusAtual + novoStatus);
        }
        if (novoStatus == StatusEntrega.ENTREGUE) {
            entregaId.setDataEntrega(LocalDate.now());
        }
        entregaId.setStatus(novoStatus);
        return entregaRepository.save(entregaId);
    }

    public Page<Entrega> listar(StatusEntrega status, LocalDate dataEnvioInicio, LocalDate dataEnvioFim, String destinatario, Pageable pageable) {
        Specification<Entrega> spec = EntregaFiltroRepository.filtrar(status, dataEnvioInicio, dataEnvioFim, destinatario);
        return entregaRepository.findAll(spec, pageable);
    }

    public Entrega buscarPorId(Long id) {
        return entregaRepository.findById(id).orElseThrow(() -> new RuntimeException("Entrega não encontrada"));
    }

    public RelatorioResponseDTO buscarPorRelatorio(LocalDate inicio, LocalDate fim) {
        List<Object[]> resultado = entregaRepository.buscarRelatorio(inicio, fim);

        if (resultado.isEmpty()) {
            RelatorioResponseDTO vazio = new RelatorioResponseDTO();
            vazio.setQuantidade(0L);
            vazio.setTotalFrete(BigDecimal.ZERO);
            return vazio;
        }

        Object[] dados = resultado.get(0);
        RelatorioResponseDTO relatorio = new RelatorioResponseDTO();
        relatorio.setQuantidade((Long) dados[0]);
        relatorio.setTotalFrete((BigDecimal) dados[1]);
        return relatorio;
    }

    public List<Entrega> importarCSV(MultipartFile arquivo) throws Exception {
        List<Entrega> entregas = csvService.buscarPorCSV(arquivo);
        for (Entrega entrega : entregas){
            salvar(entrega);
        }
        return entregas;
    }
}
