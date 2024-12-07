package com.ecoprint.control_center.dto;

public record ImpressoraRequestDTO(
        //Talvez mudar para id_tipo_impressora
        int tipoImpressora,
        String modelo,
        Long capacidade
) {}