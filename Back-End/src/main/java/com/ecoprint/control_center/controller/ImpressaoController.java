package com.ecoprint.control_center.controller;

import com.ecoprint.control_center.controller.mapper.ImpressaoMapper;
import com.ecoprint.control_center.dto.ImpressaoRequestDTO;
import com.ecoprint.control_center.model.Impressao;
import com.ecoprint.control_center.repository.ImpressaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

@RestController
@RequestMapping("/impressoes")
@RequiredArgsConstructor
public class ImpressaoController {

    private final ImpressaoRepository impressaoRepository;
    private final ImpressaoMapper impressaoMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Impressao create(@RequestBody ImpressaoRequestDTO impressaoRequestDTO) {
        Impressao impressao = impressaoMapper.toModel(impressaoRequestDTO);
        return impressaoRepository.save(impressao);
    }

    @GetMapping("/count")
    public long countImpressoras() {
        return impressaoRepository.count();
    }

    @GetMapping
    public ResponseEntity<String> gerarRelatorioCSV() {
        StringWriter writer = new StringWriter();
        PrintWriter csvWriter = new PrintWriter(writer);

        // Cabeçalho
        csvWriter.println("ID,Produto ID,Descrição,Modelo ICC,CMYK Predictor");

        // Dados
        List<Impressao> impressoes = impressaoRepository.findAll();
        for (Impressao impressao : impressoes) {
            csvWriter.printf("%d,%d,%s,%s,%s%n",
                    impressao.getId(),
                    impressao.getProduto().getId(),
                    impressao.getDescricao(),
                    impressao.getIccModel(),
                    impressao.getCmykPredictor()
            );
        }

        csvWriter.flush();

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=relatorio_impressoes.csv")
                .body(writer.toString());
    }

}
