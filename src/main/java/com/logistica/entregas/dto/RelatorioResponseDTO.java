package com.logistica.entregas.dto;

import java.math.BigDecimal;

public class RelatorioResponseDTO {

    private Long quantidade;
    private BigDecimal totalFrete;

    public Long getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Long quantidade) {
        this.quantidade = quantidade;
    }

    public BigDecimal getTotalFrete() {
        return totalFrete;
    }

    public void setTotalFrete(BigDecimal totalFrete) {
        this.totalFrete = totalFrete;
    }
}
