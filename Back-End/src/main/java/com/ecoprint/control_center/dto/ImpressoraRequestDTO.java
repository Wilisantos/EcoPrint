package com.ecoprint.control_center.dto;

public record ImpressoraRequestDTO(
        //Talvez mudar para id_tipo_impressora
        String modelo,
        Long capacidade,
        Integer tipoImpressora
) {}