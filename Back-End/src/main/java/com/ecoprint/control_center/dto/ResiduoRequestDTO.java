package com.ecoprint.control_center.dto;


public record ResiduoRequestDTO(
        int id,
        int tipoResiduo,
        String tipo,
        int quantidade
)
{}
