package com.ecoprint.control_center.controller.mapper;

import com.ecoprint.control_center.dto.ResiduoRequestDTO;
import com.ecoprint.control_center.model.Residuo;
import com.ecoprint.control_center.model.TipoResiduo;
import com.ecoprint.control_center.repository.ResiduoRepository;
import com.ecoprint.control_center.repository.TipoResiduoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ResiduoMapper {

    private final TipoResiduoRepository tipoResiduoRepository;
    private final ResiduoRepository residuoRepository;

    // Método para converter o DTO para o modelo
    public Residuo toModel(ResiduoRequestDTO residuoRequestDTO){

        // Verifica se existe o tipo de resíduo
        TipoResiduo tipoResiduo = tipoResiduoRepository.findById(residuoRequestDTO.tipoResiduo())
                .orElseThrow(() -> new IllegalArgumentException("Tipo de resíduo não encontrado"));

        // Cria e retorna o objeto Residuo
        return Residuo.builder()
                .tipoResiduo(tipoResiduo)
                .quantidade(residuoRequestDTO.quantidade())
                .build();
    }

    // Método para atualizar um resíduo já existente
    public Residuo toModel(Integer id, ResiduoRequestDTO residuoRequestDTO){

        // Verifica se o resíduo existe com o ID fornecido
        Residuo residuo = residuoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Resíduo com " + id + " não encontrado"));

        // Verifica se existe o tipo de resíduo que será atualizado
        TipoResiduo tipoResiduo = tipoResiduoRepository.findById(residuoRequestDTO.tipoResiduo())
                .orElseThrow(() -> new IllegalArgumentException("Tipo de resíduo não encontrado"));

        // Retorna o resíduo atualizado
        return Residuo.builder()
                .id(residuo.getId())
                .tipoResiduo(tipoResiduo)
                .quantidade(residuoRequestDTO.quantidade())
                .build();
    }
}
