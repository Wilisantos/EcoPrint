package com.ecoprint.control_center.controller.mapper;

import com.ecoprint.control_center.dto.TipoResiduoRequestDTO;
import com.ecoprint.control_center.model.TipoResiduo;
import com.ecoprint.control_center.repository.TipoResiduoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TipoResiduoMapper {

    private final TipoResiduoRepository tipoResiduoRepository;

    // Criação de um novo TipoResiduo
    public TipoResiduo toModel(TipoResiduoRequestDTO tipoResiduoRequestDTO) {
        return TipoResiduo.builder()
                .tipo(tipoResiduoRequestDTO.tipo())
                .build();
    }

    // Atualização de um TipoResiduo existente
    public TipoResiduo toModel(Integer id, TipoResiduoRequestDTO tipoResiduoRequestDTO) {
        TipoResiduo tipoResiduo = tipoResiduoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Tipo de resíduo com ID " + id + " não encontrado"));

        tipoResiduo.setTipo(tipoResiduoRequestDTO.tipo());
        return tipoResiduo;
    }
}
