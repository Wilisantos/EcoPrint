package com.ecoprint.control_center.controller.mapper;

import com.ecoprint.control_center.dto.InsumoRequestDTO;
import com.ecoprint.control_center.model.Insumo;
import com.ecoprint.control_center.repository.InsumoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class InsumoMapper {

    private final InsumoRepository insumoRepository;

    // Criação de um novo Insumo
    public Insumo toModel(InsumoRequestDTO insumoRequestDTO) {
        return Insumo.builder()
                .tipoInsumo(insumoRequestDTO.tipoInsumo())
                .descricao(insumoRequestDTO.descricao())
                .quantidade(insumoRequestDTO.quantidade())
                .build();
    }

    // Atualização de um Insumo existente
    public Insumo toModel(Long id, InsumoRequestDTO insumoRequestDTO) {
        Insumo insumo = insumoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Insumo com ID " + id + " não encontrado"));

        insumo.setTipoInsumo(insumoRequestDTO.tipoInsumo());
        insumo.setDescricao(insumoRequestDTO.descricao());
        insumo.setQuantidade(insumoRequestDTO.quantidade());
        return insumo;
    }
}
