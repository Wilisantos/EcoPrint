package com.ecoprint.control_center.controller;

import com.ecoprint.control_center.controller.mapper.ImpressaoMapper;
import com.ecoprint.control_center.dto.ImpressaoRequestDTO;
import com.ecoprint.control_center.model.Impressao;
import com.ecoprint.control_center.repository.ImpressaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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
}
