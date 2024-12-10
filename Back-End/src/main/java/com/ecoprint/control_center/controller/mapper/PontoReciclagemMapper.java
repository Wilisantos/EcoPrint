package com.ecoprint.control_center.controller.mapper;

import com.ecoprint.control_center.dto.PontoReciclagemRequestDTO;
import com.ecoprint.control_center.model.PontoReciclagem;
import com.ecoprint.control_center.repository.PontoReciclagemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PontoReciclagemMapper {

    private final PontoReciclagemRepository pontoReciclagemRepository;

    // Criação de um novo PontoReciclagem
    public PontoReciclagem toModel(PontoReciclagemRequestDTO pontoReciclagemRequestDTO) {
        return PontoReciclagem.builder()
                .tipoResiduos(pontoReciclagemRequestDTO.tipoResiduos())
                .descricao(pontoReciclagemRequestDTO.descricao())
                .build();
    }

    // Atualização de um PontoReciclagem existente
    public PontoReciclagem toModel(Integer id, PontoReciclagemRequestDTO pontoReciclagemRequestDTO) {
        PontoReciclagem pontoReciclagem = pontoReciclagemRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("PontoReciclagem com ID " + id + " não encontrado"));

        pontoReciclagem.setTipoResiduos(pontoReciclagemRequestDTO.tipoResiduos());
        pontoReciclagem.setDescricao(pontoReciclagemRequestDTO.descricao());

        return pontoReciclagem;
    }
}
