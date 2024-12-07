package com.ecoprint.control_center.controller.mapper;

import com.ecoprint.control_center.dto.ImpressoraRequestDTO;
import com.ecoprint.control_center.model.Impressora;
import com.ecoprint.control_center.model.TipoImpressora;
import com.ecoprint.control_center.repository.ImpressoraRepository;
import com.ecoprint.control_center.repository.TipoImpressoraRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ImpressoraMapper {

    private final TipoImpressoraRepository tipoImpressoraRepository;
    private final ImpressoraRepository impressoraRepository;

    public Impressora toModel(ImpressoraRequestDTO impressoraRequestDTO){

        //Vendo se existe o tipoImpressão
        TipoImpressora tipoImpressora = tipoImpressoraRepository.findById(impressoraRequestDTO.tipoImpressora())
                .orElseThrow(() -> new IllegalArgumentException("Ja existe o tipoImpressora"));

      //Criando e retornando a impressora
        return Impressora.builder().tipoImpressora(tipoImpressora)
                .modelo(impressoraRequestDTO.modelo())
                .capacidade(impressoraRequestDTO.capacidade())
                .build();
    }

    public Impressora toModel(Integer id, ImpressoraRequestDTO impressoraRequestDTO){

        //Vendo se existe a impressora com o respectivo id
        Impressora impressora = impressoraRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Impressora com " + id + " não encontrada"));

        //Vendo se existe o tipoImpressão que vai ser atualizado
        TipoImpressora tipoImpressora = tipoImpressoraRepository.findById(impressoraRequestDTO.tipoImpressora())
                .orElseThrow(() -> new IllegalArgumentException("Salve Salve"));


        //retornando a impressora editada
        return Impressora.builder()
                .id(impressora.getId())
                .tipoImpressora(tipoImpressora)
                .modelo(impressoraRequestDTO.modelo())
                .capacidade(impressoraRequestDTO.capacidade())
                .build();
    }



}
