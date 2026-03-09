package com.logistica.entregas;

import com.logistica.entregas.entity.Entrega;
import com.logistica.entregas.repository.EntregaRepository;
import com.logistica.entregas.service.EntregaService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

@ExtendWith(MockitoExtension.class)
public class EntregaServiceTest {
    @Mock EntregaRepository entregaRepository;

    @InjectMocks EntregaService entregaService;


    @Test
    public void deveLancarExcecaoFreteInvalido(){
        Entrega entrega = new Entrega();
        entrega.setValorFrete(BigDecimal.ZERO);

        Assertions.assertThrows(RuntimeException.class, () -> entregaService.salvar(entrega));
    }
}
