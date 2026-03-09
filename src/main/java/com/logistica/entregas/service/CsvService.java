package com.logistica.entregas.service;

import com.logistica.entregas.entity.Entrega;
import com.logistica.entregas.enumeration.StatusEntrega;
import com.opencsv.CSVReader;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class CsvService {

    public List<Entrega> buscarPorCSV(MultipartFile arquivo) throws Exception{
        InputStreamReader arquivoLido = new InputStreamReader(arquivo.getInputStream());
        CSVReader csvLeitor = new CSVReader(arquivoLido);

        List<Entrega> listaRelatorioCSV = new ArrayList<>();
        List<String[]> linhas = csvLeitor.readAll();
        for (String[] linha : linhas.subList(1, linhas.size())) {
            Entrega entrega = new Entrega();
            entrega.setCodigoRastreamento(linha[0]);
            entrega.setDataEnvio(LocalDate.parse(linha[1]));
            entrega.setDataEntrega(linha[2].isEmpty() ? null : LocalDate.parse(linha[2]));
            entrega.setValorFrete(new BigDecimal(linha[3]));
            entrega.setDestinatario(linha[4]);
            entrega.setEndereco(linha[5]);
            entrega.setStatus(StatusEntrega.valueOf(linha[6]));
            listaRelatorioCSV.add(entrega);
        }
        return listaRelatorioCSV;
    }
}
