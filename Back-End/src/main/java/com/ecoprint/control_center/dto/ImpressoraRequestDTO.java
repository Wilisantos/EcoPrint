package com.ecoprint.control_center.dto;

public record ImpressoraRequestDTO(
        Long id,
        Long tipoImpressora,
        String modelo,
        Long capacidade
) {}