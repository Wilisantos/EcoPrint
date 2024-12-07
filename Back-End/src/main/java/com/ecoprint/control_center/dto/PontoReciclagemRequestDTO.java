package com.ecoprint.control_center.dto;

import java.util.List;

public record PontoReciclagemRequestDTO(
        int id,
        List<Long> tipoResiduos,
        String descricao
) {}
