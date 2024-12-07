// package com.ecoprint.control_center.controller.mapper;

// import com.ecoprint.control_center.dto.ImpressaoRequestDTO;
// import com.ecoprint.control_center.model.Impressao;
// import com.ecoprint.control_center.repository.ImpressaoRepository;
// import lombok.RequiredArgsConstructor;
// import org.springframework.stereotype.Component;

// @Component
// @RequiredArgsConstructor
// public class ImpressaoMapper {

//     private final ImpressaoRepository impressaoRepository;

//     // Criação de uma nova Impressão
//     public Impressao toModel(ImpressaoRequestDTO impressaoRequestDTO) {
//         return Impressao.builder()
//                 .custo(impressaoRequestDTO.custo())
//                 .insumosUtilizados(impressaoRequestDTO.insumosUtilizados())
//                 .build();
//     }

//     // Atualização de uma Impressão existente
//     public Impressao toModel(Integer id, ImpressaoRequestDTO impressaoRequestDTO) {
//         Impressao impressao = impressaoRepository.findById(id)
//                 .orElseThrow(() -> new IllegalArgumentException("Impressão com ID " + id + " não encontrada"));

//         impressao.setCusto(impressaoRequestDTO.custo());
//         impressao.setInsumosUtilizados(impressaoRequestDTO.insumosUtilizados());

//         return impressao;
//     }
// }
