package com.ecoprint.control_center.controller.mapper;

import com.ecoprint.control_center.dto.TipoProdutoRequestDTO;
import com.ecoprint.control_center.model.TipoProduto;
import org.springframework.stereotype.Component;

@Component
public class TipoProdutoMapper {

    // Converte de DTO para Entidade (criação)
    public TipoProduto toModel(TipoProdutoRequestDTO tipoProdutoRequestDTO) {
        return TipoProduto.builder()
                .id(tipoProdutoRequestDTO.id())
                .tipo(tipoProdutoRequestDTO.tipo())
                .build();
    }

    // Converte de Entidade para DTO
    public TipoProdutoRequestDTO toDTO(TipoProduto tipoProduto) {
        return new TipoProdutoRequestDTO(
                tipoProduto.getId(),
                tipoProduto.getTipo()
        );
    }
}
