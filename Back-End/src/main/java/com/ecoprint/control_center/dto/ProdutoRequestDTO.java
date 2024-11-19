package com.ecoprint.control_center.dto;

public record ProdutoRequestDTO(
        Long id,
        Long tipoProduto,
        Long valor,
        String descricao
) {}