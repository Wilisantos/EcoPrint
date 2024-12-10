package com.ecoprint.control_center.dto;

public record InsumoRequestDTO(
        int id,
        String tipoInsumo,
        String descricao,
        Long quantidade
) {}
