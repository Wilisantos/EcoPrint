package com.ecoprint.control_center.controller.mapper;

import com.ecoprint.control_center.dto.ReciclagemRequestDTO;
import com.ecoprint.control_center.model.Reciclagem;
import com.ecoprint.control_center.repository.ReciclagemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReciclagemMapper {

    private final ReciclagemRepository reciclagemRepository;

    // Criação de uma nova entidade Reciclagem
    public Reciclagem toModel(ReciclagemRequestDTO reciclagemRequestDTO) {
        return Reciclagem.builder()
                .tipoResiduos(reciclagemRequestDTO.tipoResiduos())
                .pontoReciclagem(reciclagemRequestDTO.pontoReciclagem())
                .valorGanho(reciclagemRequestDTO.valorGanho())
                .build();
    }

    // Atualização de uma entidade Reciclagem existente
    public Reciclagem toModel(Integer id, ReciclagemRequestDTO reciclagemRequestDTO) {
        // Busca a reciclagem pelo ID ou lança uma exceção se não encontrar
        Reciclagem reciclagem = reciclagemRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Reciclagem com ID " + id + " não encontrada"));

        // Atualiza os campos da entidade existente
        reciclagem.setTipoResiduos(reciclagemRequestDTO.tipoResiduos());
        reciclagem.setPontoReciclagem(reciclagemRequestDTO.pontoReciclagem());
        reciclagem.setValorGanho(reciclagemRequestDTO.valorGanho());

        return reciclagem;
    }
}
