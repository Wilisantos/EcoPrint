package com.ecoprint.control_center.dto;

public record ProdutoRequestDTO(
        int id,
        int tipoProduto,
        Long valor,
        String descricao
) {}