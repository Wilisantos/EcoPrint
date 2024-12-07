package com.ecoprint.control_center.dto;

import java.util.List;

public record ReciclagemRequestDTO(
        int id,
        List<Long> tipoResiduos,
        String pontoReciclagem,
        Long valorGanho
) {}
