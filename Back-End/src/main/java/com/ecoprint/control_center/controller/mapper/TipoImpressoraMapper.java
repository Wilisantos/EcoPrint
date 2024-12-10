package com.ecoprint.control_center.controller.mapper;

import com.ecoprint.control_center.dto.TipoImpressoraRequestDTO;
import com.ecoprint.control_center.model.TipoImpressora;
import com.ecoprint.control_center.repository.TipoImpressoraRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TipoImpressoraMapper {

    private final TipoImpressoraRepository tipoImpressoraRepository;

    // Criação de um novo TipoImpressora
    public TipoImpressora toModel(TipoImpressoraRequestDTO tipoImpressoraRequestDTO) {
        return TipoImpressora.builder()
                .tipo(tipoImpressoraRequestDTO.tipo())
                .build();
    }

    // Atualização de um TipoImpressora existente
    public TipoImpressora toModel(Integer id, TipoImpressoraRequestDTO tipoImpressoraRequestDTO) {
        TipoImpressora tipoImpressora = tipoImpressoraRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Tipo de impressora com ID " + id + " não encontrado"));

        tipoImpressora.setTipo(tipoImpressoraRequestDTO.tipo());
        return tipoImpressora;
    }
}
